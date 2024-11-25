package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {

    }

    public UserDaoJDBCImpl(Connection connection) {
        this.connection = connection;
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGSERIAL PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "lastName VARCHAR(255) NOT NULL, " +
                "age SMALLINT NOT NULL" +
                ");";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
            System.out.println("Table 'users' created successfully (if it didn't already exist).");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while creating 'users' table: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF exists users;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
            System.out.println("Table 'users' drop successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while dropping 'users' table: " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User " + name + " " + lastName + " has been added to the database.");
        } catch (SQLException e) {
            System.err.println("Error while creating user: " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("User with ID " + id + " has been removed.");
        } catch (SQLException e) {
            System.err.println("Error while remove users: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
            System.out.println("All users have been retrieved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while retrieving users: " + e.getMessage());
        }

        return users;
    }

    public void cleanUsersTable() {
        String sql = "Truncate table users";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
            System.out.println("Table 'users' cleaned successfully.");
        }catch (SQLException e) {
            System.err.println("Error while cleaning table: " + e.getMessage());
        }
    }
}
