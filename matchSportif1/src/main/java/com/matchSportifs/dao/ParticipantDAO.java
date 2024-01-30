package com.matchSportifs.dao;

import com.matchSportifs.model.Participant;
import com.matchSportifs.model.ParticipantId;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.util.List;

import com.matchSportifs.util.HibernateUtil;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class ParticipantDAO {

    public void saveParticipant(Participant participant) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(participant);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void updateParticipant(Participant participant) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(participant);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void deleteParticipant(Participant participant) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(participant);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public Participant getParticipantById(ParticipantId id) {
        Transaction transaction = null;
        Participant participant = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            participant = session.get(Participant.class, id);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return participant;
    }

    public List<Participant> getAllParticipants() {
        Transaction transaction = null;
        List<Participant> listOfParticipants = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TypedQuery<Participant> query = session.createQuery("FROM Participant", Participant.class);
            listOfParticipants = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return listOfParticipants;
    }
    
    public List<Participant> getParticipantsByEquipeAndRencontre(int idEquipe, int idRencontre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT p FROM Participant p " +
                         "JOIN p.rencontre r " +
                         "WHERE r.id = :idRencontre AND p.id.idRencontre = :idRencontre AND p.id.idJoueur = :idEquipe";
            Query<Participant> query = session.createQuery(hql, Participant.class);
            query.setParameter("idEquipe", idEquipe);
            query.setParameter("idRencontre", idRencontre);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Participant getParticipantByIdJoueur(int idJoueur) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT p FROM Participant p " +
                         "JOIN FETCH p.joueur j " +
                         "JOIN FETCH j.equipeActuelle e " +
                         "WHERE j.id = :idJoueur";
            Query<Participant> query = session.createQuery(hql, Participant.class);
            query.setParameter("idJoueur", idJoueur);
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Handle case where no participant is found for the given joueur ID
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any other exceptions
            throw new RuntimeException("Error fetching participant by joueur ID: " + idJoueur, e);
        }
    }


    private void handleTransactionException(Exception e) {
        e.printStackTrace();
    }

    public List<Participant> getParticipantsByRencontre(int idRencontre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Participant WHERE id.idRencontre = :idRencontre";
            Query<Participant> query = session.createQuery(hql, Participant.class);
            query.setParameter("idRencontre", idRencontre);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching participants by rencontre ID: " + idRencontre, e);
        }
    }
    private void handleTransactionException(Transaction transaction, Exception e) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
}
