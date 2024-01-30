package com.matchSportifs.resource;

import com.matchSportifs.dao.RencontreDAO;
import com.matchSportifs.dao.TempsDeJeuxDAO;
import com.matchSportifs.dao.ArbitreDAO;
import com.matchSportifs.dao.ButRencontreDAO;
import com.matchSportifs.dao.EquipeDAO;
import com.matchSportifs.dao.EquipeRencontreDAO;
import com.matchSportifs.dao.JoueurDAO;
import com.matchSportifs.dao.LieuAcceuilDAO;
import com.matchSportifs.dao.ParticipantDAO;
import com.matchSportifs.dao.PenaliteDAO;
import com.matchSportifs.model.Adresse;
import com.matchSportifs.model.Arbitre;
import com.matchSportifs.model.ButRencontre;
import com.matchSportifs.model.ClassementEquipes;
import com.matchSportifs.model.Equipe;
import com.matchSportifs.model.EquipeRencontre;
import com.matchSportifs.model.EquipeRencontreId;
import com.matchSportifs.model.Joueur;
import com.matchSportifs.model.LieuAcceuil;
import com.matchSportifs.model.Participant;
import com.matchSportifs.model.ParticipantId;
import com.matchSportifs.model.Penalite;
import com.matchSportifs.model.Rencontre;
import com.matchSportifs.model.TempsDeJeux;
import com.matchSportifs.util.HibernateUtil;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Path("/rencontres")
public class RencontreResource {

    private final RencontreDAO rencontreDAO = new RencontreDAO();
    private final LieuAcceuilDAO lieuAcceuilDAO = new LieuAcceuilDAO();
    private final EquipeDAO equipeDao = new EquipeDAO();
    private final ArbitreDAO arbitreDao = new ArbitreDAO();
    private final ParticipantDAO participantDAO = new ParticipantDAO();
    private final TempsDeJeuxDAO tempsDeJeuxDAO;
    private final EquipeRencontreDAO equipeRencontreDAO= new EquipeRencontreDAO();
    private final JoueurDAO joueurDAO= new JoueurDAO();
    private final ButRencontreDAO butRencontreDAO= new ButRencontreDAO();
    private final PenaliteDAO penaliteDAO = new PenaliteDAO();
    public RencontreResource() {
		this.tempsDeJeuxDAO = new TempsDeJeuxDAO();
        // Constructor logic
    }
    public RencontreResource(TempsDeJeuxDAO tempsDeJeuxDAO) {
        this.tempsDeJeuxDAO = tempsDeJeuxDAO;
    }
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getAllRencontres(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
            List<Rencontre> rencontres = rencontreDAO.getAllRencontres();
            request.setAttribute("listRencontre", rencontres);
            RequestDispatcher rd = request.getRequestDispatcher("/views/rencontre/rencontre-list.jsp");
            rd.forward(request, response);

            return Response.ok().build();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new WebApplicationException("Error fetching rencontres", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    public Response getRencontre(@PathParam("id") int id) {
        try {
            Rencontre rencontre = rencontreDAO.getRencontreById(id);
            if (rencontre != null) {
                return Response.ok(rencontre).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Rencontre not found").build();
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching rencontre", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    
    

    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public Response showAddNewRencontreForm(@Context HttpServletRequest request, @Context HttpServletResponse response) {
    	List<LieuAcceuil> lieuAcceuils = lieuAcceuilDAO.getAllLieuxAcceuils();
    	List<Equipe> equipes = equipeDao.getAllEquipes();
        request.setAttribute("listLieuAcceuil", lieuAcceuils);
        request.setAttribute("listEquipe", equipes);
        List<Arbitre> allArbitres = arbitreDao.getAllArbitres();
        request.setAttribute("allArbitres", allArbitres);
    	RequestDispatcher rd = request.getRequestDispatcher("/views/rencontre/rencontre-form.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new WebApplicationException("Error showing add new rencontre form", Response.Status.INTERNAL_SERVER_ERROR);
        }
        return Response.ok().build();
    }
    
    

	
	

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addRencontre(
            @FormParam("lieuAccueil") int idLieuAcceuil,
            @FormParam("dateDebut") String dateDebut,
            @FormParam("heureDebut") String heureDebut,
            @FormParam("dateFin") String dateFin,
            @FormParam("heureFin") String heureFin,
            @FormParam("urlBilletrie") String urlBilletrie,
            @FormParam("equipe1") int equipe1Id,
            @FormParam("equipe2") int equipe2Id,
            @FormParam("arbitres") List<Integer> arbitreIds,
            @Context UriInfo uriInfo
    ) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Fetch LieuAcceuil by ID
            LieuAcceuil selectedLieuAcceuil = lieuAcceuilDAO.getLieuAcceuilById(idLieuAcceuil);
            if (selectedLieuAcceuil == null) {
                transaction.rollback();
                return Response.status(Response.Status.BAD_REQUEST).entity("Selected LieuAcceuil not found").build();
            }

            // Parse date and time
            LocalDate parsedDateDebut = convertStringToLocalDate(dateDebut);
            LocalTime parsedHeureDebut = convertStringToLocalTime(heureDebut);
            LocalDate parsedDateFin = convertStringToLocalDate(dateFin);
            LocalTime parsedHeureFin = convertStringToLocalTime(heureFin);

            // Create Rencontre
            Rencontre newRencontre = new Rencontre();
            newRencontre.setLieuAcceuil(selectedLieuAcceuil);
            newRencontre.setDateDebut(parsedDateDebut);
            newRencontre.setHeureDebut(parsedHeureDebut);
            newRencontre.setDateFin(parsedDateFin);
            newRencontre.setHeureFin(parsedHeureFin);
            newRencontre.setUrlBilletrie(urlBilletrie);

            // Save Rencontre first to generate its ID
            session.save(newRencontre);
         // Create EquipeRencontre entries for the Rencontre
            Equipe equipe1 = session.get(Equipe.class, equipe1Id);
            Equipe equipe2 = session.get(Equipe.class, equipe2Id);
            
            EquipeRencontreId equipeRencontreId1 = new EquipeRencontreId(equipe1Id, newRencontre.getIdRencontre());
            EquipeRencontreId equipeRencontreId2 = new EquipeRencontreId(equipe2Id, newRencontre.getIdRencontre());
            
            EquipeRencontre equipeRencontre1 = new EquipeRencontre(equipeRencontreId1, equipe1, newRencontre, true);
            EquipeRencontre equipeRencontre2 = new EquipeRencontre(equipeRencontreId2, equipe2, newRencontre, false);
            
            // Save EquipeRencontre entries to the database
            session.save(equipeRencontre1);
            session.save(equipeRencontre2);
            // Add EquipeRencontre entries to Rencontre
            newRencontre.getEquipeRencontres().add(equipeRencontre1);
            newRencontre.getEquipeRencontres().add(equipeRencontre2);
            // Update Rencontre to cascade changes to EquipeRencontre entries
            session.update(newRencontre);
            
            
         // Create Arbitre entries for the Rencontre
            Set<Arbitre> arbitres = new HashSet<>();
            for (Integer arbitreId : arbitreIds) {
                Arbitre arbitre = session.get(Arbitre.class, arbitreId);
                if (arbitre != null) {
                    arbitre.addRencontre(newRencontre);
                    arbitres.add(arbitre);
                }
            }

            // Update Arbitre instances outside the loop
            for (Arbitre arbitre : arbitres) {
                session.update(arbitre);
            }
            transaction.commit();

            URI uri = uriInfo.getBaseUriBuilder().path("/rencontres").build();
            return Response.seeOther(uri).build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException("Error adding rencontre", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }



    private LocalTime convertStringToLocalTime(String time) {
    	if (time == null || time.trim().isEmpty()) {
            return null; 
        }
    	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
         return LocalTime.parse(time, formatter);
    }



    private LocalDate convertStringToLocalDate(String date) {
    	if  (date == null || date.trim().isEmpty()) {
            return null; 
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    @GET
    @Path("/edit")
    @Produces(MediaType.TEXT_HTML)
    public Response showEditForm(
            @QueryParam("id") int id,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        try {
            Rencontre existingRencontre = rencontreDAO.getRencontreById(id);

            if (existingRencontre != null) {
                

                // Fetch all LieuAccueil objects for the dropdown list
            	List<LieuAcceuil> lieuAcceuils = lieuAcceuilDAO.getAllLieuxAcceuils();
            	List<Equipe> equipes = equipeDao.getAllEquipes();
            	List<Arbitre> allArbitres = arbitreDao.getAllArbitres();
                request.setAttribute("allArbitres", allArbitres);

                RequestDispatcher rd = request.getRequestDispatcher("/views/rencontre/rencontre-form.jsp");
                request.setAttribute("rencontre", existingRencontre);
                request.setAttribute("listLieuAcceuil", lieuAcceuils);
                request.setAttribute("listEquipe", equipes);
                rd.forward(request, response);

                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Rencontre not found").build();
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching rencontre", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateRencontre(
    		@FormParam("idRencontre") int idRencontre,
            @FormParam("lieuAccueil") int idLieuAcceuil,
            @FormParam("dateDebut") String dateDebut,
            @FormParam("heureDebut") String heureDebut,
            @FormParam("dateFin") String dateFin,
            @FormParam("heureFin") String heureFin,
            @FormParam("urlBilletrie") String urlBilletrie,
            @FormParam("arbitres") List<Integer> arbitreIds,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                Rencontre existingRencontre = rencontreDAO.getRencontreById(idRencontre);

                if (existingRencontre == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("Rencontre not found").build();
                }
                
                
                
                // Parse date and time
                LocalDate parsedDateDebut = convertStringToLocalDate(dateDebut);
                LocalTime parsedHeureDebut = convertStringToLocalTime(heureDebut);
                LocalDate parsedDateFin = convertStringToLocalDate(dateFin);
                LocalTime parsedHeureFin = convertStringToLocalTime(heureFin);

     
                existingRencontre.setDateDebut(parsedDateDebut);
                existingRencontre.setHeureDebut(parsedHeureDebut);
                existingRencontre.setDateFin(parsedDateFin);
                existingRencontre.setHeureFin(parsedHeureFin);
                existingRencontre.setUrlBilletrie(urlBilletrie);
                

                // Fetch the associated LieuAcceuil
                LieuAcceuil existingLieuAcceuil = lieuAcceuilDAO.getLieuAcceuilById(idLieuAcceuil);

                if (existingLieuAcceuil == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("LieuAcceuil not found").build();
                }

                // Set the updated LieuAcceuil to the Rencontre
                existingRencontre.setLieuAcceuil(existingLieuAcceuil);
                
             // Update Arbitre entries for the Rencontre
                Set<Arbitre> existingArbitres = existingRencontre.getArbitres();
                Set<Arbitre> newArbitres = new HashSet<>();

                for (Integer arbitreId : arbitreIds) {
                    Arbitre arbitre = session.get(Arbitre.class, arbitreId);
                    if (arbitre != null) {
                        newArbitres.add(arbitre);
                    }
                }

                // Remove arbitres not in the new list
                existingArbitres.removeIf(arbitre -> !newArbitres.contains(arbitre));

                // Add new arbitres to the Rencontre
                existingArbitres.addAll(newArbitres);


                session.saveOrUpdate(existingRencontre);
                transaction.commit();

                // Construct the URI for redirection
                URI uri = uriInfo.getBaseUriBuilder()
                        .path("/rencontres")
                        .build();

                // Redirect to the lieuAcceuil-list page
                return Response.seeOther(uri).build();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                throw new WebApplicationException("Error updating rencontre", Response.Status.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
        	e.printStackTrace();
            throw new WebApplicationException("Error updating rencontre", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/delete")
    @Produces(MediaType.TEXT_HTML)
    public Response deleteRencontre(
            @QueryParam("id") int id,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        try {
            // Start a transaction
            Transaction transaction = null;

            try {
                Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                transaction = session.beginTransaction();

                Rencontre rencontreToDelete = rencontreDAO.getRencontreById(id);

                if (rencontreToDelete == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("Rencontre not found").build();
                }

                // Delete Rencontre
                session.delete(rencontreToDelete);

                
                // Commit the transaction
                transaction.commit();

                // Construct the URI for redirection
                URI uri = uriInfo.getBaseUriBuilder()
                        .path("/rencontres")
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
    
    

    @GET
    @Path("/{id}/details")
    @Produces(MediaType.TEXT_HTML)
    public void getRencontreDetail(@PathParam("id") int id, @Context HttpServletRequest request, @Context HttpServletResponse response) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Rencontre rencontre = rencontreDAO.getRencontreById(id);

            if (rencontre != null) {
//                 Fetch temps de jeux for the rencontre
                List<TempsDeJeux> tempsDeJeux = tempsDeJeuxDAO.getTempsDeJeuxForRencontre(id);
//                 Fetch participants for the rencontre
                List<Participant> participants = participantDAO.getParticipantsByRencontre(id);

                // Initialize collections
                Hibernate.initialize(rencontre.getEquipeRencontres());
                Hibernate.initialize(rencontre.getArbitres());
                Hibernate.initialize(rencontre.getTempsDeJeux());
                Hibernate.initialize(rencontre.getParticipants());
//                 Set attributes for the JSP
                request.setAttribute("rencontre", rencontre);
                request.setAttribute("tempsDeJeux", tempsDeJeux);
                request.setAttribute("participants", participants);
                request.setAttribute("showParticipants", true);
                request.setAttribute("showlistTemps", true);
                

//                // Forward to the JSP page
                RequestDispatcher rd = request.getRequestDispatcher("/views/rencontre/rencontre-details.jsp");
                rd.forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Rencontre not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching rencontre");
            } catch (IOException ioException) {
                ioException.printStackTrace();
           }
        }
   }
//    @GET
//    @Path("/{id}/details")
//    @Produces(MediaType.TEXT_HTML)
//    public void getRencontreDetail(@PathParam("id") int id, @Context HttpServletRequest request, @Context HttpServletResponse response) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Rencontre rencontre = rencontreDAO.getRencontreById(id);
//
//            if (rencontre != null) {
//                // Fetch temps de jeux for the rencontre
//                List<TempsDeJeux> tempsDeJeux = tempsDeJeuxDAO.getTempsDeJeuxForRencontre(id);
//                // Fetch equipeRencontres for the rencontre
//                List<EquipeRencontre> equipeRencontres = equipeRencontreDAO.getEquipeRencontresForRencontre(id);
//
//                // Initialize collections
//                Hibernate.initialize(rencontre.getArbitres());
//                Hibernate.initialize(rencontre.getTempsDeJeux());
//                
//                // Set attributes for the JSP
//                request.setAttribute("rencontre", rencontre);
//                request.setAttribute("tempsDeJeux", tempsDeJeux);
//                request.setAttribute("equipeRencontres", equipeRencontres);
//                request.setAttribute("showParticipantsTemps", true);
//                request.setAttribute("showParticipants", true);
//                // Forward to the JSP page
//                RequestDispatcher rd = request.getRequestDispatcher("/views/rencontre/rencontre-details.jsp");
//                rd.forward(request, response);
//            } else {
//                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Rencontre not found");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching rencontre");
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//        }
//    }

    
    //temps de Jeux
    @GET 
    @Path("/{idRencontre}/details/tempsdejeux")
    @Produces(MediaType.TEXT_HTML)
    public Response getAllTempsDeJeuxs(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
            List<TempsDeJeux> tempsDeJeux = tempsDeJeuxDAO.getAllTempsDeJeux();
            request.setAttribute("listTempsDeJeux", tempsDeJeux);
            request.setAttribute("showParticipantsTemps", true);
            // Forward to the JSP page
            RequestDispatcher rd = request.getRequestDispatcher("/views/rencontre/rencontre-details.jsp");
    
           // RequestDispatcher rd = request.getRequestDispatcher("/list-todo.jsp");
            rd.forward(request, response);
            // Returning Response.ok() to satisfy JAX-RS requirements
            return Response.ok().build();
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching tempsDeJeux", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{idRencontre}/details/tempsdejeux/{id}")
    public Response getTempsDeJeux(@PathParam("idRencontre") int idRencontre,@PathParam("id") int id) {
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
    @Path("/{idRencontre}/details/tempsdejeux/new")
    @Produces(MediaType.TEXT_HTML)
    public void showAddNewTempsDeJeuxForm(
        @PathParam("idRencontre") int idRencontre,
        @Context HttpServletRequest request,
        @Context HttpServletResponse response
    ) {
    	
    	 try (Session session = HibernateUtil.getSessionFactory().openSession()) {
             Rencontre rencontre = rencontreDAO.getRencontreById(idRencontre);

             if (rencontre != null) {
                 
                 // Initialize collections
                 Hibernate.initialize(rencontre.getEquipeRencontres());
                 Hibernate.initialize(rencontre.getArbitres());
                 Hibernate.initialize(rencontre.getTempsDeJeux());

                 // Set attributes for the JSP
                 request.setAttribute("rencontre", rencontre);
                 request.setAttribute("showParticipantsTemps", false);
                 // Forward to the JSP page
                 RequestDispatcher rd = request.getRequestDispatcher("/views/rencontre/rencontre-details.jsp");
                 rd.forward(request, response);
             } else {
                 response.sendError(HttpServletResponse.SC_NOT_FOUND, "Rencontre not found");
             }
         } catch (Exception e) {
             e.printStackTrace();
             try {
                 response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching rencontre");
             } catch (IOException ioException) {
                 ioException.printStackTrace();
             }
         }
    }

    @POST
    @Path("/{idRencontre}/details/tempsdejeux/insert")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addTempsDeJeux(
            @FormParam("heureDebut") String heureDebut,
            @FormParam("heureFin") String heureFin,
            @FormParam("numero") String numero,
            @PathParam("idRencontre") int idRencontre,
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
            newTempsDeJeux.setNumero(numero);
            
            // Fetching the Rencontre object using its ID
            Rencontre rencontre = rencontreDAO.getRencontreById(idRencontre);
            newTempsDeJeux.setRencontre(rencontre);

            // Saving the new TempsDeJeux object
            tempsDeJeuxDAO.saveTempsDeJeux(newTempsDeJeux);


            URI uri = uriInfo.getBaseUriBuilder()
                    .path("/rencontres/{idRencontre}/details")
                    .resolveTemplate("idRencontre", idRencontre)
                    .build();

            // Redirecting to the tempsDeJeux-list page
            return Response.seeOther(uri).build();
        } catch (Exception e) {
            throw new WebApplicationException("Error adding tempsDeJeux", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

  

	@GET
	@Path("/{idRencontre}/details/tempsdejeux/edit")
	@Produces(MediaType.TEXT_HTML)
	public Response showEditForm(@PathParam("idRencontre") int idRencontre,@QueryParam("id") int id, @Context UriInfo uriInfo, @Context HttpServletRequest request, @Context HttpServletResponse response) {
	    try {
	        System.out.println("edit");
	        TempsDeJeux existingTempsDeJeux = tempsDeJeuxDAO.getTempsDeJeuxById(id);
	        Rencontre rencontre = rencontreDAO.getRencontreById(idRencontre);

	        if (existingTempsDeJeux != null) {
	        	request.setAttribute("showParticipantsTemps", false);
	            // Forward to the JSP page
	            RequestDispatcher rd = request.getRequestDispatcher("/views/rencontre/rencontre-details.jsp");
	        	
	            request.setAttribute("tempsDeJeux", existingTempsDeJeux);
	            request.setAttribute("rencontre", rencontre);
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
	@Path("/{idRencontre}/details/tempsdejeux/update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateTempsDeJeux(
			@PathParam("idRencontre") int idRencontre,
	        @FormParam("idtempsDeJeux") int id,
	        @FormParam("heureDebut") String heureDebut,
	        @FormParam("heureFin") String heureFin,
	        @FormParam("numero") String numero,
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
	        existingTempsDeJeux.setNumero(numero);

	        Rencontre rencontre = rencontreDAO.getRencontreById(idRencontre);
	        existingTempsDeJeux.setRencontre(rencontre);

	        tempsDeJeuxDAO.updateTempsDeJeux(existingTempsDeJeux);

	        
	        URI uri = uriInfo.getBaseUriBuilder()
	                .replacePath("/matchSportif/api/rencontres/" + idRencontre + "/details")
	                .build();

	        return Response.seeOther(uri).build();
	    } catch (Exception e) {
	    	e.printStackTrace();
	        throw new WebApplicationException("Error updating tempsDeJeux", Response.Status.INTERNAL_SERVER_ERROR);
	    }
	}




    @GET
    @Path("/{idRencontre}/details/tempsdejeux/delete")
    @Produces(MediaType.TEXT_HTML)
    public Response deleteTempsDeJeux(
    		@PathParam("idRencontre") int idRencontre,
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
	            transaction.commit();
	            // Construct the URI for redirection
	            URI uri = uriInfo.getBaseUriBuilder()
		                .replacePath("/matchSportif/api/rencontres/" + idRencontre + "/details")
		                .build();
	         // Redirect to the lieuAcceuil-list page
                return Response.seeOther(uri).build();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            	e.printStackTrace();

                throw new WebApplicationException("Error deleting tempsDeJeux", Response.Status.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
        	e.printStackTrace();
            throw new WebApplicationException("Error deleting  tempsDeJeux", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    //participant
    @GET
    @Path("/{idRencontre}/details/participants")
    @Produces(MediaType.TEXT_HTML)
    public Response getAllParticipants(
        @PathParam("idRencontre") int idRencontre,
        @QueryParam("idEquipe") int idEquipe,
        @Context HttpServletRequest request,
        @Context HttpServletResponse response
    ) {
        try {
            Rencontre rencontre = rencontreDAO.getRencontreById(idRencontre);
            if (rencontre == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Rencontre not found").build();
            }
         // Fetch participants for the first team if equipeRencontres contains at least one element
            List<Participant> team1Participants = null;
            if (!rencontre.getEquipeRencontres().isEmpty()) {
                EquipeRencontre firstTeamEquipeRencontre = rencontre.getEquipeRencontres().iterator().next();
                team1Participants = participantDAO.getParticipantsByEquipeAndRencontre(firstTeamEquipeRencontre.getEquipe().getIdEquipe(), idRencontre);
                
            }
            
     
            // Fetch participants for the second team if equipeRencontres contains at least two elements
            List<Participant> team2Participants = null;
            if (rencontre.getEquipeRencontres().size() > 1) {
                Iterator<EquipeRencontre> iterator = rencontre.getEquipeRencontres().iterator();
                iterator.next(); // Skip the first element
                EquipeRencontre secondTeamEquipeRencontre = iterator.next();
                team2Participants = participantDAO.getParticipantsByEquipeAndRencontre(secondTeamEquipeRencontre.getEquipe().getIdEquipe(), idRencontre);
            }


            request.setAttribute("team1Participants", team1Participants);
            request.setAttribute("team2Participants", team2Participants);
            request.setAttribute("showParticipants", true);
            RequestDispatcher rd = request.getRequestDispatcher("/views/rencontre/rencontre-details.jsp");
            rd.forward(request, response);
            
            return Response.ok().build();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new WebApplicationException("Error fetching participants", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

//    @GET
//    @Path("/{idRencontre}/details/participants/{id}")
//    public Response getParticipant(
//        @PathParam("idRencontre") int idRencontre,
//        @PathParam("id") int id
//    ) {
//        try {
//            Participant participant = participantDAO.getParticipantById(id);
//            if (participant != null) {
//                return Response.ok(participant).build();
//            } else {
//                return Response.status(Response.Status.NOT_FOUND).entity("Participant not found").build();
//            }
//        } catch (Exception e) {
//            throw new WebApplicationException("Error fetching participant", Response.Status.INTERNAL_SERVER_ERROR);
//        }
//    }

    
    @GET
    @Path("/{idRencontre}/details/equipe/{idEquipe}/participants/new")
    @Produces(MediaType.TEXT_HTML)
    public void showAddNewParticipantForm(
        @PathParam("idRencontre") int idRencontre,
        @PathParam("idEquipe") int idEquipe,
        @Context HttpServletRequest request,
        @Context HttpServletResponse response
    ) {
        try {
            // Fetch rencontre object
            Rencontre rencontre = rencontreDAO.getRencontreById(idRencontre);
            // Check if the rencontre exists
            if (rencontre == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Rencontre not found");
                return;
            }

            // Fetch participants for the specified equipe and rencontre
            List<Participant> participants = participantDAO.getParticipantsByEquipeAndRencontre(idEquipe, idRencontre);
            List<TempsDeJeux> tempsDeJeux = tempsDeJeuxDAO.getAllTempsDeJeux();
            // Fetch players for the specified team
            List<Joueur> teamPlayers = joueurDAO.getPlayersByTeam(idEquipe);

            // Initialize collections
            Hibernate.initialize(rencontre.getEquipeRencontres());
            Hibernate.initialize(rencontre.getArbitres());
            Hibernate.initialize(rencontre.getParticipants());
            Hibernate.initialize(rencontre.getTempsDeJeux());
            // Set attributes for the JSP
            request.setAttribute("idEquipe", idEquipe);
            request.setAttribute("rencontre", rencontre);
            request.setAttribute("participants", participants);
            request.setAttribute("listTempsDeJeux", tempsDeJeux);
            request.setAttribute("teamPlayers", teamPlayers);
            request.setAttribute("showParticipants", false);
            request.setAttribute("showlistTemps", true);
            // Forward to the JSP page
            RequestDispatcher rd = request.getRequestDispatcher("/views/rencontre/rencontre-details.jsp");
            try {
				rd.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (IOException e) {
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching rencontre");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }



    @POST
    @Path("/{idRencontre}/details/equipe/{idEquipe}/participants/insert")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addParticipant(
        @FormParam("heureEntree") String heureEntreeString,
        @FormParam("heureSortie") String heureSortieString,
        @PathParam("idRencontre") int idRencontre,
        @PathParam("idEquipe") int idEquipe,
        @FormParam("selectedPlayer") int selectedPlayerId,
        @Context UriInfo uriInfo
    ) {
        try {
            // Parse LocalDateTime from the string inputs
            LocalTime heureEntree = convertStringToLocalTime(heureEntreeString);
            LocalTime heureSortie = convertStringToLocalTime(heureSortieString);
            // Fetch rencontre object
            Rencontre rencontre = rencontreDAO.getRencontreById(idRencontre);

            // Check if the rencontre exists
            if (rencontre == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Rencontre not found").build();
            }

            // Fetch the selected player by ID
            Joueur selectedPlayer = joueurDAO.getJoueurById(selectedPlayerId);

            // Create a new ParticipantId object
            ParticipantId participantId = new ParticipantId();
            participantId.setIdRencontre(idRencontre);
            participantId.setIdJoueur(selectedPlayerId);

            // Create a new Participant object with the ParticipantId
            Participant newParticipant = new Participant();
            newParticipant.setId(participantId);
            newParticipant.setHeureEntree(heureEntree);
            newParticipant.setHeureSortie(heureSortie);
            newParticipant.setRencontre(rencontre);
            newParticipant.setJoueur(selectedPlayer);

            // Save the new participant object
            participantDAO.saveParticipant(newParticipant);

            // Construct the URI for redirection
            URI uri = uriInfo.getBaseUriBuilder()
                .path("/rencontres/{idRencontre}/details")
                .resolveTemplate("idRencontre", idRencontre)
                .build();

            // Redirect to the participants-list page
            return Response.seeOther(uri).build();
        } catch (DateTimeParseException e) {
        	e.printStackTrace();
            // Handle parsing errors
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid date-time format").build();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle other exceptions
            throw new WebApplicationException("Error adding participant", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    @GET
    @Path("/{idRencontre}/details/equipe/{idEquipe}/participants/edit")
    @Produces(MediaType.TEXT_HTML)
    public Response showEditParticipantForm(
            @PathParam("idRencontre") int idRencontre,
            @PathParam("idEquipe") int idEquipe,
            @QueryParam("idJoueur") int idJoueur,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        try {
            
         // Create a new ParticipantId object
            Rencontre rencontre = rencontreDAO.getRencontreById(idRencontre);

            // Check if the rencontre exists
            if (rencontre == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Rencontre not found").build();
            }

            // Fetch the selected player by ID
            Joueur selectedPlayer = joueurDAO.getJoueurById(idJoueur);

            // Create a new ParticipantId object
            ParticipantId participantId = new ParticipantId();
            participantId.setIdRencontre(idRencontre);
            participantId.setIdJoueur(idJoueur);
            Participant existingParticipant = participantDAO.getParticipantById(participantId);
            if (existingParticipant != null) {
            	 List<Joueur> teamPlayers = joueurDAO.getPlayersByTeam(idEquipe);
            	 request.setAttribute("teamPlayers", teamPlayers);
            	 request.setAttribute("idEquipe", idEquipe);
            	 request.setAttribute("idJoueur", idJoueur);
                request.setAttribute("showParticipants", false);
                request.setAttribute("participant", existingParticipant);
                request.setAttribute("rencontre", rencontre); 
                RequestDispatcher rd = request.getRequestDispatcher("/views/rencontre/rencontre-details.jsp");
                rd.forward(request, response);
                return Response.ok().build();
                } else {
                	return Response.status(Response.Status.NOT_FOUND).entity("Rencontre not found").build();
                }
           
        } catch (Exception e) {
        	e.printStackTrace();
            throw new WebApplicationException("Error fetching participant", e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    
    @POST
    @Path("/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateParticipant(
            @PathParam("idRencontre") int idRencontre,
            @PathParam("idEquipe") int idEquipe,
            @PathParam("idJoueur") int idJoueur,
            @FormParam("heureEntree") String heureEntree,
            @FormParam("heureSortie") String heureSortie,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        try {
            // Fetch existing participant by ID
            ParticipantId participantId = new ParticipantId();
            participantId.setIdRencontre(idRencontre);
            participantId.setIdJoueur(idJoueur);
            Participant existingParticipant = participantDAO.getParticipantById(participantId);
            
            // Check if participant exists
            if (existingParticipant == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Participant not found").build();
            }
            LocalTime parsedheureEntree = convertStringToLocalTime(heureEntree);
            LocalTime parsedheureSortie = convertStringToLocalTime(heureSortie);
            // Update participant attributes
            existingParticipant.setHeureEntree(parsedheureEntree);
            existingParticipant.setHeureSortie(parsedheureSortie);

            // Fetch rencontre object
            Rencontre rencontre = rencontreDAO.getRencontreById(idRencontre);
            existingParticipant.setRencontre(rencontre);

            // Update participant in the database
            participantDAO.updateParticipant(existingParticipant);

            // Redirect to the details page after successful update
            URI uri = uriInfo.getBaseUriBuilder()
                    .path("/rencontres/{idRencontre}/details")
                    .resolveTemplate("idRencontre", idRencontre)
                    .build();
            return Response.seeOther(uri).build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException("Error updating participant", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    
//    @GET
//    @Path("/{idRencontre}/details/participants/delete")
//    @Produces(MediaType.TEXT_HTML)
//    public Response deleteParticipant(
//        @PathParam("idRencontre") int idRencontre,
//        @QueryParam("id") int id,
//        @Context UriInfo uriInfo,
//        @Context HttpServletRequest request,
//        @Context HttpServletResponse response
//    ) {
//        try {
//            Transaction transaction = null;
//            try {
//                Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//                transaction = session.beginTransaction();
//                Participant participantToDelete = participantDAO.getParticipantById(id);
//                if (participantToDelete == null) {
//                    return Response.status(Response.Status.NOT_FOUND).entity("Participant not found").build();
//                }
//                session.delete(participantToDelete);
//                transaction.commit();
//                URI uri = uriInfo.getBaseUriBuilder()
//                    .replacePath("/matchSportif/api/rencontres/" + idRencontre + "/details")
//                    .build();
//                return Response.seeOther(uri).build();
//            } catch (Exception e) {
//                if (transaction != null) {
//                    transaction.rollback();
//                }
//                e.printStackTrace();
//                throw new WebApplicationException("Error deleting participant", Response.Status.INTERNAL_SERVER_ERROR);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new WebApplicationException("Error deleting participant", Response.Status.INTERNAL_SERVER_ERROR);
//        }
//    }


    @GET
    @Path("/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details")
    @Produces(MediaType.TEXT_HTML)
    public void getPArticipantDetail(@PathParam("idRencontre") int idRencontre, @PathParam("idEquipe") int idEquipe,@PathParam("idJoueur") int idJoueur,
    		@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Rencontre rencontre = rencontreDAO.getRencontreById(idRencontre);

            if (rencontre != null) {
//                 Fetch temps de jeux for the rencontre
                List<TempsDeJeux> tempsDeJeux = tempsDeJeuxDAO.getTempsDeJeuxForRencontre(idRencontre);
//                 Fetch participants for the rencontre
                List<Participant> participants = participantDAO.getParticipantsByRencontre(idRencontre);
                Joueur selectedPlayer = joueurDAO.getJoueurById(idJoueur);
                
                List<ButRencontre> butRencontreList = butRencontreDAO.getAllButRencontreForPlayer(idRencontre, idJoueur);
                

                List<Penalite> penaliteList = penaliteDAO.getAllPenalitesForPlayer(idRencontre, idJoueur);
                
                // Set the retrieved list as an attribute in the request
                request.setAttribute("butRencontre", butRencontreList);
                request.setAttribute("listPenalite", penaliteList);

           
                // Initialize collections
                Hibernate.initialize(rencontre.getEquipeRencontres());
                Hibernate.initialize(rencontre.getButRencontres());
                Hibernate.initialize(rencontre.getArbitres());
                Hibernate.initialize(rencontre.getTempsDeJeux());
                Hibernate.initialize(rencontre.getParticipants());
//                 Set attributes for the JSP
                request.setAttribute("rencontre", rencontre);
                request.setAttribute("tempsDeJeux", tempsDeJeux);
                request.setAttribute("participants", participants);
                request.setAttribute("joueur", selectedPlayer);
                request.setAttribute("showButParticipants", true);
                request.setAttribute("showPinalite", true);
                request.setAttribute("idRencontre", idRencontre);
                request.setAttribute("idEquipe", idEquipe);
                request.setAttribute("idJoueur", idJoueur);
                request.setAttribute("butRencontreList", butRencontreList);
//                // Forward to the JSP page
                RequestDispatcher rd = request.getRequestDispatcher("/views/rencontre/rencontre-participant-details.jsp");
                rd.forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Rencontre not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching rencontre");
            } catch (IOException ioException) {
                ioException.printStackTrace();
           }
        }
   }    
    
    //But Rencontre
    @GET 
    @Path("/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details/butRencontre")
    @Produces(MediaType.TEXT_HTML)
    public Response getAllButRencontreForPlayer(
            @PathParam("idRencontre") int idRencontre, 
            @PathParam("idEquipe") int idEquipe,
            @PathParam("idJoueur") int idJoueur,
            @Context HttpServletRequest request, 
            @Context HttpServletResponse response) {
        try {
            // Fetch all goals for the specified player in the encounter
            List<ButRencontre> butRencontreList = butRencontreDAO.getAllButRencontreForPlayer(idRencontre, idJoueur);
            // Set the retrieved list as an attribute in the request
            request.setAttribute("butRencontreList", butRencontreList);
            request.setAttribute("idRencontre", idRencontre);
            request.setAttribute("idEquipe", idEquipe);
            request.setAttribute("idJoueur", idJoueur);
            request.setAttribute("showButParticipants",true);
            // Forward the request to the JSP page
            RequestDispatcher rd = request.getRequestDispatcher("/views/rencontre/rencontre-participant-details.jsp");
            rd.forward(request, response);

            // Returning Response.ok() to satisfy JAX-RS requirements
            return Response.ok().build();
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching butRencontre", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details/butRencontre/new")
    @Produces(MediaType.TEXT_HTML)
    public void showAddNewButRencontreForPlayerForm(
    		@PathParam("idRencontre") int idRencontre, 
            @PathParam("idEquipe") int idEquipe,
            @PathParam("idJoueur") int idJoueur,
        @Context HttpServletRequest request,
        @Context HttpServletResponse response
    ) {
    	
    	 try (Session session = HibernateUtil.getSessionFactory().openSession()) {
             Rencontre rencontre = rencontreDAO.getRencontreById(idRencontre);
             Joueur joueur = joueurDAO.getJoueurById(idJoueur);
             if (rencontre != null) {
            	 List<Participant> participants = participantDAO.getParticipantsByRencontre(idRencontre);
            	 Set<Arbitre> arbitres = rencontre.getArbitres();
                 // Initialize collections
                 Hibernate.initialize(rencontre.getEquipeRencontres());
                 Hibernate.initialize(rencontre.getArbitres());
                 Hibernate.initialize(rencontre.getTempsDeJeux());
                 Hibernate.initialize(rencontre.getButRencontres());
                 Hibernate.initialize(rencontre.getParticipants());
                 // Set attributes for the JSP
                 request.setAttribute("participants", participants);
                 request.setAttribute("joueur", joueur);
                 request.setAttribute("arbitres", arbitres);
                 request.setAttribute("rencontre", rencontre);
                 request.setAttribute("idRencontre", idRencontre);
                 request.setAttribute("idEquipe", idEquipe);
                 request.setAttribute("idJoueur", idJoueur);
                 request.setAttribute("showButParticipants", false);
                 // Forward to the JSP page
                 RequestDispatcher rd = request.getRequestDispatcher("/views/rencontre/rencontre-participant-details.jsp");
                 rd.forward(request, response);
             } else {
                 response.sendError(HttpServletResponse.SC_NOT_FOUND, "Rencontre not found");
             }
         } catch (Exception e) {
             e.printStackTrace();
             try {
                 response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching but rencontre participant");
             } catch (IOException ioException) {
                 ioException.printStackTrace();
             }
         }
    }
    @POST
    @Path("/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details/butRencontre/insert")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addButRencontreForPlayer(
        @FormParam("heure") String heure,
        @FormParam("typeAction") String typeAction,
        @PathParam("idRencontre") int idRencontre, 
        @PathParam("idEquipe") int idEquipe,
        @PathParam("idJoueur") int idJoueur,
        @Context UriInfo uriInfo,
        @Context HttpServletRequest request,
        @Context HttpServletResponse response
    ) {
        try {
            // Parsing string input to LocalTime
            LocalTime localHeure = LocalTime.parse(heure);

            // Creating a new ButRencontre object
            ButRencontre newButRencontre = new ButRencontre();
            newButRencontre.setHeure(localHeure);
            newButRencontre.setTypeAction(typeAction);

            // Fetching the Rencontre and Joueur objects using their IDs
            Rencontre rencontre = rencontreDAO.getRencontreById(idRencontre);
            Joueur joueur = joueurDAO.getJoueurById(idJoueur);
            newButRencontre.setRencontre(rencontre);
            newButRencontre.setJoueur(joueur);

            // Saving the new ButRencontre object
            butRencontreDAO.saveButRencontre(newButRencontre);
        


            URI uri = uriInfo.getBaseUriBuilder()
            	    .path("/rencontres/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details")
            	    .resolveTemplate("idRencontre", idRencontre)
            	    .resolveTemplate("idEquipe", idEquipe)
            	    .resolveTemplate("idJoueur", idJoueur)
            	    .build();


            // Redirecting to the butRencontre-list page
            return Response.seeOther(uri).build();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new WebApplicationException("Error adding butRencontre", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details/butRencontre/edit")
    @Produces(MediaType.TEXT_HTML)
    public Response showEditForm(
            @PathParam("idRencontre") int idRencontre,
            @PathParam("idEquipe") int idEquipe,
            @PathParam("idJoueur") int idJoueur, 
            @QueryParam("idButRencontre") int idButRencontre,
            @Context HttpServletRequest request, 
            @Context HttpServletResponse response) {
        try {
            System.out.println("edit");
            
            ButRencontre existingButRencontre = butRencontreDAO.getButRencontre(idButRencontre);

            if (existingButRencontre != null) {
            	   List<Participant> participants = participantDAO.getParticipantsByRencontre(idRencontre);

                   List<Penalite> penaliteList = penaliteDAO.getAllPenalitesForPlayer(idRencontre, idJoueur);
                // Forward to the JSP page
            request.setAttribute("idRencontre", idRencontre);
            request.setAttribute("idButRencontre", idButRencontre);
            request.setAttribute("idEquipe", idEquipe);
            request.setAttribute("idJoueur", idJoueur);
            
            request.setAttribute("participants", participants);
            request.setAttribute("penaliteList", penaliteList);
            request.setAttribute("showPinalite",true);
                RequestDispatcher rd = request.getRequestDispatcher("/views/rencontre/rencontre-participant-details.jsp");
                request.setAttribute("butRencontre", existingButRencontre);
                rd.forward(request, response);

                // Returning Response.ok() to satisfy JAX-RS requirements
                return Response.ok().build();
            } else {
                // ButRencontre not found, return a 404 response
                return Response.status(Response.Status.NOT_FOUND).entity("ButRencontre not found").build();
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching ButRencontre", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    
    
    @POST
    @Path("/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details/butRencontre/{idButRencontre}/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateButRencontreForPlayer(
            @FormParam("heure") String heure,
            @FormParam("typeAction") String typeAction,
            @PathParam("idRencontre") int idRencontre,
            @PathParam("idEquipe") int idEquipe,
            @PathParam("idJoueur") int idJoueur,
            @PathParam("idButRencontre") int idButRencontre,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        try {
            ButRencontre existingButRencontre = butRencontreDAO.getButRencontre(idButRencontre); 

            if (existingButRencontre == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("ButRencontre not found").build();
            }

            LocalTime localHeure = LocalTime.parse(heure);

            existingButRencontre.setHeure(localHeure);
            existingButRencontre.setTypeAction(typeAction);

            Rencontre rencontre = rencontreDAO.getRencontreById(idRencontre);
            Joueur joueur = joueurDAO.getJoueurById(idJoueur);

            existingButRencontre.setRencontre(rencontre);
            existingButRencontre.setJoueur(joueur);

            butRencontreDAO.updateButRencontre(existingButRencontre);

            // Redirect to the details page after updating
            URI uri = uriInfo.getBaseUriBuilder()
            		.path("/rencontres/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details")
            	    .resolveTemplate("idRencontre", idRencontre)
            	    .resolveTemplate("idEquipe", idEquipe)
            	    .resolveTemplate("idJoueur", idJoueur)
            	    .build();

            return Response.seeOther(uri).build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException("Error updating ButRencontre", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    @GET
    @Path("/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details/butRencontre/delete")
    @Produces(MediaType.TEXT_HTML)
    public Response deleteButRencontre(
    		@PathParam("idRencontre") int idRencontre,
            @PathParam("idEquipe") int idEquipe,
            @PathParam("idJoueur") int idJoueur,
            @QueryParam("idButRencontre") int idButRencontre,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response) {
        try {
            butRencontreDAO.deleteButRencontre(idButRencontre);

            // Construct the URI for redirection
            URI uri = uriInfo.getBaseUriBuilder()
            		.path("/rencontres/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details")
            	    .resolveTemplate("idRencontre", idRencontre)
            	    .resolveTemplate("idEquipe", idEquipe)
            	    .resolveTemplate("idJoueur", idJoueur)
            	    .build();

            // Redirect to the butRencontre-list page
            return Response.seeOther(uri).build();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new WebApplicationException("Error deleting but rencontre", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    //Penalite
    @GET 
    @Path("/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details/Penalite")
    @Produces(MediaType.TEXT_HTML)
    public Response getAllPenalitesForPlayer(
        @PathParam("idRencontre") int idRencontre, 
        @PathParam("idEquipe") int idEquipe,
        @PathParam("idJoueur") int idJoueur,
        @Context HttpServletRequest request, 
        @Context HttpServletResponse response) {
        try {
            // Fetch all penalites for the specified player in the encounter
            List<Penalite> penaliteList = penaliteDAO.getAllPenalitesForPlayer(idRencontre, idJoueur);
            
            // Set the retrieved list as an attribute in the request
            request.setAttribute("penaliteList", penaliteList);
            request.setAttribute("idRencontre", idRencontre);
            request.setAttribute("idEquipe", idEquipe);
            request.setAttribute("idJoueur", idJoueur);
            // Forward the request to the JSP page
            RequestDispatcher rd = request.getRequestDispatcher("/views/penalite/penalite-list.jsp");
            rd.forward(request, response);

            // Returning Response.ok() to satisfy JAX-RS requirements
            return Response.ok().build();
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching penalites", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    @GET
    @Path("/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details/penalite/new")
    @Produces(MediaType.TEXT_HTML)
    public void showAddNewPenalitePlayerForm(
    		@PathParam("idRencontre") int idRencontre, 
            @PathParam("idEquipe") int idEquipe,
            @PathParam("idJoueur") int idJoueur,
        @Context HttpServletRequest request,
        @Context HttpServletResponse response
    ) {
    	
    	
    	 try (Session session = HibernateUtil.getSessionFactory().openSession()) {
             Rencontre rencontre = rencontreDAO.getRencontreById(idRencontre);

             if (rencontre != null) {
            	 List<Participant> participants = participantDAO.getParticipantsByRencontre(idRencontre);
            	 List<TempsDeJeux> tempsDeJeux = tempsDeJeuxDAO.getTempsDeJeuxForRencontre(idRencontre);
//               Fetch participants for the rencontre
	              Joueur selectedPlayer = joueurDAO.getJoueurById(idJoueur);
	              
	              List<ButRencontre> butRencontreList = butRencontreDAO.getAllButRencontreForPlayer(idRencontre, idJoueur);
	              
	              
	              List<Penalite> penaliteList = penaliteDAO.getAllPenalitesForPlayer(idRencontre, idJoueur);
	              
	              // Set the retrieved list as an attribute in the request
	              request.setAttribute("butRencontre", butRencontreList);
	              request.setAttribute("penaliteList", penaliteList);

               
	              Set<Arbitre> arbitres = rencontre.getArbitres(); 
	                request.setAttribute("listArbitre", arbitres);
//                 // Initialize collections
//                 Hibernate.initialize(rencontre.getEquipeRencontres());
//                 Hibernate.initialize(rencontre.getArbitres());
//                 Hibernate.initialize(rencontre.getTempsDeJeux());
//                 Hibernate.initialize(rencontre.getButRencontres());
//                 Hibernate.initialize(rencontre.getParticipants());
//                 Hibernate.initialize(rencontre.getPenalites());

                 // Set attributes for the JSP
                 request.setAttribute("participants", participants);
                 request.setAttribute("idRencontre", idRencontre);
                 request.setAttribute("idEquipe", idEquipe);
                 request.setAttribute("idJoueur", idJoueur);
                 request.setAttribute("rencontre", rencontre);
                 request.setAttribute("tempsDeJeux", tempsDeJeux);
                 request.setAttribute("participants", participants);
                 request.setAttribute("joueur", selectedPlayer);
                 request.setAttribute("showPenalite", false);
                 request.setAttribute("showButParticipants", true);
                 // Forward to the JSP page
                 RequestDispatcher rd = request.getRequestDispatcher("/views/rencontre/rencontre-participant-details.jsp");
                 rd.forward(request, response);
             } else {
                 response.sendError(HttpServletResponse.SC_NOT_FOUND, "Rencontre not found");
             }
         } catch (Exception e) {
             e.printStackTrace();
             try {
                 response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching but rencontre participant");
             } catch (IOException ioException) {
                 ioException.printStackTrace();
             }
         }
    }
    @POST
    @Path("/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details/penalite/insert")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addPenaliteForPlayer(
        @FormParam("libelle") String libelle,
        @FormParam("id") int idArbitre,
        @FormParam("heure") String heure,
        @FormParam("provisoire") boolean provisoire,
        @FormParam("definitive") boolean definitive,
        @PathParam("idRencontre") int idRencontre, 
        @PathParam("idEquipe") int idEquipe,
        @PathParam("idJoueur") int idJoueur,
        @Context UriInfo uriInfo,
        @Context HttpServletRequest request,
        @Context HttpServletResponse response
    ) {
        try {
            // Parsing string input to LocalTime
            LocalTime localHeure = LocalTime.parse(heure);
            Arbitre arbitre = arbitreDao.getArbitre(idArbitre);
            // Creating a new Penalite object
            Penalite newPenalite = new Penalite();
            newPenalite.setLibelle(libelle);
            newPenalite.setHeure(localHeure);
            newPenalite.setProvisoire(provisoire);
            newPenalite.setDefinitive(definitive);
            newPenalite.setArbitre(arbitre);
            // Fetching the Rencontre and Joueur objects using their IDs
            Rencontre rencontre = rencontreDAO.getRencontreById(idRencontre);
            Joueur joueur = joueurDAO.getJoueurById(idJoueur);
            newPenalite.setRencontre(rencontre);
            newPenalite.setJoueur(joueur);

            // Saving the new Penalite object
            penaliteDAO.savePenalite(newPenalite);

            // Constructing the URI for redirection
            URI uri = uriInfo.getBaseUriBuilder()
                .path("/rencontres/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details")
                .resolveTemplate("idRencontre", idRencontre)
                .resolveTemplate("idEquipe", idEquipe)
                .resolveTemplate("idJoueur", idJoueur)
                .build();

            // Redirecting to the details page
            return Response.seeOther(uri).build();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new WebApplicationException("Error adding penalite", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details/penalite/edit")
    @Produces(MediaType.TEXT_HTML)
    public Response showEditPenaliteForm(
            @PathParam("idRencontre") int idRencontre,
            @PathParam("idEquipe") int idEquipe,
            @PathParam("idJoueur") int idJoueur, 
            @QueryParam("idPenalite") int idPenalite,
            @Context HttpServletRequest request, 
            @Context HttpServletResponse response) {
        try {
            System.out.println("edit");
            
            Penalite existingPenalite = penaliteDAO.getPenaliteById(idPenalite);

            if (existingPenalite != null) {
                // Forward to the JSP page
            request.setAttribute("idRencontre", idRencontre);
            request.setAttribute("idPenalite", idPenalite);
            request.setAttribute("idEquipe", idEquipe);
            request.setAttribute("idJoueur", idJoueur);
                RequestDispatcher rd = request.getRequestDispatcher("/views/rencontre/rencontre-participant-details.jsp");
                request.setAttribute("penalite", existingPenalite);
                rd.forward(request, response);

                // Returning Response.ok() to satisfy JAX-RS requirements
                return Response.ok().build();
            } else {
                // ButRencontre not found, return a 404 response
                return Response.status(Response.Status.NOT_FOUND).entity("ButRencontre not found").build();
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching ButRencontre", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    
    
    @POST
    @Path("/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details/penalite/{idPenalite}/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updatePenaliteForPlayer(
            @FormParam("libelle") String libelle,
            @FormParam("heure") String heure,
            @FormParam("provisoire") boolean provisoire,
            @FormParam("definitive") boolean definitive,
            @PathParam("idRencontre") int idRencontre,
            @PathParam("idEquipe") int idEquipe,
            @PathParam("idJoueur") int idJoueur,
            @PathParam("idPenalite") int idPenalite,
            @Context UriInfo uriInfo
    ) {
        try {
            // Ensure heure is not null or empty before parsing
            if (heure == null || heure.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Heure cannot be null or empty").build();
            }

            // Parse the heure string to LocalTime
            LocalTime localHeure = LocalTime.parse(heure);

            // Retrieve existing penalite by ID
            Penalite existingPenalite = penaliteDAO.getPenaliteById(idPenalite);
            if (existingPenalite == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Penalite not found").build();
            }

            // Set the updated fields
            existingPenalite.setHeure(localHeure);
            existingPenalite.setLibelle(libelle);
            existingPenalite.setProvisoire(provisoire);
            existingPenalite.setDefinitive(definitive);

            // Retrieve Rencontre and Joueur objects
            Rencontre rencontre = rencontreDAO.getRencontreById(idRencontre);
            Joueur joueur = joueurDAO.getJoueurById(idJoueur);

            // Set Rencontre and Joueur for the penalite
            existingPenalite.setRencontre(rencontre);
            existingPenalite.setJoueur(joueur);

            // Update the penalite
            penaliteDAO.updatePenalite(existingPenalite);

            // Redirect to the details page after updating
            URI uri = uriInfo.getBaseUriBuilder()
                    .path("/rencontres/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details")
                    .resolveTemplate("idRencontre", idRencontre)
                    .resolveTemplate("idEquipe", idEquipe)
                    .resolveTemplate("idJoueur", idJoueur)
                    .build();

            return Response.seeOther(uri).build();
        } catch (IllegalArgumentException e) {
            // Handle parsing error
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid heure format").build();
        } catch (Exception e) {
            // Handle other errors
            e.printStackTrace();
            throw new WebApplicationException("Error updating penalite", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }




    @GET
    @Path("/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details/penalite/delete")
    @Produces(MediaType.TEXT_HTML)
    public Response deletePenalite(
    		@PathParam("idRencontre") int idRencontre,
            @PathParam("idEquipe") int idEquipe,
            @PathParam("idJoueur") int idJoueur,
            @QueryParam("idPenalite") int idPenalite,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response) {
        try {
            butRencontreDAO.deleteButRencontre(idPenalite);

            // Construct the URI for redirection
            URI uri = uriInfo.getBaseUriBuilder()
            		.path("/rencontres/{idRencontre}/details/equipe/{idEquipe}/participants/{idJoueur}/details")
            	    .resolveTemplate("idRencontre", idRencontre)
            	    .resolveTemplate("idEquipe", idEquipe)
            	    .resolveTemplate("idJoueur", idJoueur)
            	    .build();

            // Redirect to the butRencontre-list page
            return Response.seeOther(uri).build();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new WebApplicationException("Error deleting but rencontre", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
