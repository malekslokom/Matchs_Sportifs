package com.matchSportifs.resource;

import com.matchSportifs.dao.LieuAcceuilDAO;
import com.matchSportifs.dao.AdresseDAO;

import com.matchSportifs.model.Adresse;
import com.matchSportifs.model.LieuAcceuil;
import com.matchSportifs.util.HibernateUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Path("/lieuxAcceuil")
public class LieuAcceuilResource {

    private LieuAcceuilDAO lieuAcceuilDAO = new LieuAcceuilDAO();
    private AdresseDAO adresseDAO = new AdresseDAO();

    @GET 
    @Produces(MediaType.TEXT_HTML)
    public Response getAllLieuAcceuils(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
        	System.out.println("Lieu ACC");
            List<LieuAcceuil> lieuAcceuils = lieuAcceuilDAO.getAllLieuxAcceuils();
            request.setAttribute("listLieuAcceuil", lieuAcceuils);

            // Use RequestDispatcher to forward the request to the JSP page
            RequestDispatcher rd = request.getRequestDispatcher("/views/lieuAccueil/lieuAccueil-list.jsp");
            rd.forward(request, response);

            // Returning Response.ok() to satisfy JAX-RS requirements
            return Response.ok().build();
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching lieuAcceuils", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    public Response getLieuAcceuil(@PathParam("id") int id) {
        try {
            LieuAcceuil lieuAcceuil = lieuAcceuilDAO.getLieuAcceuilById(id);
            if (lieuAcceuil != null) {
                return Response.ok(lieuAcceuil).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("LieuAcceuil not found").build();
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching lieuAcceuil", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public Response showAddNewLieuAcceuilForm(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        RequestDispatcher rd = request.getRequestDispatcher("/views/lieuAccueil/lieuAccueil-form.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

        // Returning Response.ok() to satisfy JAX-RS requirements
        return Response.ok().build();
    }

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addLieuAcceuil(
            @FormParam("type") String type,
            @FormParam("nom") String nom,
            @FormParam("rue") String rue,
            @FormParam("codePostal") String codePostal,
            @FormParam("ville") String ville,
            @FormParam("courriel") String courriel,
            @FormParam("infosPMR") String infosPMR,
            @FormParam("commentaires") String commentaires,
            @FormParam("telephone") String telephone,
            @FormParam("siteInternet") String siteInternet,
            @FormParam("capacite") int capacite,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        try {
            // Start a transaction
            Transaction transaction = null;
            
            try {
                // Begin transaction
            	 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                transaction = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
                
                // Vérifiez si l'adresse existe déjà dans la base de données
                Adresse existingAdresse = adresseDAO.getAdresseByDetails(rue, codePostal, ville);

                // Si l'adresse n'existe pas, créez-la
                if (existingAdresse == null) {
                    Adresse newAdresse = new Adresse();
                    newAdresse.setRue(rue);
                    newAdresse.setCodePostal(codePostal);
                    newAdresse.setVille(ville);
                    
                    session.save(newAdresse); 
                    System.out.println("newAdresse DONE");
                    // Récupérez l'ID généré pour la nouvelle adresse
                    int idAdresse = newAdresse.getIdAdresse();
                    
                    // Créez le lieu d'accueil avec l'ID de l'adresse
                    LieuAcceuil newLieuAcceuil = new LieuAcceuil();
                    newLieuAcceuil.setType(type);
                    newLieuAcceuil.setNom(nom);
                    newLieuAcceuil.setAdresse(newAdresse);
                    newLieuAcceuil.setCourriel(courriel);
                    newLieuAcceuil.setInfosPMR(infosPMR);
                    newLieuAcceuil.setCommentaires(commentaires);
                    newLieuAcceuil.setTelephone(telephone);
                    newLieuAcceuil.setSiteInternet(siteInternet);
                    newLieuAcceuil.setCapacite(capacite);
                 
                    session.save(newLieuAcceuil); 
                    
                    // Commit the transaction
                    transaction.commit();

                    // Construct the URI for redirection
                    URI uri = uriInfo.getBaseUriBuilder()
                            .path("/lieuxAcceuil")
                            .build();

                    // Redirect to the lieuAcceuil-list page
                    return Response.seeOther(uri).build();
                } else {
                    // L'adresse existe déjà, utilisez son ID pour créer le lieu d'accueil
                    int idAdresse = existingAdresse.getIdAdresse();

                    // Créez le lieu d'accueil avec l'ID de l'adresse
                    LieuAcceuil newLieuAcceuil = new LieuAcceuil();
                    newLieuAcceuil.setType(type);
                    newLieuAcceuil.setNom(nom);
                    newLieuAcceuil.setAdresse(existingAdresse);
                    newLieuAcceuil.setCourriel(courriel);
                    newLieuAcceuil.setInfosPMR(infosPMR);
                    newLieuAcceuil.setCommentaires(commentaires);
                    newLieuAcceuil.setTelephone(telephone);
                    newLieuAcceuil.setSiteInternet(siteInternet);
                    newLieuAcceuil.setCapacite(capacite);
                    
                    lieuAcceuilDAO.saveLieuAcceuil(newLieuAcceuil);

                    // Commit the transaction
                    transaction.commit();

                    // Construct the URI for redirection
                    URI uri = uriInfo.getBaseUriBuilder()
                            .path("/lieuxAcceuil")
                            .build();

                    // Redirect to the lieuAcceuil-list page
                    return Response.seeOther(uri).build();
                }
            } catch (Exception e) {
                // Rollback the transaction if an exception occurs
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                throw new WebApplicationException("Error adding lieuAcceuil", Response.Status.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error adding lieuAcceuil", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }



    private LocalDate convertStringToLocalDate(String dateNaissance) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateNaissance, formatter);
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
            LieuAcceuil existingLieuAcceuil = lieuAcceuilDAO.getLieuAcceuilById(id);

            if (existingLieuAcceuil != null) {
            	
                
                // Fetch the associated Adresse
                Adresse adresse = existingLieuAcceuil.getAdresse();
                
                RequestDispatcher rd = request.getRequestDispatcher("/views/lieuAccueil/lieuAccueil-form.jsp");
                request.setAttribute("lieuAcceuil", existingLieuAcceuil);
                request.setAttribute("adresse", adresse);
                rd.forward(request, response);

                // Returning Response.ok() to satisfy JAX-RS requirements
                return Response.ok().build();
            } else {
                // LieuAcceuil not found, return a 404 response
                return Response.status(Response.Status.NOT_FOUND).entity("LieuAcceuil not found").build();
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching lieuAcceuil", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateLieuAcceuil(
            @FormParam("id") int id,
            @FormParam("type") String type,
            @FormParam("nom") String nom,
            @FormParam("idAdresse") int idAdresse,
            @FormParam("rue") String rue,
            @FormParam("codePostal") String codePostal,
            @FormParam("ville") String ville,
            @FormParam("courriel") String courriel,
            @FormParam("infosPMR") String infosPMR,
            @FormParam("commentaires") String commentaires,
            @FormParam("telephone") String telephone,
            @FormParam("siteInternet") String siteInternet,
            @FormParam("capacite") int capacite,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
    	try {
            Transaction transaction = null;
            
       try {
        	 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
             transaction = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        
            LieuAcceuil existingLieuAcceuil = lieuAcceuilDAO.getLieuAcceuilById(id);

            if (existingLieuAcceuil == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("LieuAcceuil not found").build();
            }

            // Set updated properties
            existingLieuAcceuil.setType(type);
            existingLieuAcceuil.setNom(nom);
            existingLieuAcceuil.setCourriel(courriel);
            existingLieuAcceuil.setInfosPMR(infosPMR);
            existingLieuAcceuil.setCommentaires(commentaires);
            existingLieuAcceuil.setTelephone(telephone);
            existingLieuAcceuil.setSiteInternet(siteInternet);
            existingLieuAcceuil.setCapacite(capacite);
            // Fetch the associated Adresse
            Adresse existingAdresse = adresseDAO.getAdresse(idAdresse);
            
            if (existingAdresse == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Adresse not found").build();
            }
            existingAdresse.setRue(rue);
            existingAdresse.setCodePostal(codePostal);
            existingAdresse.setVille(ville);
			session.saveOrUpdate(existingAdresse);
            // Set the updated Adresse to the LieuAcceuil
            existingLieuAcceuil.setAdresse(existingAdresse);
            transaction.commit();
            // Construct the URI for redirection
            URI uri = uriInfo.getBaseUriBuilder()
                    .path("/lieuxAcceuil")
                    .build();

            // Redirect to the lieuAcceuil-list page
            return Response.seeOther(uri).build();
        } catch (Exception e) {
            throw new WebApplicationException("Error updating lieuAcceuil", Response.Status.INTERNAL_SERVER_ERROR);
        }
    	} catch (Exception e) {
            throw new WebApplicationException("Error updating lieuAcceuil", Response.Status.INTERNAL_SERVER_ERROR);
        }
        
    }


    @GET
    @Path("/delete")
    @Produces(MediaType.TEXT_HTML)
    public Response deleteLieuAcceuil(
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

                LieuAcceuil lieuAcceuilToDelete = lieuAcceuilDAO.getLieuAcceuilById(id);

                if (lieuAcceuilToDelete == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("LieuAcceuil not found").build();
                }

                Adresse associatedAdresse = lieuAcceuilToDelete.getAdresse();

                session.delete(lieuAcceuilToDelete);

                // Check if the associated Adresse is not used by any other LieuAcceuil
                List<LieuAcceuil> lieuxByAdresse = lieuAcceuilDAO.getLieuxAcceuilByAdresse(associatedAdresse.getIdAdresse());

                if (lieuxByAdresse == null || lieuxByAdresse.isEmpty()) {
                    // If the Adresse is not used by any other LieuAcceuil, delete it
                    Adresse existingAdresse = session.get(Adresse.class, associatedAdresse.getIdAdresse());
                    session.delete(existingAdresse);
                }

                // Commit the transaction
                transaction.commit();

                // Construct the URI for redirection
                URI uri = uriInfo.getBaseUriBuilder()
                        .path("/lieuxAcceuil")
                        .build();

                // Redirect to the lieuAcceuil-list page
                return Response.seeOther(uri).build();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new WebApplicationException("Error deleting lieuAcceuil", Response.Status.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error updating lieuAcceuil", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }



}
