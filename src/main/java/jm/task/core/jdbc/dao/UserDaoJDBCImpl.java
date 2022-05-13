package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.UtilDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = UtilDataBase.getConnection();

    @Override
    public void createUsersTable() {
        String sql =  "CREATE TABLE IF NOT EXISTS users id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(45) NOT NULL, last_name VARCHAR (45) NOT NULL, age TINYINT NOT NULL, PRIMARY KEY (id))";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ? ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, last_name, age FROM users";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
