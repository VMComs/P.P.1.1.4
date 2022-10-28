package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.util.Properties;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Util {
    private final static String userName = "root";
    private final static String password = "Cjkmlkzdct[1";
    private final static String connectionURL = "jdbc:mysql://localhost:3306/new_schema?serverTimezone=Europe/Moscow&useSSL=false";
    private final static String JDBCDriver = "com.mysql.jdbc.Driver";


    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(JDBCDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(connectionURL, userName, password);
    } // реализуйте настройку соеденения с БД

    public static class HibernateUtil {

        private static SessionFactory sessionFactory;

        public static SessionFactory getSessionFactory() {
            if (sessionFactory == null) {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, JDBCDriver);
                settings.put(Environment.URL, connectionURL);
                settings.put(Environment.USER, userName);
                settings.put(Environment.PASS, password);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "false");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
            return sessionFactory;
        }
    }
}
