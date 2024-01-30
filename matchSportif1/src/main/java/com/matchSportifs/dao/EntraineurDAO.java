package com.matchSportifs.dao;

import com.matchSportifs.model.Entraineur;
import com.matchSportifs.model.Joueur;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import com.matchSportifs.util.HibernateUtil;

import javax.persistence.TypedQuery;

public class EntraineurDAO {

    public void saveEntraineur(Entraineur entraineur) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(entraineur);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void updateEntraineur(Entraineur entraineur) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(entraineur);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void deleteEntraineur(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Entraineur entraineur = session.get(Entraineur.class, id);
            if (entraineur != null) {
                session.delete(entraineur);
                System.out.println("Entraineur is deleted");
            }
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public Entraineur getEntraineur(int id) {
        Transaction transaction = null;
        Entraineur entraineur = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            entraineur = session.get(Entraineur.class, id);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return entraineur;
    }

    public List<Entraineur> getAllEntraineurs() {
        Transaction transaction = null;
        List<Entraineur> listOfEntraineurs = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TypedQuery<Entraineur> query = session.createQuery("FROM Entraineur", Entraineur.class);
            listOfEntraineurs = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return listOfEntraineurs;
    }
    public List<Entraineur> getAllEntraineursWithoutEquipe() {
        Transaction transaction = null;
        List<Entraineur> listOfEntraineurs = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TypedQuery<Entraineur> query = session.createQuery("FROM Entraineur WHERE	 equipe is NULL", Entraineur.class);
            listOfEntraineurs = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return listOfEntraineurs;
    }

    private void handleTransactionException(Transaction transaction, Exception e) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
}