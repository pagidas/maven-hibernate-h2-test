package dev.kostasakrivos.hibernate.util;

import dev.kostasakrivos.hibernate.entity.Student;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Bootstraps Hibernate functionality and configuration using only programmatic style.
 *
 * Calling {@link #getSessionFactory()} and, after transactions are committed, {@link #shutdown()}
 * will be just enough to get Hibernate up-and-running.
 */
public class HibernateUtil {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    /**
     * Creates the single instance of SessionFactory if there not already one.
     *
     * - Creates new MetadataSources using a configured StandardServiceRegistry
     * - Adds Annotated classes in the MetadataSources for ORM functionality
     * - Finally builds the SessionFactory
     *
     * @return The configured and built SessionFactory instance.
     */
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

    /**
     * Builds and configures the StandardServiceRegistry, parsing a hibernate.properties file,
     * in order to be create a new instance of MetadataSources.
     *
     * @return The configured and built StandardServiceRegistry.
     */
    private static StandardServiceRegistry configureServiceRegistry() {
        return new StandardServiceRegistryBuilder()
                .applySettings(getProperties())
                .build();
    }

    /**
     * Parses and loads the hibernate.properties file from a specified
     * path in the project directory, into a Properties instance.
     *
     * @return The Properties instance, containing the hibernate.properties data.
     */
    private static Properties getProperties() {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("src/main/resources/hibernate.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }

    /**
     * Adds annotated classes into a given MetadataSource.
     *
     * This where all the POJO entities with JPA annotations are hardcoded,
     * so that the logic behind creating a SessionFactory is decluttered.
     *
     * @param metadataSources The MetadataSources instance which will contain the annotated classes
     */
    private static void addEntityClasses(MetadataSources metadataSources) {
        metadataSources.addAnnotatedClass(Student.class);
    }

    /**
     * Destroys the StandardServiceRegistry with the hibernate.properties,
     * thus destroying the SessionFactory.
     */
    public static void shutdown() {
        if(registry != null)
            StandardServiceRegistryBuilder.destroy(registry);
    }

}
