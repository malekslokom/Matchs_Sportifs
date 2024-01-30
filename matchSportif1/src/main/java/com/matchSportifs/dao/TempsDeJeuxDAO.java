package com.matchSportifs.dao;

import com.matchSportifs.model.TempsDeJeux;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import com.matchSportifs.util.HibernateUtil;

import javax.persistence.TypedQuery;

public class TempsDeJeuxDAO {

    public void saveTempsDeJeux(TempsDeJeux tempsDeJeux) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(tempsDeJeux);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void updateTempsDeJeux(TempsDeJeux tempsDeJeux) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(tempsDeJeux);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void deleteTempsDeJeux(TempsDeJeux tempsDeJeux) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(tempsDeJeux);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public TempsDeJeux getTempsDeJeuxById(int idTempsDeJeux) {
        Transaction transaction = null;
        TempsDeJeux tempsDeJeux = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            tempsDeJeux = session.get(TempsDeJeux.class, idTempsDeJeux);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return tempsDeJeux;
    }

    public List<TempsDeJeux> getAllTempsDeJeux() {
        Transaction transaction = null;
        List<TempsDeJeux> listOfTempsDeJeux = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TypedQuery<TempsDeJeux> query = session.createQuery("FROM TempsDeJeux", TempsDeJeux.class);
            listOfTempsDeJeux = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return listOfTempsDeJeux;
    }

    private void handleTransactionException(Transaction transaction, Exception e) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
    
 // Method to fetch TempsDeJeux associated with a Rencontre by its ID
    public List<TempsDeJeux> getTempsDeJeuxForRencontre(int idRencontre) {
        Transaction transaction = null;
        List<TempsDeJeux> tempsDeJeuxList = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TypedQuery<TempsDeJeux> query = session.createQuery(
                    "SELECT t FROM TempsDeJeux t WHERE t.rencontre.idRencontre = :idRencontre",
                    TempsDeJeux.class
            );
            query.setParameter("idRencontre", idRencontre);
            tempsDeJeuxList = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return tempsDeJeuxList;
    }


}
