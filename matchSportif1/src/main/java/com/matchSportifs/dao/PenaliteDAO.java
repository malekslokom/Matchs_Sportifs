package com.matchSportifs.dao;

import com.matchSportifs.model.Penalite;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import com.matchSportifs.util.HibernateUtil;

import javax.persistence.TypedQuery;

public class PenaliteDAO {

    public void savePenalite(Penalite penalite) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(penalite);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void updatePenalite(Penalite penalite) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(penalite);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void deletePenalite(Penalite penalite) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(penalite);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public Penalite getPenaliteById(int idPenalite) {
        Transaction transaction = null;
        Penalite penalite = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            penalite = session.get(Penalite.class, idPenalite);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return penalite;
    }

    public List<Penalite> getAllPenalites() {
        Transaction transaction = null;
        List<Penalite> listOfPenalites = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TypedQuery<Penalite> query = session.createQuery("FROM Penalite", Penalite.class);
            listOfPenalites = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return listOfPenalites;
    }

    public List<Penalite> getAllPenalitesForPlayer(int idRencontre, int idJoueur) {
        Transaction transaction = null;
        List<Penalite> listOfPenalites = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // Use HQL to fetch penalites for a specific player in a specific encounter
            TypedQuery<Penalite> query = session.createQuery(
                    "FROM Penalite p WHERE p.rencontre.idRencontre = :idRencontre AND p.joueur.id = :idJoueur",
                    Penalite.class
            );
            query.setParameter("idRencontre", idRencontre);
            query.setParameter("idJoueur", idJoueur);
            listOfPenalites = query.getResultList();
            
            transaction.commit();
        } catch (Exception e) {
        	e.printStackTrace();
            handleTransactionException(transaction, e);
        }
        return listOfPenalites;
    }
    private void handleTransactionException(Transaction transaction, Exception e) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
}
