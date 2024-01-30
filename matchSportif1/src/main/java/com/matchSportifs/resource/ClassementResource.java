package com.matchSportifs.resource;

import com.matchSportifs.dao.ClassementEquipesDAO;
import com.matchSportifs.dao.EquipeDAO;
import com.matchSportifs.model.ClassementEquipes;
import com.matchSportifs.model.Equipe;

import java.io.IOException;
import java.net.URI;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;



@Path("/classements")
@Produces(MediaType.APPLICATION_JSON)
public class ClassementResource {

    private final ClassementEquipesDAO classementDao = new ClassementEquipesDAO();
    private final EquipeDAO equipeDao = new EquipeDAO();

    @GET 
    @Produces(MediaType.TEXT_HTML)
    public Response getAllClassementsEquipes(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
            List<ClassementEquipes> classements = classementDao.getAllClassementEquipes();
            request.setAttribute("listClassement", classements);

            // Use RequestDispatcher to forward the request to the JSP page
           RequestDispatcher rd = request.getRequestDispatcher("/views/classement/classement-list.jsp");
            rd.forward(request, response);
            // Returning Response.ok() to satisfy JAX-RS requirements
            return Response.ok().build();
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching classements", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    public Response getClassement(@PathParam("id") long id) {
        try {
            ClassementEquipes classement = classementDao.getClassementEquipes(id);
            if (classement != null) {
                return Response.ok(classement).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Classement not found").build();
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching classement", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public Response showAddNewClassementForm(@Context HttpServletRequest request, @Context HttpServletResponse response) {

        RequestDispatcher rd = request.getRequestDispatcher("/views/classement/classement-form.jsp");
        List<Equipe> equipes = equipeDao.getEquipesWithoutClassement();
        request.setAttribute("listEquipe", equipes);
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
    public Response addClassement(
            @FormParam("points") int points,
            @FormParam("pointsDom") int pointsDom,
            @FormParam("pointsExt") int pointsExt,
            @FormParam("matchesPlayed") int matchesPlayed,
            @FormParam("idEquipe") int idEquipe,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        try {

        	Equipe equipe = equipeDao.getEquipe(idEquipe);
        	ClassementEquipes newClassement = new ClassementEquipes();
            newClassement.setEquipe(equipe);
            newClassement.setPoints(points);
            newClassement.setPointsDom(pointsDom);
            newClassement.setPointsExt(pointsExt);
            newClassement.setMatchesPlayed(matchesPlayed);
            classementDao.saveClassementEquipes(newClassement);
         
            // Construct the URI for redirection
            URI uri = uriInfo.getBaseUriBuilder()
                    .path("/classements")
                    .build();

            // Redirect to the classement-list page
            return Response.seeOther(uri).build();
        } catch (Exception e) {
            throw new WebApplicationException("Error adding classement", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }





	@GET
	@Path("/edit")
	@Produces(MediaType.TEXT_HTML)
	public Response showEditForm(@QueryParam("id") long id, @Context UriInfo uriInfo, @Context HttpServletRequest request, @Context HttpServletResponse response) {
	    try {
	        System.out.println("edit");
	        ClassementEquipes existingClassement = classementDao.getClassementEquipes(id);
	        List<Equipe> equipes = equipeDao.getAllEquipes();
	        if (existingClassement != null) {
	            // Forward the request to the classement-form.jsp page with classement object as an attribute
	        	RequestDispatcher rd = request.getRequestDispatcher("/views/classement/classement-form.jsp");
	        	request.setAttribute("listEquipe", equipes);
	        	// RequestDispatcher rd = request.getRequestDispatcher("/todo-page.jsp");
	            request.setAttribute("classement", existingClassement);
	            rd.forward(request, response);

	            // Returning Response.ok() to satisfy JAX-RS requirements
	            return Response.ok().build();
	        } else {
	            // classement not found, return a 404 response
	            return Response.status(Response.Status.NOT_FOUND).entity("Classement not found").build();
	        }
	    } catch (Exception e) {
	        throw new WebApplicationException("Error fetching classement", Response.Status.INTERNAL_SERVER_ERROR);
	    }
	}

  


	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateClassement(
	        @FormParam("id") long id,
	        @FormParam("points") int points,
	        @FormParam("pointsDom") int pointsDom,
	        @FormParam("pointsExt") int pointsExt,
	        @FormParam("matchesPlayed") int matchesPlayed,
	        @Context UriInfo uriInfo,
	        @Context HttpServletRequest request,
	        @Context HttpServletResponse response
	) {
	    try {

	        // Fetch the classement before updating
	        ClassementEquipes existingClassement = classementDao.getClassementEquipes(id);

	        if (existingClassement == null) {
	            return Response.status(Response.Status.NOT_FOUND).entity("Classement not found").build();
	        }

	        // Update the classement
	        existingClassement.setPoints(points);
	        existingClassement.setPointsDom(pointsDom);
	        existingClassement.setPointsExt(pointsExt);
	        existingClassement.setMatchesPlayed(matchesPlayed);
	        
	        classementDao.updateClassementEquipes(existingClassement);

	        // Construct the URI for redirection
	        URI uri = uriInfo.getBaseUriBuilder()
	                .path("/classements")
	                .build();

	        // Redirect to the classement-list page
	        return Response.seeOther(uri).build();
	    } catch (Exception e) {
	        throw new WebApplicationException("Error updating classement", Response.Status.INTERNAL_SERVER_ERROR);
	    }
	}



    @GET
    @Path("/delete")
    @Produces(MediaType.TEXT_HTML)
    public Response deleteClassement(
            @QueryParam("id") long id,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response) {
        try {

           
            classementDao.deleteClassementEquipes(id);

            // Construct the URI for redirection
            URI uri = uriInfo.getBaseUriBuilder()
                    .path("/classements")
                    .build();

            // Redirect to the classement-list page
            return Response.seeOther(uri).build();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new WebApplicationException("Error deleting classement", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
