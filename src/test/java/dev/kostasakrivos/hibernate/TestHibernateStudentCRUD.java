package dev.kostasakrivos.hibernate;

import dev.kostasakrivos.hibernate.entity.Student;
import dev.kostasakrivos.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class TestHibernateStudentCRUD {

    private static SessionFactory factory;
    private static Session session;

    @BeforeClass
    public static void setupTest() {
        // gets the current SessionFactory (or creates if null)
        factory = HibernateUtil.getSessionFactory();
    }

    @AfterClass
    public static void teardownTest() {
        // closes the session factory
        HibernateUtil.shutdown();
    }

    @Test
    public void testCreateUser() {
        // creates a Student
        System.out.println("\nCreating a new Student...");
        Student student = new Student("hibernate-firstName", "hibernate-lastName");

        // creates a Session
        session = factory.openSession();

        // starts a Transaction
        session.beginTransaction();

        // saves the Student
        System.out.println("Saving the Student...");
        session.save(student);

        // commits the Transaction
        session.getTransaction().commit();
        System.out.println("Done!\n");

        System.out.println("Looking for that Student...");

        // SELECT that student
        assertThat(session.get(Student.class, 1), not(nullValue()));
        System.out.println("Done!\n");
    }

}
