package com.matchSportifs.dao;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.matchSportifs.model.EquipeRencontre;
import com.matchSportifs.util.HibernateUtil;

import java.util.Collections;
import java.util.List;


import javax.persistence.TypedQuery;

public class EquipeRencontreDAO {

    public void saveEquipeRencontre(EquipeRencontre equipeRencontre) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(equipeRencontre);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void updateEquipeRencontre(EquipeRencontre equipeRencontre) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(equipeRencontre);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void deleteEquipeRencontre(EquipeRencontre equipeRencontre) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(equipeRencontre);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public List<EquipeRencontre> getAllEquipeRencontres() {
        Transaction transaction = null;
        List<EquipeRencontre> listOfEquipeRencontres = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TypedQuery<EquipeRencontre> query = session.createQuery("FROM EquipeRencontre", EquipeRencontre.class);
            listOfEquipeRencontres = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return listOfEquipeRencontres;
    }
	public List<EquipeRencontre> getEquipeRencontresForRencontre(int rencontreId) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        CriteriaBuilder builder = session.getCriteriaBuilder();
	        CriteriaQuery<EquipeRencontre> criteriaQuery = builder.createQuery(EquipeRencontre.class);
	        Root<EquipeRencontre> root = criteriaQuery.from(EquipeRencontre.class);
	        
	        // Join to fetch the related Equipe and Rencontre
	        root.fetch("equipe", JoinType.INNER);
	        root.fetch("rencontre", JoinType.INNER);
	        
	        // Add condition to select EquipeRencontre for the given rencontreId
	        criteriaQuery.where(builder.equal(root.get("id").get("idRencontre"), rencontreId));

	        return session.createQuery(criteriaQuery).getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return Collections.emptyList();
	    }
	}
    private void handleTransactionException(Transaction transaction, Exception e) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
}
