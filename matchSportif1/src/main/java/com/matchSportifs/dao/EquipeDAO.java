package com.matchSportifs.dao;

import com.matchSportifs.model.Equipe;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import com.matchSportifs.util.HibernateUtil;

import java.util.*;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class EquipeDAO {

    public void saveEquipe(Equipe equipe) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(equipe);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }
    public List<Equipe> getEquipesWithoutClassement() {
        Transaction transaction = null;
        List<Equipe> equipesWithoutClassement = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // Query to select all teams that are not referenced in the ClassementEquipes table
            String hql = "SELECT e FROM Equipe e WHERE e.idEquipe NOT IN (SELECT c.equipe.idEquipe FROM ClassementEquipes c)";
            TypedQuery<Equipe> query = session.createQuery(hql, Equipe.class);
            equipesWithoutClassement = query.getResultList();
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return equipesWithoutClassement;
    }
    public void updateEquipe(Equipe equipe) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(equipe);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void deleteEquipe(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Equipe equipe = session.get(Equipe.class, id);
            if (equipe != null) {
                session.delete(equipe);
                System.out.println("Equipe is deleted");
            }
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public Equipe getEquipe(int id) {
        Transaction transaction = null;
        Equipe equipe = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            equipe = session.get(Equipe.class, id);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return equipe;
    }

    public List<Equipe> getAllEquipes() {
        Transaction transaction = null;
        List<Equipe> listOfEquipes = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TypedQuery<Equipe> query = session.createQuery("FROM Equipe", Equipe.class);
            listOfEquipes = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return listOfEquipes;
    }

    private void handleTransactionException(Transaction transaction, Exception e) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
    
    public List<Equipe> getEquipeByClassement(Long long1) {
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Query query = session.createQuery("SELECT e FROM Equipe e WHERE e.classement.id = :classementId", Equipe.class);
            query.setParameter("classementId", long1);
            return query.getResultList();
        } catch (Exception e) {
            throw new HibernateException("Error retrieving equipe by classement: " + e.getMessage());
        }
    }
}
