package com.matchSportifs.resource;

import com.matchSportifs.dao.EquipeDAO;
import com.matchSportifs.dao.JoueurDAO;
import com.matchSportifs.model.Equipe;
import com.matchSportifs.model.Joueur;

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
import java.time.format.DateTimeFormatter;
import java.util.List;

@Path("/joueurs")
@Produces(MediaType.APPLICATION_JSON)
public class JoueurResource {

    private final JoueurDAO joueurDao = new JoueurDAO();
    private final EquipeDAO equipeDao = new EquipeDAO();

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getAllJoueurs(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
            List<Joueur> joueurs = joueurDao.getAllJoueurs();
            request.setAttribute("listJoueur", joueurs);

            RequestDispatcher rd = request.getRequestDispatcher("/views/joueur/Joueur-list.jsp");
            rd.forward(request, response);

            return Response.ok().build();
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching joueurs", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    public Response getJoueur(@PathParam("id") int id) {
        try {
            Joueur joueur = joueurDao.getJoueurById(id);
            if (joueur != null) {
                return Response.ok(joueur).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Joueur not found").build();
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching joueur", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public Response showAddNewJoueurForm(@Context HttpServletRequest request, @Context HttpServletResponse response) {
    	List<Equipe> equipes = equipeDao.getAllEquipes();
    	
        RequestDispatcher rd = request.getRequestDispatcher("/views/joueur/joueur-form.jsp");
        request.setAttribute("listEquipe", equipes);
        try {
            rd.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

        return Response.ok().build();
    }

//    @POST
//    @Path("/insert")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public Response addJoueur(
//            @FormParam("nom") String nom,
//            @FormParam("prenom") String prenom,
//            @FormParam("dateNaissance") String dateNaissance,
//            @FormParam("nationalite") String nationalite,
//            @Context UriInfo uriInfo,
//            @Context HttpServletRequest request,
//            @Context HttpServletResponse response
//    ) {
//        try {
//            LocalDate localDateNaissance = convertStringToLocalDate(dateNaissance);
//            List<Equipe> equipes = equipeDao.getAllEquipes();
//            Joueur newJoueur = new Joueur();
//            newJoueur.setNom(nom);
//            newJoueur.setPrenom(prenom);
//            newJoueur.setDateNaissance(localDateNaissance);
//            newJoueur.setNationalite(nationalite);
//
//            joueurDao.saveJoueur(newJoueur);
//
//            URI uri = uriInfo.getBaseUriBuilder()
//                    .path("/joueurs")
//                    .build();
//
//            return Response.seeOther(uri).build();
//        } catch (Exception e) {
//            throw new WebApplicationException("Error adding joueur", Response.Status.INTERNAL_SERVER_ERROR);
//        }
//    }

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addJoueur(
            @FormParam("nom") String nom,
            @FormParam("prenom") String prenom,
            @FormParam("dateNaissance") String dateNaissance,
            @FormParam("nationalite") String nationalite,
            @FormParam("equipeActuelle") int idEquipeActuelle,
            @FormParam("maillot") String maillot, // Ajout de cette ligne
            @FormParam("poste") String poste, // Ajout de cette ligne
            @FormParam("equipePrecedente") int idEquipePrecedente,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        try {
            LocalDate localDateNaissance = convertStringToLocalDate(dateNaissance);
            List<Equipe> equipes = equipeDao.getAllEquipes();

            Joueur newJoueur = new Joueur();
            newJoueur.setNom(nom);
            newJoueur.setPrenom(prenom);
            newJoueur.setDateNaissance(localDateNaissance);
            newJoueur.setNationalite(nationalite);

            if (idEquipeActuelle > 0) {
                Equipe equipeActuelle = equipeDao.getEquipe(idEquipeActuelle);
                newJoueur.setEquipeActuelle(equipeActuelle);
                // Ajout du maillot et du poste si l'équipe actuelle est spécifiée
                newJoueur.setMaillot(Integer.parseInt(maillot));
                newJoueur.setPost(poste);
            }

            if (idEquipePrecedente > 0) {
                Equipe equipePrecedente = equipeDao.getEquipe(idEquipePrecedente);
                newJoueur.setEquipePrecedente(equipePrecedente);
            }

            joueurDao.saveJoueur(newJoueur);

            URI uri = uriInfo.getBaseUriBuilder()
                    .path("/joueurs")
                    .build();

            return Response.seeOther(uri).build();
        } catch (Exception e) {
            throw new WebApplicationException("Error adding joueur", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    private LocalDate convertStringToLocalDate(String dateNaissance) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateNaissance, formatter);
    }

    @GET
    @Path("/edit")
    @Produces(MediaType.TEXT_HTML)
    public Response showEditForm(@QueryParam("id") int id, @Context UriInfo uriInfo, @Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
            Joueur existingJoueur = joueurDao.getJoueurById(id);
            List<Equipe> equipes = equipeDao.getAllEquipes();
            if (existingJoueur != null) {
                RequestDispatcher rd = request.getRequestDispatcher("/views/joueur/joueur-form.jsp");
                request.setAttribute("listEquipe", equipes);
                request.setAttribute("joueur", existingJoueur);
                rd.forward(request, response);

                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Joueur not found").build();
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching joueur", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateJoueur(
            @FormParam("id") int id,
            @FormParam("nom") String nom,
            @FormParam("prenom") String prenom,
            @FormParam("dateNaissance") String dateNaissance,
            @FormParam("nationalite") String nationalite,
            @FormParam("equipeActuelle") int idEquipeActuelle, // Modifier le nom du paramètre
            @FormParam("maillot") String maillot,
            @FormParam("poste") String poste,
            @FormParam("equipePrecedente") int idEquipePrecedente, // Modifier le nom du paramètre
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        try {
            Joueur existingJoueur = joueurDao.getJoueurById(id);
            if (existingJoueur == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Joueur not found").build();
            }

            LocalDate localDateNaissance = convertStringToLocalDate(dateNaissance);

            existingJoueur.setNom(nom);
            existingJoueur.setPrenom(prenom);
            existingJoueur.setDateNaissance(localDateNaissance);
            existingJoueur.setNationalite(nationalite);

            if (idEquipeActuelle > 0) {
                Equipe equipe = equipeDao.getEquipe(idEquipeActuelle);
                int maillotValue = Integer.parseInt(maillot);
                existingJoueur.setEquipeActuelle(equipe);
                existingJoueur.setMaillot(maillotValue);
                existingJoueur.setPost(poste);
            } else {
                existingJoueur.setEquipeActuelle(null);
            }

            if (idEquipePrecedente > 0) {
                Equipe equipePres = equipeDao.getEquipe(idEquipePrecedente);
                existingJoueur.setEquipePrecedente(equipePres);
            } else {
                existingJoueur.setEquipePrecedente(null);
            }

            joueurDao.updateJoueur(existingJoueur);

            URI uri = uriInfo.getBaseUriBuilder()
                    .path("/joueurs")
                    .build();

            return Response.seeOther(uri).build();
        } catch (Exception e) {
            throw new WebApplicationException("Error updating joueur", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/delete")
    @Produces(MediaType.TEXT_HTML)
    public Response deleteJoueur(
            @QueryParam("id") int id,
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response) {
        try {
            joueurDao.deleteJoueur(id);

            URI uri = uriInfo.getBaseUriBuilder()
                    .path("/joueurs")
                    .build();

            return Response.seeOther(uri).status(Response.Status.FOUND).build();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new WebApplicationException("Error deleting joueur", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
