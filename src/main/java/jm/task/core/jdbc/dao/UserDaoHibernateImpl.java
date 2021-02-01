package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql =  "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(45) NOT NULL, " +
                "lastName VARCHAR (45) NOT NULL, " +
                "age TINYINT NOT NULL, PRIMARY KEY (id))";
        try (Session session = new Util().getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql =  "DROP TABLE IF EXISTS users";
        try (Session session = new Util().getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = new Util().getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = new Util().getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = new Util().getSessionFactory().openSession()) {
            session.beginTransaction();
            users = session.createSQLQuery("SELECT * FROM users").addEntity(User.class).list();
            session.getTransaction().commit();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = new Util().getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
