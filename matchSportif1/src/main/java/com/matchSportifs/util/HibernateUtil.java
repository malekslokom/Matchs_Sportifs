package com.matchSportifs.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.matchSportifs.model.*;
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");  // Use the updated driver class
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/match_sportif");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");  // Use the updated dialect

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "update");  // Use "update" to avoid dropping and recreating the table

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(LieuAcceuil.class);
                configuration.addAnnotatedClass(Rencontre.class);
                configuration.addAnnotatedClass(Joueur.class);
                configuration.addAnnotatedClass(Participant.class);
                configuration.addAnnotatedClass(EquipeRencontre.class);
                configuration.addAnnotatedClass(Entraineur.class);
                configuration.addAnnotatedClass(Penalite.class);
                configuration.addAnnotatedClass(Arbitre.class);
                configuration.addAnnotatedClass(ButRencontre.class);
                configuration.addAnnotatedClass(TempsDeJeux.class);
                configuration.addAnnotatedClass(Adresse.class);
                configuration.addAnnotatedClass(Equipe.class);
                configuration.addAnnotatedClass(ClassementEquipes.class);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
                System.out.println("Hibernate Java Config serviceRegistry created");
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                return sessionFactory;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
