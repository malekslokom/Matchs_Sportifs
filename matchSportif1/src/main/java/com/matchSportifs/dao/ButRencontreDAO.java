package com.matchSportifs.dao;

import com.matchSportifs.model.ButRencontre;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

import com.matchSportifs.util.HibernateUtil;

import javax.persistence.TypedQuery;

public class ButRencontreDAO {

    public void saveButRencontre(ButRencontre butRencontre) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(butRencontre);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void updateButRencontre(ButRencontre butRencontre) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(butRencontre);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void deleteButRencontre(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            ButRencontre butRencontre = session.get(ButRencontre.class, id);
            if (butRencontre != null) {
                session.delete(butRencontre);
                System.out.println("ButRencontre is deleted");
            }
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public ButRencontre getButRencontre(int id) {
        Transaction transaction = null;
        ButRencontre butRencontre = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            butRencontre = session.get(ButRencontre.class, id);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return butRencontre;
    }

    public List<ButRencontre> getAllButRencontres() {
        Transaction transaction = null;
        List<ButRencontre> listOfButRencontres = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TypedQuery<ButRencontre> query = session.createQuery("FROM ButRencontre", ButRencontre.class);
            listOfButRencontres = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return listOfButRencontres;
    }
    
    public ButRencontre getButRencontreByIds(int idRencontre, int idEquipe, int idJoueur) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<ButRencontre> query = session.createQuery(
                "SELECT br FROM ButRencontre br " +
                "WHERE br.rencontre.idRencontre = :idRencontre " +
                "AND br.joueur.id = :idJoueur", ButRencontre.class)
                .setParameter("idRencontre", idRencontre)
                .setParameter("idJoueur", idJoueur);

            ButRencontre butRencontre = query.uniqueResult();

            session.getTransaction().commit();

            return butRencontre;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<ButRencontre> getAllButRencontreForPlayer(int idRencontre, int idJoueur) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            
            List<ButRencontre> listOfButRencontres = null;
            
            try {
                String hql = "FROM ButRencontre b WHERE b.rencontre.idRencontre = :idRencontre AND b.joueur.id = :idJoueur";
                Query<ButRencontre> query = session.createQuery(hql, ButRencontre.class);
                query.setParameter("idRencontre", idRencontre);
                query.setParameter("idJoueur", idJoueur);
                listOfButRencontres = query.getResultList();
                transaction.commit();
                
            } catch (Exception e) {
                // Rollback transaction if an error occurs
                transaction.rollback();
                handleTransactionException(transaction, e);
            }
            
            return listOfButRencontres;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void handleTransactionException(Transaction transaction, Exception e) {
    	e.printStackTrace();
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
}
