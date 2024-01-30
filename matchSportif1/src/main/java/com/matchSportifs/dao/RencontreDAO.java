package com.matchSportifs.dao;

import com.matchSportifs.model.Rencontre;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import com.matchSportifs.util.HibernateUtil;

import javax.persistence.TypedQuery;

public class RencontreDAO {

    public void saveRencontre(Rencontre rencontre) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(rencontre);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void updateRencontre(Rencontre rencontre) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(rencontre);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    
    public void deleteRencontre(Rencontre rencontre) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(rencontre);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public Rencontre getRencontreByIdidRencontre(int idRencontre) {
        Transaction transaction = null;
        Rencontre rencontre = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            rencontre = session.get(Rencontre.class, idRencontre);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return rencontre;
    }
    public Rencontre getRencontreById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "SELECT DISTINCT r " +
                "FROM Rencontre r " +
                "LEFT JOIN FETCH r.arbitres " +
                "WHERE r.idRencontre = :id", Rencontre.class)
                .setParameter("id", id)
                .uniqueResult();
        }
    }
    public List<Rencontre> getAllRencontres() {
        Transaction transaction = null;
        List<Rencontre> listOfRencontres = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TypedQuery<Rencontre> query = session.createQuery("FROM Rencontre", Rencontre.class);
            listOfRencontres = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            handleTransactionException(transaction, e);
        }
        return listOfRencontres;
    }

    
    public List<Rencontre> getRencontreByAdresse(int idAdresse) {
        Transaction transaction = null;
        List<Rencontre> listOfRencontres = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            TypedQuery<Rencontre> query = session.createQuery("FROM Rencontre WHERE adresse.idAdresse = :idAdresse", Rencontre.class);
            query.setParameter("idAdresse", idAdresse);
            
            listOfRencontres = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return listOfRencontres;
    }

    private void handleTransactionException(Transaction transaction, Exception e) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
}
