package com.matchSportifs.test;

import com.matchSportifs.model.*;
import com.matchSportifs.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AdresseTest {

//	public static void main(String[] args) {
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        Session session = sessionFactory.openSession();
//        Transaction transaction = null;
//
//        try {
//            transaction = session.beginTransaction();
//
//            // Création des instances de chaque entité
//            Adresse adresse = new Adresse();
//            adresse.setRue("123 rue de l'Exemple");
//            adresse.setCodePostal("75000");
//            adresse.setVille("Paris");
//
//            LieuAcceuil lieuAcceuil = new LieuAcceuil();
//            lieuAcceuil.setNom("Stade Olympique");
//        //    lieuAcceuil.setAdresse(adresse);
//            lieuAcceuil.setType("Stade");
//            lieuAcceuil.setCapacite(50000);
//
//            Equipe equipe = new Equipe();
//            equipe.setNom("Les Aigles");
//            equipe.setAnneeNaissance(LocalDate.of(1990, 1, 1));
//
//            Joueur joueur = new Joueur();
//            joueur.setNom("Dupont");
//            joueur.setPrenom("Jean");
//            joueur.setDateNaissance(LocalDate.of(1995, 5, 20));
//            joueur.setNationalite("Française");
//            joueur.setEquipeActuelle(equipe);
//            joueur.setMaillot(10);
//            joueur.setPost("Attaquant");
//
//            Rencontre rencontre = new Rencontre();
//            rencontre.setLieuAcceuil(lieuAcceuil);
//            rencontre.setDateDebut(LocalDate.now());
//            rencontre.setHeureDebut(LocalTime.of(20, 0));
//            rencontre.setDateFin(LocalDate.now());
//            rencontre.setHeureFin(LocalTime.of(22, 0));
//
//            Arbitre arbitre = new Arbitre();
//            arbitre.setNom("L'Arbitre");
//            arbitre.setPrenom("Pierre");
//            arbitre.setDateNaissance(LocalDate.of(1980, 10, 10));
//            arbitre.setRencontre(rencontre);
//
//            ClassementEquipes classement = new ClassementEquipes();
//            classement.setPoints(0);
//            classement.setPointsDom(0);
//            classement.setPointsExt(0);
//            classement.setMatchesPlayed(0);
//
//            Entraineur entraineur = new Entraineur();
//            entraineur.setNom("Doe");
//            entraineur.setPrenom("John");
//            entraineur.setDateNaissance(LocalDate.of(1975, 3, 15));
//            entraineur.setEquipe(equipe);
//
//            ButRencontre butRencontre = new ButRencontre();
//            butRencontre.setHeure(LocalTime.of(20, 30));
//            butRencontre.setTypeAction("Goal");
//            butRencontre.setJoueur(joueur);
//            butRencontre.setRencontre(rencontre);
//
//            // Création de l'instance de EquipeRencontre
//            EquipeRencontre equipeRencontre = new EquipeRencontre();
//            EquipeRencontreId  equipeRencontreId= new EquipeRencontreId();
//            
//            // Assurez-vous que les instances de Equipe et Rencontre ont été enregistrées
//            // et ont des identifiants générés avant de les associer
//            equipeRencontreId.setIdEquipe(equipe.getIdEquipe());
//            equipeRencontreId.setIdRencontre(rencontre.getIdRencontre());
//            
//            equipeRencontre.setId(equipeRencontreId);
//            equipeRencontre.setEquipe(equipe);
//            equipeRencontre.setRencontre(rencontre);
//            equipeRencontre.setDomicile(true); // ou false, selon la logique métier
//
//            
//            Penalite penalite = new Penalite();
//            penalite.setLibelle("Foul");
//            penalite.setHeure(LocalTime.of(20, 45));
//            penalite.setProvisoire(true);
//            penalite.setDefinitive(false);
//            penalite.setJoueur(joueur);
//            penalite.setRencontre(rencontre);
//            penalite.setArbitre(arbitre);
//
//            Participant participant = new Participant();
//            ParticipantId participantId = new ParticipantId();
//            participantId.setIdJoueur(joueur.getIdJoueur());
//            participantId.setIdRencontre(rencontre.getIdRencontre());
//            participant.setId(participantId);
//            participant.setRencontre(rencontre);
//            participant.setJoueur(joueur);
//            participant.setHeureEntree(LocalDateTime.of(LocalDate.now(), LocalTime.of(20, 0)));
//            participant.setHeureSortie(LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 0)));
//
//            TempsDeJeux tempsDeJeux = new TempsDeJeux();
//            tempsDeJeux.setHeureDebut(LocalTime.of(20, 0));
//            tempsDeJeux.setHeureFin(LocalTime.of(21, 0));
//            tempsDeJeux.setType("Première mi-temps");
//            tempsDeJeux.setRencontre(rencontre);
//
//            // Enregistrement des instances dans la base de données
//            session.save(adresse);
//            session.save(lieuAcceuil);
//            session.save(equipe);
//            session.save(joueur);
//            session.save(rencontre);
//            session.save(arbitre);
//            session.save(classement);
//            session.save(entraineur);
//            session.save(butRencontre);
//            session.save(penalite);
//            session.save(participant);
//            session.save(tempsDeJeux);
//            session.save(equipeRencontre);
//            // Commit de la transaction
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            session.close();
//            sessionFactory.close();
//        }
//    }
}