package ru.job4j_hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.job4j_hibernate.model.User;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class HibernateRun {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        System.out.println("Creating new user.");
        User tempUser = new User("John", new GregorianCalendar(2020, Calendar.AUGUST, 18));
        System.out.println(tempUser.toString());
        System.out.println();

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.save(tempUser);
            session.getTransaction().commit();
            System.out.println("User was saved.");
            System.out.println();
        }

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            User foundUser = session.get(User.class, tempUser.getId());
            System.out.println("Found user:");
            System.out.println(foundUser.toString());
            System.out.println();

            foundUser.setName("Billy");
            System.out.println("Changed user to:");
            System.out.println(foundUser.toString());
            System.out.println();
            session.save(foundUser);
            session.getTransaction().commit();
        }

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            User foundUser = session.get(User.class, tempUser.getId());
            System.out.println("Found user:");
            System.out.println(foundUser.toString());
            System.out.println();
            session.delete(foundUser);
            System.out.println("Deleted user.");
            session.getTransaction().commit();
        }

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<User> users = session.createQuery("from User").getResultList();
            System.out.println("Found all user:");
            System.out.println(users.toString());
        }
    }
}
