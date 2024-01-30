package com.matchSportifs.dao;

import com.matchSportifs.model.Arbitre;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import com.matchSportifs.util.HibernateUtil;

import javax.persistence.TypedQuery;

public class ArbitreDAO {

    public void saveArbitre(Arbitre arbitre) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(arbitre);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void updateArbitre(Arbitre arbitre) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(arbitre);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void deleteArbitre(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Arbitre arbitre = session.get(Arbitre.class, id);
            if (arbitre != null) {
                session.delete(arbitre);
                System.out.println("Arbitre is deleted");
            }
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public Arbitre getArbitre(int id) {
        Transaction transaction = null;
        Arbitre arbitre = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            arbitre = session.get(Arbitre.class, id);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return arbitre;
    }

    
    public List<Arbitre> getAllArbitres() {
        Transaction transaction = null;
        List<Arbitre> listOfArbitres = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TypedQuery<Arbitre> query = session.createQuery("FROM Arbitre", Arbitre.class);
            listOfArbitres = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return listOfArbitres;
    }

    private void handleTransactionException(Transaction transaction, Exception e) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
}
