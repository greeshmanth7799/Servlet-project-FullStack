
package com.company.util;

public class HibernateTest {

    public static void main(String[] args) {
        try {
            HibernateUtil.getSessionFactory();
            System.out.println("Hibernate initialized successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
