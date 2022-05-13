package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.UtilDataBase;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

    private final Session session = UtilDataBase.getSessionFactory().openSession();

    @Override
    public void createUsersTable() {
        String sql =  "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(45) NOT NULL, " +
                "last_name VARCHAR (45) NOT NULL, " +
                "age TINYINT NOT NULL, PRIMARY KEY (id))";
        try {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception exception) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql =  "DROP TABLE IF EXISTS users";
        try {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception exception) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception exception) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception exception) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            session.beginTransaction();
            users = session.createQuery("FROM User", User.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception exception) {
            session.getTransaction().rollback();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM User", User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception exception) {
            session.getTransaction().rollback();
        }
    }
}
