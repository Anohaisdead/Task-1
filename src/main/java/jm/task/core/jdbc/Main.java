package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        try (Connection connection = Util.getConnection()) {
            UserDao userDao = new UserDaoJDBCImpl(connection);

            userDao.createUsersTable();

            userDao.saveUser("Иван1", "Иванов1", (byte) 25);

            userDao.saveUser("Иван2", "Иванов2", (byte) 30);

            userDao.saveUser("Иван3", "Иванов3", (byte) 28);

            userDao.saveUser("Иван4", "Иванов4", (byte) 35);

            for (User user : userDao.getAllUsers()) {
                System.out.println(user);
            }

            userDao.cleanUsersTable();

            userDao.dropUsersTable();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
