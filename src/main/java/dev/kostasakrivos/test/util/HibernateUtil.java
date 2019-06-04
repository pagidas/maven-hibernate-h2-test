package dev.kostasakrivos.test.util;

import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private static StandardServiceRegistry configureServiceRegistry() {
        return new StandardServiceRegistryBuilder()
                .applySettings(getProperties())
                .build();
    }

}
