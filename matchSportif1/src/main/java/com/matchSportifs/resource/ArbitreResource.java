package com.matchSportifs.resource;

import com.matchSportifs.dao.ArbitreDAO;
import com.matchSportifs.model.Arbitre;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;



@Path("/arbitres")
@Produces(MediaType.APPLICATION_JSON)
public class ArbitreResource {

    private final ArbitreDAO arbitreDao = new ArbitreDAO();

    @GET 
    @Produces(MediaType.TEXT_HTML)
    public Response getAllArbitres(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
            List<Arbitre> arbitres = arbitreDao.getAllArbitres();
            request.setAttribute("listArbitre", arbitres);

            // Use RequestDispatcher to forward the request to the JSP page
           RequestDispatcher rd = request.getRequestDispatcher("/views/arbitre/arbitre-list.jsp");
            rd.forward(request, response);
            // Returning Response.ok() to satisfy JAX-RS requirements
            return Response.ok().build();
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching arbitres", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    public Response getArbitre(@PathParam("id") int id) {
        try {
            Arbitre arbitre = arbitreDao.getArbitre(id);
            if (arbitre != null) {
                return Response.ok(arbitre).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Arbitre not found").build();
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching arbitre", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public Response showAddNewArbitreForm(@Context HttpServletRequest request, @Context HttpServletResponse response) {
       RequestDispatcher rd = request.getRequestDispatcher("/views/arbitre/arbitre-form.jsp");
        
        try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Returning Response.ok() to satisfy JAX-RS requirements
        return Response.ok().build();
    }

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addArbitre(
            @FormParam("nom") String nom,
            @FormParam("prenom") String prenom,
            @FormParam("dateNaissance") String dateNaissance,
            @FormParam("nationalite") String nationalite,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        try {
            LocalDate localDateNaissance = convertStringToLocalDate(dateNaissance);
            Arbitre newArbitre = new Arbitre();
            newArbitre.setNom(nom);
            newArbitre.setPrenom(prenom);
            newArbitre.setDateNaissance(localDateNaissance);
            newArbitre.setNationalite(nationalite);

            arbitreDao.saveArbitre(newArbitre);

            // Construct the URI for redirection
            URI uri = uriInfo.getBaseUriBuilder()
                    .path("/arbitres")
                    .build();

            // Redirect to the arbitre-list page
            return Response.seeOther(uri).build();
        } catch (Exception e) {
            throw new WebApplicationException("Error adding arbitre", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    private LocalDate convertStringToLocalDate(String dateNaissance) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the string into a LocalDate object
        return LocalDate.parse(dateNaissance, formatter);
	}

	private Response redirectToArbitreList(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Arbitre> arbitres = arbitreDao.getAllArbitres();
            request.setAttribute("listArbitre", arbitres);

            // Use RequestDispatcher to forward the request to the JSP page
            RequestDispatcher rd = request.getRequestDispatcher("/views/arbitre/arbitre-list.jsp");
            
            rd.forward(request, response);

            // Set the location header for redirection
            String contextPath = request.getContextPath();
            String arbitreListPath = contextPath + "/api/arbitres/";
            response.setHeader("Location", arbitreListPath);
            response.setStatus(HttpServletResponse.SC_SEE_OTHER);

            // Returning Response.ok() to satisfy JAX-RS requirements
            return Response.ok().build();
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching arbitres", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }



	@GET
	@Path("/edit")
	@Produces(MediaType.TEXT_HTML)
	public Response showEditForm(@QueryParam("id") int id, @Context UriInfo uriInfo, @Context HttpServletRequest request, @Context HttpServletResponse response) {
	    try {
	        Arbitre existingArbitre = arbitreDao.getArbitre(id);

	        if (existingArbitre != null) {
	            // Forward the request to the arbitre-form.jsp page with arbitre object as an attribute
	            RequestDispatcher rd = request.getRequestDispatcher("/views/arbitre/arbitre-form.jsp");
	        	
	            request.setAttribute("arbitre", existingArbitre);
	            rd.forward(request, response);

	            // Returning Response.ok() to satisfy JAX-RS requirements
	            return Response.ok().build();
	        } else {
	            // Arbitre not found, return a 404 response
	            return Response.status(Response.Status.NOT_FOUND).entity("Arbitre not found").build();
	        }
	    } catch (Exception e) {
	        throw new WebApplicationException("Error fetching arbitre", Response.Status.INTERNAL_SERVER_ERROR);
	    }
	}

  


    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateArbitre(
            @FormParam("id") int id,
            @FormParam("nom") String nom,
            @FormParam("prenom") String prenom,
            @FormParam("dateNaissance") String dateNaissance,
            @FormParam("nationalite") String nationalite,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        try {
            // Fetch the arbitre before updating
            Arbitre existingArbitre = arbitreDao.getArbitre(id);

            if (existingArbitre == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Arbitre not found").build();
            }

            // Convert dateNaissance String to LocalDate (implement this conversion method)
            LocalDate localDateNaissance = convertStringToLocalDate(dateNaissance);

            // Update the arbitre
            existingArbitre.setNom(nom);
            existingArbitre.setPrenom(prenom);
            existingArbitre.setDateNaissance(localDateNaissance);
            existingArbitre.setNationalite(nationalite);

            arbitreDao.updateArbitre(existingArbitre);

            // Construct the URI for redirection
            URI uri = uriInfo.getBaseUriBuilder()
                    .path("/arbitres")
                    .build();

            // Redirect to the arbitre-list page
            return Response.seeOther(uri).build();
        } catch (Exception e) {
            throw new WebApplicationException("Error updating arbitre", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }





    @GET
    @Path("/delete")
    @Produces(MediaType.TEXT_HTML)
    public Response deleteArbitre(
            @QueryParam("id") int id,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response) {
        try {

            arbitreDao.deleteArbitre(id);

            // Construct the URI for redirection
            URI uri = uriInfo.getBaseUriBuilder()
                    .path("/arbitres")
                    .build();

            // Redirect to the arbitre-list page
            return Response.seeOther(uri).build();
        } catch (Exception e) {
            throw new WebApplicationException("Error deleting arbitre", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
