package com.matchSportifs.dao;

import com.matchSportifs.model.Arbitre;
import com.matchSportifs.model.LieuAcceuil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

import com.matchSportifs.util.HibernateUtil;

import javax.persistence.TypedQuery;

public class LieuAcceuilDAO {

    public void saveLieuAcceuil(LieuAcceuil lieuAcceuil) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(lieuAcceuil);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void updateLieuAcceuil(LieuAcceuil lieuAcceuil) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(lieuAcceuil);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }
    
    public void deleteLieuAcceuil(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            LieuAcceuil lieuAcceuil = session.get(LieuAcceuil.class, id);
            if (lieuAcceuil != null) {
                session.delete(lieuAcceuil);
                System.out.println("lieuAcceuil is deleted");
            }
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }
    
    public List<LieuAcceuil> getLieuxAcceuilByAdresse(int idAdresse) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<LieuAcceuil> query = session.createQuery("FROM LieuAcceuil WHERE adresse.idAdresse = :idAdresse", LieuAcceuil.class);
            query.setParameter("idAdresse", idAdresse);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
            return null;
        }
    }

    public LieuAcceuil getLieuAcceuilById(int id) {
        Transaction transaction = null;
        LieuAcceuil lieuAcceuil = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            lieuAcceuil = session.get(LieuAcceuil.class, id);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return lieuAcceuil;
    }

    public List<LieuAcceuil> getAllLieuxAcceuils() {
        Transaction transaction = null;
        List<LieuAcceuil> listOfLieuxAcceuil = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TypedQuery<LieuAcceuil> query = session.createQuery("FROM LieuAcceuil", LieuAcceuil.class);
            listOfLieuxAcceuil = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return listOfLieuxAcceuil;
    }

    private void handleTransactionException(Transaction transaction, Exception e) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
}
