package com.matchSportifs.resource;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.matchSportifs.dao.RencontreDAO;
import com.matchSportifs.dao.TempsDeJeuxDAO;
import com.matchSportifs.model.Rencontre;
import com.matchSportifs.model.TempsDeJeux;
import com.matchSportifs.util.HibernateUtil;

public class TempsDeJeuxResource {

    private final TempsDeJeuxDAO tempsDeJeuxDAO = new TempsDeJeuxDAO();
    private final RencontreDAO rencontreDAO = new RencontreDAO();
    @GET 
    @Produces(MediaType.TEXT_HTML)
    public Response getAllTempsDeJeuxs(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
            List<TempsDeJeux> tempsDeJeux = tempsDeJeuxDAO.getAllTempsDeJeux();
            request.setAttribute("listTempsDeJeux", tempsDeJeux);

            // Use RequestDispatcher to forward the request to the JSP page
           RequestDispatcher rd = request.getRequestDispatcher("/views/tempsDeJeux/tempsDeJeux-list.jsp");
           // RequestDispatcher rd = request.getRequestDispatcher("/list-todo.jsp");
            rd.forward(request, response);
            // Returning Response.ok() to satisfy JAX-RS requirements
            return Response.ok().build();
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching tempsDeJeux", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    public Response getTempsDeJeux(@PathParam("id") int id) {
        try {
            TempsDeJeux tempsDeJeux = tempsDeJeuxDAO.getTempsDeJeuxById(id);
            if (tempsDeJeux != null) {
                return Response.ok(tempsDeJeux).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("TempsDeJeux not found").build();
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching tempsDeJeux", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/new/{idRencontre}")
    @Produces(MediaType.TEXT_HTML)
    public Response showAddNewTempsDeJeuxForm(
        @PathParam("idRencontre") int idRencontre,
        @Context HttpServletRequest request,
        @Context HttpServletResponse response
    ) {
    	System.out.println("NEW");
       RequestDispatcher rd = request.getRequestDispatcher("/views/tempsDeJeux/tempsDeJeux-form.jsp");
        
        try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Returning Response.ok() to satisfy JAX-RS requirements
        return Response.ok().build();
    }

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addTempsDeJeux(
            @FormParam("heureDebut") String heureDebut,
            @FormParam("heureFin") String heureFin,
            @FormParam("type") String type,
            @FormParam("idRencontre") int idRencontre,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        try {
            // Parsing string inputs to LocalTime
            LocalTime localHeureDebut = LocalTime.parse(heureDebut);
            LocalTime localHeureFin = LocalTime.parse(heureFin);

            // Creating a new TempsDeJeux object
            TempsDeJeux newTempsDeJeux = new TempsDeJeux();
            newTempsDeJeux.setHeureDebut(localHeureDebut);
            newTempsDeJeux.setHeureFin(localHeureFin);
            newTempsDeJeux.setNumero(type);
            
            // Fetching the Rencontre object using its ID
            Rencontre rencontre = rencontreDAO.getRencontreById(idRencontre);
            newTempsDeJeux.setRencontre(rencontre);

            // Saving the new TempsDeJeux object
            tempsDeJeuxDAO.saveTempsDeJeux(newTempsDeJeux);

            // Constructing the URI for redirection
            URI uri = uriInfo.getBaseUriBuilder()
                    .path("/tempsDeJeux")
                    .build();

            // Redirecting to the tempsDeJeux-list page
            return Response.seeOther(uri).build();
        } catch (Exception e) {
            throw new WebApplicationException("Error adding tempsDeJeux", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    private LocalDate convertStringToLocalDate(String dateNaissance) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the string into a LocalDate object
        return LocalDate.parse(dateNaissance, formatter);
	}

	private Response redirectToTempsDeJeuxList(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<TempsDeJeux> tempsDeJeux = tempsDeJeuxDAO.getAllTempsDeJeux();
            request.setAttribute("listTempsDeJeux", tempsDeJeux);

            // Use RequestDispatcher to forward the request to the JSP page
            RequestDispatcher rd = request.getRequestDispatcher("/views/tempsDeJeux/tempsDeJeux-list.jsp");
            
            rd.forward(request, response);

            // Set the location header for redirection
            String contextPath = request.getContextPath();
            String tempsDeJeuxListPath = contextPath + "/api/tempsDeJeux/";
            response.setHeader("Location", tempsDeJeuxListPath);
            response.setStatus(HttpServletResponse.SC_SEE_OTHER);

            // Returning Response.ok() to satisfy JAX-RS requirements
            return Response.ok().build();
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching tempsDeJeux", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }



	@GET
	@Path("/edit")
	@Produces(MediaType.TEXT_HTML)
	public Response showEditForm(@QueryParam("id") int id, @Context UriInfo uriInfo, @Context HttpServletRequest request, @Context HttpServletResponse response) {
	    try {
	        System.out.println("edit");
	        TempsDeJeux existingTempsDeJeux = tempsDeJeuxDAO.getTempsDeJeuxById(id);

	        if (existingTempsDeJeux != null) {
	            // Forward the request to the tempsDeJeux-form.jsp page with tempsDeJeux object as an attribute
	            RequestDispatcher rd = request.getRequestDispatcher("/views/tempsDeJeux/tempsDeJeux-form.jsp");
	        	
	            request.setAttribute("tempsDeJeux", existingTempsDeJeux);
	            rd.forward(request, response);

	            // Returning Response.ok() to satisfy JAX-RS requirements
	            return Response.ok().build();
	        } else {
	            // TempsDeJeux not found, return a 404 response
	            return Response.status(Response.Status.NOT_FOUND).entity("TempsDeJeux not found").build();
	        }
	    } catch (Exception e) {
	        throw new WebApplicationException("Error fetching tempsDeJeux", Response.Status.INTERNAL_SERVER_ERROR);
	    }
	}

  


	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateTempsDeJeux(
	        @FormParam("idtempsDeJeux") int id,
	        @FormParam("heureDebut") String heureDebut,
	        @FormParam("heureFin") String heureFin,
	        @FormParam("type") String type,
	        @FormParam("idRencontre") int idRencontre,
	        @Context UriInfo uriInfo,
	        @Context HttpServletRequest request,
	        @Context HttpServletResponse response
	) {
	    try {
	        TempsDeJeux existingTempsDeJeux = tempsDeJeuxDAO.getTempsDeJeuxById(id);

	        if (existingTempsDeJeux == null) {
	            return Response.status(Response.Status.NOT_FOUND).entity("TempsDeJeux not found").build();
	        }

	        LocalTime localHeureDebut = LocalTime.parse(heureDebut);
	        LocalTime localHeureFin = LocalTime.parse(heureFin);

	        existingTempsDeJeux.setHeureDebut(localHeureDebut);
	        existingTempsDeJeux.setHeureFin(localHeureFin);
	        existingTempsDeJeux.setNumero(type);

	        Rencontre rencontre = rencontreDAO.getRencontreById(idRencontre);
	        existingTempsDeJeux.setRencontre(rencontre);

	        tempsDeJeuxDAO.updateTempsDeJeux(existingTempsDeJeux);

	        URI uri = uriInfo.getBaseUriBuilder()
	                .path("/tempsDeJeux")
	                .build();

	        return Response.seeOther(uri).build();
	    } catch (Exception e) {
	        throw new WebApplicationException("Error updating tempsDeJeux", Response.Status.INTERNAL_SERVER_ERROR);
	    }
	}




    @GET
    @Path("/delete")
    @Produces(MediaType.TEXT_HTML)
    public Response deleteTempsDeJeux(
            @QueryParam("id") int id,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response) {
    	try {
            // Start a transaction
            Transaction transaction = null;

            try {
                Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                transaction = session.beginTransaction();
	            TempsDeJeux tempsDeJeuxToDelete = tempsDeJeuxDAO.getTempsDeJeuxById(id);
	
	            if (tempsDeJeuxToDelete == null) {
	                return Response.status(Response.Status.NOT_FOUND).entity("Rencontre not found").build();
	            }
	            
	            session.delete(tempsDeJeuxToDelete);
	            // Construct the URI for redirection
	            URI uri = uriInfo.getBaseUriBuilder()
	                    .path("/tempsDeJeux")
	                    .build();
	
	         // Redirect to the lieuAcceuil-list page
                return Response.seeOther(uri).build();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new WebApplicationException("Error deleting rencontre", Response.Status.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error deleting rencontre", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


}
