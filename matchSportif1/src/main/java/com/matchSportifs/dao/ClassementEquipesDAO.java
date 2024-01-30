package com.matchSportifs.dao;

import com.matchSportifs.model.ClassementEquipes;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import com.matchSportifs.util.HibernateUtil;

import javax.persistence.TypedQuery;

public class ClassementEquipesDAO {

    public void saveClassementEquipes(ClassementEquipes classementEquipes) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(classementEquipes);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void updateClassementEquipes(ClassementEquipes classementEquipes) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(classementEquipes);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public void deleteClassementEquipes(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            ClassementEquipes classementEquipes = session.get(ClassementEquipes.class, id);
            if (classementEquipes != null) {
                session.delete(classementEquipes);
                System.out.println("ClassementEquipes is deleted");
            }
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
    }

    public ClassementEquipes getClassementEquipes(Long long1) {
        Transaction transaction = null;
        ClassementEquipes classementEquipes = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            classementEquipes = session.get(ClassementEquipes.class, long1);
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return classementEquipes;
    }

    public List<ClassementEquipes> getAllClassementEquipes() {
        Transaction transaction = null;
        List<ClassementEquipes> listOfClassementEquipes = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TypedQuery<ClassementEquipes> query = session.createQuery("FROM ClassementEquipes", ClassementEquipes.class);
            listOfClassementEquipes = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, e);
        }
        return listOfClassementEquipes;
    }

    private void handleTransactionException(Transaction transaction, Exception e) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
}
