
package com.company.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.company.entity.User;
import com.company.util.HibernateUtil;

public class UserDAO {

    public boolean registerUser(User user) {
        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();

            return true;

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();
            return false;
        }
    }

    public User findByEmail(String email) {
        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {

            Query<User> query = session.createQuery(
                "FROM User WHERE email = :email",
                User.class
            );

            query.setParameter("email", email);

            return query.uniqueResult();
        }
    }
}
