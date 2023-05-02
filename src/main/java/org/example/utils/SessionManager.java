package org.example.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

public class SessionManager {
    private static final Configuration configuration = new Configuration();
    private static final SessionFactory sessionFactory;

    static {
        configuration.configure();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        sessionFactory = configuration.buildSessionFactory();
    }

    public static Session get() {
        return sessionFactory.openSession();
    }
}

