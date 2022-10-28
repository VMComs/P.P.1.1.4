package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import static jm.task.core.jdbc.dao.UserDaoJDBCImpl.createTableSQL;
import static jm.task.core.jdbc.dao.UserDaoJDBCImpl.dropTableSQL;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.HibernateUtil.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(createTableSQL).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Таблица уже создана");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(dropTableSQL).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Таблица уже удалена");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = sessionFactory.openSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            userList = session.createQuery("from User").list();
            session.getTransaction().commit();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
