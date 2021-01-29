package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/mydbtest?autoReconnect=true&useSSL=FALSE&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    private Connection connection;

    public Connection getConnection() throws SQLException {
        return connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
    }// реализуйте настройку соеденения с БД
}
