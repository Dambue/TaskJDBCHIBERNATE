package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Alex", "Ivanov", (byte) 22);
        userService.saveUser("Roman", "Petrov", (byte) 32);
        userService.saveUser("Semen", "Sidorov", (byte) 12);
        userService.saveUser("Petr", "Vasechkin", (byte) 52);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
        // реализуйте алгоритм здесь
    }
}
