package com.matchSportifs.resource;

import com.matchSportifs.dao.UserDAO;

import java.io.IOException;
import java.net.URI;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.matchSportifs.model.*;
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResouce {
	 private UserDAO userDAO = new UserDAO();

	 @POST
	 @Path("/login")
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 public Response loginUser(
	         @FormParam("email") String email,
	         @FormParam("password") String password, 
	         @Context UriInfo uriInfo,
	         @Context HttpServletRequest request
	         ) {
	     User user = userDAO.findUserByEmail(email);
	     if (user != null && user.getPassword().equals(password)) { // Utilisez une vérification de mot de passe sécurisée
	         request.getSession().setAttribute("user", user); // Stockez l'utilisateur ou son rôle dans la session
	         request.getSession().setAttribute("role", user.getRole());
	         URI uri = uriInfo.getBaseUriBuilder().path("/classements").build(); // Redirection vers /classements
	         return Response.seeOther(uri).build(); // Redirection
	     } else {
	         return Response.status(Response.Status.UNAUTHORIZED).entity("Login failed").build();
	     }
	 }
	 @GET
	 @Path("/logout")
	 public Response logoutUser(@Context HttpServletRequest request, @Context HttpServletResponse response) {
	     HttpSession session = request.getSession(false);
	     if (session != null) {
	         session.invalidate();
	     }

	     // Obtenir le contexte de l'application
	     String contextPath = request.getContextPath();

	     // Rediriger vers la racine de l'application
	     try {
	         response.sendRedirect(contextPath + "/");
	     } catch (IOException e) {
	         e.printStackTrace();
	     }

	     return Response.ok().entity("Logout successful").build();
	 }


}
