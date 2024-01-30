package com.matchSportifs.dao;

import com.matchSportifs.model.Arbitre;
import com.matchSportifs.model.Joueur;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

import com.matchSportifs.util.HibernateUtil;

import javax.persistence.TypedQuery;

public class JoueurDAO {

    public void saveJoueur(Joueur joueur) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(joueur);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }
    public List<Joueur> getAllJoueursWithoutEquipe() {
        Transaction transaction = null;
        List<Joueur> listOfJoueurs = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TypedQuery<Joueur> query = session.createQuery("FROM Joueur WHERE equipeActuelle is NULL", Joueur.class);
            listOfJoueurs = query.getResultList();
            System.out.println("Joueurs dao");
            System.out.println(listOfJoueurs);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return listOfJoueurs;
    }
    public void updateJoueur(Joueur joueur) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(joueur);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void deleteJoueur(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Joueur joueur = session.get(Joueur.class, id);
            System.out.println(joueur);
            if (joueur != null) {
                session.delete(joueur);
                System.out.println("joueur is deleted");
            }
    
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public Joueur getJoueurById(int id) {
        Transaction transaction = null;
        Joueur joueur = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            joueur = session.get(Joueur.class, id);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return joueur;
    }

    public List<Joueur> getAllJoueurs() {
        Transaction transaction = null;
        List<Joueur> listOfJoueurs = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TypedQuery<Joueur> query = session.createQuery("FROM Joueur", Joueur.class);
            listOfJoueurs = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return listOfJoueurs;
    }
    public List<Joueur> getPlayersByTeam(int idEquipe) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT j FROM Joueur j WHERE j.equipeActuelle.id = :idEquipe";
            Query<Joueur> query = session.createQuery(hql, Joueur.class);
            query.setParameter("idEquipe", idEquipe);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void handleTransactionException(Transaction transaction, Exception e) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
}
