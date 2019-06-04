package dev.kostasakrivos.test.util;

import dev.kostasakrivos.test.entity.Student;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class HibernateUtil {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if(sessionFactory == null) {
            try {
                MetadataSources metadataSources = new MetadataSources(configureServiceRegistry());

                addEntityClasses(metadataSources);

                sessionFactory = metadataSources.buildMetadata()
                        .getSessionFactoryBuilder()
                        .build();

            } catch (Exception e) {
                e.printStackTrace();
                if(registry != null)
                    StandardServiceRegistryBuilder.destroy(registry);
            }
        }

        return sessionFactory;
    }

    private static StandardServiceRegistry configureServiceRegistry() {
        return new StandardServiceRegistryBuilder()
                .applySettings(getProperties())
                .build();
    }

    private static Properties getProperties() {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("src/main/resources/hibernate.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }

    private static void addEntityClasses(MetadataSources metadataSources) {
        metadataSources.addAnnotatedClass(Student.class);
    }

    public static void shutdown() {
        if(registry != null)
            StandardServiceRegistryBuilder.destroy(registry);
    }

}
