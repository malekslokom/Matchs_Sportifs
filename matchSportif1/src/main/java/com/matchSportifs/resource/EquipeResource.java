package com.matchSportifs.resource;

import com.matchSportifs.dao.ClassementEquipesDAO;
import com.matchSportifs.dao.EntraineurDAO;
import com.matchSportifs.dao.EquipeDAO;
import com.matchSportifs.dao.JoueurDAO;
import com.matchSportifs.model.ClassementEquipes;
import com.matchSportifs.model.Equipe;
import com.matchSportifs.model.Joueur;
import com.matchSportifs.util.HibernateUtil;
import com.matchSportifs.model.Entraineur;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Set;



@Path("/equipes")
@Produces(MediaType.APPLICATION_JSON)
public class EquipeResource {

    private final EquipeDAO equipeDao = new EquipeDAO();
    private final ClassementEquipesDAO classementEquipeDao = new ClassementEquipesDAO();
    private final JoueurDAO joueurDao = new JoueurDAO();
    private final EntraineurDAO entraineurDao = new EntraineurDAO();
    @GET 
    @Produces(MediaType.TEXT_HTML)
    public Response getAllEquipes(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
            List<Equipe> equipes = equipeDao.getAllEquipes();
            request.setAttribute("listEquipe",equipes);
            
            // Use RequestDispatcher to forward the request to the JSP page
            RequestDispatcher rd = request.getRequestDispatcher("/views/equipe/equipe-list.jsp");
            rd.forward(request, response);
            // Returning Response.ok() to satisfy JAX-RS requirements
            return Response.ok().build();
        } catch (Exception e) {
        	e.printStackTrace();

            throw new WebApplicationException("Error fetching equipes", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    public Response getEquipe(@PathParam("id") int id) {
        try {
        	Equipe equipe = equipeDao.getEquipe(id);
            if (equipe != null) {
                return Response.ok(equipe).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Equipe not found").build();
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching equipe", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}/detail")
    @Produces(MediaType.TEXT_HTML)
    public void getEquipeDetail(@PathParam("id") int id, @Context HttpServletRequest request, @Context HttpServletResponse response) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Equipe equipe = session.get(Equipe.class, id);
            ClassementEquipes classement = equipe.getClassement();
            if (equipe != null) {
                Hibernate.initialize(equipe.getJoueursActuels()); // Initialise la collection des joueurs
                Hibernate.initialize(equipe.getEntraineurs());
                request.setAttribute("classement",classement);

                request.setAttribute("equipe", equipe);
                RequestDispatcher rd = request.getRequestDispatcher("/views/equipe/equipe-details.jsp");
                rd.forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Equipe not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching equipe");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public Response showAddNewEquipeForm(@Context HttpServletRequest request, @Context HttpServletResponse response) {
    	System.out.println("NEW");
        RequestDispatcher rd = request.getRequestDispatcher("/views/equipe/equipe-form.jsp");
        
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
    public Response addEquipe(
            @FormParam("nom") String nom,
            @FormParam("anneeNaissance") String anneeNaissance,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        try {
        	System.out.println("Insert");
            LocalDate localDateNaissance = convertStringToLocalDate(anneeNaissance);

            Equipe newEquipe = new Equipe();
            newEquipe.setNom(nom);
            newEquipe.setAnneeNaissance(localDateNaissance);
            equipeDao.saveEquipe(newEquipe);

            // Construct the URI for redirection
            URI uri = uriInfo.getBaseUriBuilder()
                    .path("/equipes")
                    .build();

            // Redirect to the equipe-list page
            return Response.seeOther(uri).build();
        } catch (Exception e) {
            throw new WebApplicationException("Error adding equipe", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    private LocalDate convertStringToLocalDate(String dateNaissance) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the string into a LocalDate object
        return LocalDate.parse(dateNaissance, formatter);
	}

	private Response redirectToEquipeList(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Equipe> equipes = equipeDao.getAllEquipes();
            request.setAttribute("listEquipee", equipes);

            // Use RequestDispatcher to forward the request to the JSP page
            RequestDispatcher rd = request.getRequestDispatcher("/views/equipe/equipe-list.jsp");
            
            rd.forward(request, response);

            // Set the location header for redirection
            String contextPath = request.getContextPath();
            String equipeListPath = contextPath + "/api/equipes/";
            response.setHeader("Location", equipeListPath);
            response.setStatus(HttpServletResponse.SC_SEE_OTHER);

            // Returning Response.ok() to satisfy JAX-RS requirements
            return Response.ok().build();
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching equipes", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }



	@GET
	@Path("/edit")
	@Produces(MediaType.TEXT_HTML)
	public Response showEditForm(@QueryParam("id") int id, @Context UriInfo uriInfo, @Context HttpServletRequest request, @Context HttpServletResponse response) {
	    Session session = null;
	    System.out.println("edit");
	    Transaction transaction = null;
	    try {
	        session = HibernateUtil.getSessionFactory().openSession();
	        transaction = session.beginTransaction();

	        Equipe existingEquipe = session.get(Equipe.class, id);
	        List<Joueur> joueurSansEquipe = joueurDao.getAllJoueursWithoutEquipe();
	        List<Entraineur> entraineurSansEquipe = entraineurDao.getAllEntraineursWithoutEquipe();

	        if (existingEquipe != null) {
	            Hibernate.initialize(existingEquipe.getJoueursActuels());
	            Hibernate.initialize(existingEquipe.getEntraineurs());

	            request.setAttribute("joueurSansEquipe", joueurSansEquipe);
	            request.setAttribute("entraineurSansEquipe", entraineurSansEquipe);
	            request.setAttribute("equipe", existingEquipe);

	            RequestDispatcher rd = request.getRequestDispatcher("/views/equipe/equipe-form.jsp");
	            rd.forward(request, response);
	            System.out.println("edit");
	            transaction.commit();
	        } else {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Equipe not found");
	        }
	    } catch (Exception e) {
	        if (transaction != null) transaction.rollback();
	        e.printStackTrace();
	    } finally {
	        if (session != null) session.close();
	    }
	    return Response.ok().build();
	}
  


	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateEquipe(
	        @FormParam("id") int id,
	        @FormParam("nom") String nom,
	        @FormParam("anneeNaissance") String anneeNaissance,
	        @FormParam("allPlayerIds") List<Integer> allPlayerIds,
	        @FormParam("removePlayers") List<Integer> removePlayerIds,
	        @FormParam("allCoachIds") List<Integer> allCoachIds,
	        @FormParam("removeCoaches") List<Integer> removeCoachIds,
	        @FormParam("addCoaches") List<Integer> addCoaches,
	        @FormParam("addPlayers") List<Integer> addPlayers,
	        @Context UriInfo uriInfo,
	        @Context HttpServletRequest request,
	        @Context HttpServletResponse response
	) {
	    Transaction transaction = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        // Fetch the equipe before updating
	        Equipe existingEquipe = equipeDao.getEquipe(id);
	        if (existingEquipe == null) {
	            return Response.status(Response.Status.NOT_FOUND).entity("Equipe not found").build();
	        }

	        // Update equipe details
	        existingEquipe.setNom(nom);
	        existingEquipe.setAnneeNaissance(LocalDate.parse(anneeNaissance));

	        // Handle removal of players
	        if (allPlayerIds != null && removePlayerIds != null) {
	            for (Integer playerId : allPlayerIds) {
	                if (!removePlayerIds.contains(playerId)) {
	                    // Player was not unchecked, do not remove
	                } else {
	                    // Player was unchecked, remove from equipe
	                    Joueur joueur = joueurDao.getJoueurById(playerId);
	                    if (joueur != null) {
	                        joueur.setEquipeActuelle(null);
	                        joueur.setEquipePrecedente(existingEquipe);
	                        session.update(joueur);
	                    }
	                }
	            }
	        }
	     // Handle addition of players
	        if (addPlayers != null) {
	            for (Integer playerId : addPlayers) {
	                Joueur joueur = joueurDao.getJoueurById(playerId);
	                if (joueur != null) {
	                    joueur.setEquipeActuelle(existingEquipe);
	                    session.update(joueur);
	                }
	            }
	        }

	        // Handle removal of coaches
	        if (allCoachIds != null && removeCoachIds != null) {
	            for (Integer coachId : allCoachIds) {
	                if (!removeCoachIds.contains(coachId)) {
	                    // Coach was not unchecked, do not remove
	                } else {
	                    // Coach was unchecked, remove from equipe
	                    Entraineur entraineur = entraineurDao.getEntraineur(coachId);
	                    if (entraineur != null) {
	                        entraineur.setEquipe(null);
	                        session.update(entraineur);
	                    }
	                }
	            }
	        }
	     // Handle addition of coaches
	        if (addCoaches != null) {
	            for (Integer coachId : addCoaches) {
	                Entraineur entraineur = entraineurDao.getEntraineur(coachId);
	                if (entraineur != null) {
	                    entraineur.setEquipe(existingEquipe);
	                    session.update(entraineur);
	                }
	            }
	        }
	        // Save the updated equipe
	        session.update(existingEquipe);
	        transaction.commit();

	        // Construct the URI for redirection to the detail page
	        URI uri = uriInfo.getBaseUriBuilder()
	                .path(EquipeResource.class)
	                .path(EquipeResource.class, "getEquipeDetail") // Use the method name that serves the detail page
	                .resolveTemplate("id", existingEquipe.getIdEquipe()) // Replace the template parameter with the actual ID
	                .build();

	        // Redirect to the equipe detail page
	        return Response.seeOther(uri).build();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        throw new WebApplicationException("Error updating Equipe", Response.Status.INTERNAL_SERVER_ERROR);
	    }
	}
    @GET
    @Path("/delete")
    @Produces(MediaType.TEXT_HTML)
    public Response deleteEquipe(
            @QueryParam("id") int id,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response) {
        try {
            equipeDao.deleteEquipe(id);

            URI uri = uriInfo.getBaseUriBuilder()
                    .path("/equipes")
                    .build();

            return Response.seeOther(uri).status(Response.Status.FOUND).build();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new WebApplicationException("Error deleting joueur", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

   @GET
    @Path("/{id}/joueurs")
    public Response getJoueursByEquipe(@PathParam("id") int equipeId) {
        try {
            Equipe equipe = equipeDao.getEquipe(equipeId);
            if (equipe != null) {
                List<Joueur> joueurs = new ArrayList<>(equipe.getJoueursActuels());
                return Response.ok(joueurs).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Equipe not found").build();
            }
        } catch(Exception e) {
            throw new WebApplicationException("Error fetching joueurs", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{id}/classement")
    public Response getClassementByEquipe(@PathParam("id") int equipeId) {
        try {
            Equipe equipe = equipeDao.getEquipe(equipeId);
            if (equipe != null) {
                ClassementEquipes classementEquipe = equipe.getClassement();
                if (classementEquipe != null) {
                    return Response.ok(classementEquipe).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Classement not found for equipe").build();
                }
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Equipe not found").build();
            }
        } catch(Exception e) {
            throw new WebApplicationException("Error fetching classement", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    @GET
    @Path("/{id}/entraineurs")
    public Response getEntraineursByEquipe(@PathParam("id") int equipeId) {
        try {
            Equipe equipe = equipeDao.getEquipe(equipeId);
            if (equipe != null) {
                Set<Entraineur> entraineurs = equipe.getEntraineurs();
                return Response.ok(new ArrayList<>(entraineurs)).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Equipe not found").build();
            }
        } catch(Exception e) {
            throw new WebApplicationException("Error fetching entraineurs", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
