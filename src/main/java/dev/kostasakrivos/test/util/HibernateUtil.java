package dev.kostasakrivos.test.util;

import dev.kostasakrivos.test.entity.Student;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class HibernateUtil {

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

}
