package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/mydbtest?autoReconnect=true&useSSL=FALSE&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    private Connection connection;
    private SessionFactory sessionFactory;

    public Connection getConnection() throws SQLException {
        return connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://127.0.0.1:3306/mydbtest?autoReconnect=true&useSSL=FALSE&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "1234");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                configuration.setProperties(settings).addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.err.println("Исключение" + e);
            }
        }
        return sessionFactory;
    }// реализуйте настройку соеденения с БД
}
