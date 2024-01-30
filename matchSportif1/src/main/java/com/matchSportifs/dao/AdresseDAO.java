package com.matchSportifs.dao;



import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.SystemException;

import org.hibernate.Session;

import com.matchSportifs.model.Adresse;
import com.matchSportifs.util.HibernateUtil;


import org.hibernate.Transaction;



public class AdresseDAO {

    public void saveAdresse(Adresse adresse) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = (Transaction) session.beginTransaction();
            session.save(adresse);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void updateAdresse(Adresse adresse) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = (Transaction) session.beginTransaction();
            session.update(adresse);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void deleteAdresse(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = (Transaction) session.beginTransaction();
            Adresse adresse = session.get(Adresse.class, id);
            if (adresse != null) {
                session.delete(adresse);
                System.out.println("Adresse is deleted");
            }
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public Adresse getAdresse(int id) {
    	System.out.println(id);
        Transaction transaction = null;
        Adresse adresse = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = (Transaction) session.beginTransaction();
            adresse = session.get(Adresse.class, id);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return adresse;
    }

    public List<Adresse> getAllAdresses() {
        Transaction transaction = null;
        List<Adresse> listOfAdresses = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = (Transaction) session.beginTransaction();
            TypedQuery<Adresse> query = (TypedQuery<Adresse>) session.createQuery("FROM Adresse", Adresse.class);
            listOfAdresses = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return listOfAdresses;
    }

    private void handleTransactionException(Transaction transaction, Exception e) {
        if (transaction != null && (transaction).isActive()) {
            try {
				transaction.rollback();
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
			}
        }
        e.printStackTrace();
    }
    
    public Adresse getAdresseByDetails(String rue, String codePostal, String ville) {
        Transaction transaction = null;
        Adresse adresse = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = (Transaction) session.beginTransaction();

            // Use TypedQuery with parameters to get Adresse by details
            TypedQuery<Adresse> query = (TypedQuery<Adresse>) session.createQuery(
                "FROM Adresse WHERE rue = :rue AND codePostal = :codePostal AND ville = :ville", 
                Adresse.class
            );
            
            // Set query parameters
            query.setParameter("rue", rue);
            query.setParameter("codePostal", codePostal);
            query.setParameter("ville", ville);

            // Execute the query and get the single result (or null if not found)
            adresse = query.getSingleResult();

            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return adresse;
    }
}
