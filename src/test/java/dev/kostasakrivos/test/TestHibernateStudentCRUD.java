package dev.kostasakrivos.test;

import dev.kostasakrivos.test.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;

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

}
