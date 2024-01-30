package com.matchSportifs.resource;

import com.matchSportifs.dao.EntraineurDAO;
import com.matchSportifs.dao.EquipeDAO;
import com.matchSportifs.model.Entraineur;
import com.matchSportifs.model.Equipe;

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



@Path("/entraineurs")
@Produces(MediaType.APPLICATION_JSON)
public class EntraineurResource {

    private final EntraineurDAO entraineurDao = new EntraineurDAO();
    private final EquipeDAO equipeDao = new EquipeDAO();
    @GET 
    @Produces(MediaType.TEXT_HTML)
    public Response getAllEntraineurs(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {

            List<Entraineur> entraineurs = entraineurDao.getAllEntraineurs();
            request.setAttribute("listEntraineur", entraineurs);
            
            // Use RequestDispatcher to forward the request to the JSP page
            RequestDispatcher rd = request.getRequestDispatcher("/views/entraineur/entraineur-list.jsp");
            rd.forward(request, response);
            // Returning Response.ok() to satisfy JAX-RS requirements
            return Response.ok().build();
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching entraineurs", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    public Response getEntraineur(@PathParam("id") int id) {
        try {
            Entraineur entraineur = entraineurDao.getEntraineur(id);
            if (entraineur != null) {
                return Response.ok(entraineur).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Entraineur not found").build();
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching entraineur", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public Response showAddNewEntraineurForm(@Context HttpServletRequest request, @Context HttpServletResponse response) {
    	System.out.println("NEW");
    	List<Equipe> equipes = equipeDao.getAllEquipes();
    	
        RequestDispatcher rd = request.getRequestDispatcher("/views/entraineur/entraineur-form.jsp");
        request.setAttribute("listEquipe",equipes);
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
    public Response addEntraineur(
            @FormParam("nom") String nom,
            @FormParam("prenom") String prenom,
            @FormParam("dateNaissance") String dateNaissance,
            @FormParam("nationalite") String nationalite,
            @FormParam("idEquipe") String idequipe,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        try {
        	System.out.println("Insert");
            LocalDate localDateNaissance = convertStringToLocalDate(dateNaissance);
            int idEquipe = Integer.parseInt(idequipe);
            
            Entraineur newEntraineur = new Entraineur();
            newEntraineur.setNom(nom);
            newEntraineur.setPrenom(prenom);
            newEntraineur.setDateNaissance(localDateNaissance);
            newEntraineur.setNationalite(nationalite);
            if (idEquipe > 0) {
                Equipe equipe = equipeDao.getEquipe(idEquipe);
                newEntraineur.setEquipe(equipe);
            } else {
                newEntraineur.setEquipe(null); // Aucune équipe sélectionnée
            }
            entraineurDao.saveEntraineur(newEntraineur);

            // Construct the URI for redirection
            URI uri = uriInfo.getBaseUriBuilder()
                    .path("/entraineurs")
                    .build();

            // Redirect to the entraineur-list page
            return Response.seeOther(uri).build();
        } catch (Exception e) {
            throw new WebApplicationException("Error adding entraineur", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    private LocalDate convertStringToLocalDate(String dateNaissance) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the string into a LocalDate object
        return LocalDate.parse(dateNaissance, formatter);
	}

	private Response redirectToEntraineurList(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Entraineur> entraineurs = entraineurDao.getAllEntraineurs();
            request.setAttribute("listEntraineur", entraineurs);

            // Use RequestDispatcher to forward the request to the JSP page
            RequestDispatcher rd = request.getRequestDispatcher("/views/entraineur/entraineur-list.jsp");
            
            rd.forward(request, response);

            // Set the location header for redirection
            String contextPath = request.getContextPath();
            String entraineurListPath = contextPath + "/api/entraineurs/";
            response.setHeader("Location", entraineurListPath);
            response.setStatus(HttpServletResponse.SC_SEE_OTHER);

            // Returning Response.ok() to satisfy JAX-RS requirements
            return Response.ok().build();
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching entraineurs", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/edit")
    @Produces(MediaType.TEXT_HTML)
    public Response showEditForm(@QueryParam("id") int id, @Context UriInfo uriInfo, @Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
	        System.out.println("edit");
	        Entraineur existingEntraineur = entraineurDao.getEntraineur(id);
	        List<Equipe> equipes = equipeDao.getAllEquipes();
	        if (existingEntraineur != null) {
	            // Set the entraineur and listEquipe as request attributes
	            request.setAttribute("entraineur", existingEntraineur);
	            request.setAttribute("listEquipe", equipes);

	            // Forward the request to the entraineur-form.jsp page
	            RequestDispatcher rd = request.getRequestDispatcher("/views/entraineur/entraineur-form.jsp");
	            rd.forward(request, response);
	            return Response.ok().build();
	        } else {
                return Response.status(Response.Status.NOT_FOUND).entity("entraineur not found").build();
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching entraineur", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

  


    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateEntraineur(
            @FormParam("id") int id,
            @FormParam("nom") String nom,
            @FormParam("prenom") String prenom,
            @FormParam("dateNaissance") String dateNaissance,
            @FormParam("nationalite") String nationalite,
            @FormParam("idEquipe") String idequipe,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        try {
            // Fetch the entraineur before updating
            Entraineur existingEntraineur = entraineurDao.getEntraineur(id);
            int idEquipe = Integer.parseInt(idequipe);
            if (existingEntraineur == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Entraineur not found").build();
            }

            // Convert dateNaissance String to LocalDate (implement this conversion method)
            LocalDate localDateNaissance = convertStringToLocalDate(dateNaissance);

            // Update the entraineur
            existingEntraineur.setNom(nom);
            existingEntraineur.setPrenom(prenom);
            existingEntraineur.setDateNaissance(localDateNaissance);
            existingEntraineur.setNationalite(nationalite);
            
            // Mettre à jour l'équipe de l'entraîneur si l'ID est valide
		     if (idEquipe > 0) {
		         Equipe equipe = equipeDao.getEquipe(idEquipe);
		         existingEntraineur.setEquipe(equipe);
		     } else {
		         existingEntraineur.setEquipe(null); // Aucune équipe sélectionnée
		     }
            entraineurDao.updateEntraineur(existingEntraineur);

            // Construct the URI for redirection
            URI uri = uriInfo.getBaseUriBuilder()
                    .path("/entraineurs")
                    .build();

            // Redirect to the entraineur-list page
            return Response.seeOther(uri).build();
        } catch (Exception e) {
            throw new WebApplicationException("Error updating entraineur", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    @GET
    @Path("/{id}/equipe")
    public Response getEquipeByEntraineur(@PathParam("id") int entraineurId) {
        try {
            Entraineur entraineur = entraineurDao.getEntraineur(entraineurId);
            if (entraineur != null) {
                Equipe equipe = entraineur.getEquipe();
                if (equipe != null) {
                    return Response.ok(equipe).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Equipe not found for entraineur").build();
                }
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Entraineur not found").build();
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching equipe for entraineur", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    @GET
    @Path("/delete")
    @Produces(MediaType.TEXT_HTML)
    public Response deleteEntraineur(
            @QueryParam("id") int id,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response) {
        try {
            System.out.println("delete");
            entraineurDao.deleteEntraineur(id);

            // Construct the URI for redirection
            URI uri = uriInfo.getBaseUriBuilder()
                    .path("/entraineurs")
                    .build();

            // Redirect to the entraineur-list page
            return Response.seeOther(uri).build();
        } catch (Exception e) {
            throw new WebApplicationException("Error deleting entraineur", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
