package com.matchSportifs.dao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.matchSportifs.model.Adresse;
import com.matchSportifs.model.ClassementEquipes;
import com.matchSportifs.model.User;

import java.util.List;

import javax.persistence.*;

import com.matchSportifs.util.HibernateUtil;
public class UserDAO {
    public User findUserByEmail(String email) {
        Transaction transaction = null;
        User user = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM User u WHERE u.email = :email";
            transaction = session.beginTransaction();
            TypedQuery<User> query = session.createQuery("FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);

            transaction.commit();
            try {
                user = query.getSingleResult();
            } catch (NoResultException e) {
                user = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    public void saveUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = (Transaction) session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
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
}