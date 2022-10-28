package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    final static String createTableSQL = "create table users_table"
            + "(user_id int auto_increment,"
            + "name varchar(20) not null,"
            + "lastName varchar(20) not null,"
            + "age int not null,"
            + "primary key (user_id))";
    final static String dropTableSQL = "drop table users_table";
    private final static String insertUserSQL = "insert into users_table (name, lastName, age) VALUES (?, ?, ?)";
    private final static String getAllUsersSQL = "select * from users_table";
    private final static String removeByIdSQL = "delete from users_table where user_id = ?";
    private final static String cleanTableSQL = "delete from users_table";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(createTableSQL);
        } catch (SQLException ex) {
            System.out.println("users уже создана");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(dropTableSQL);
        } catch (SQLException ex) {
            System.out.println("Такая база уже не существует");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement pstatement = connection.prepareStatement(insertUserSQL);
            pstatement.setString(1, name);
            pstatement.setString(2, lastName);
            pstatement.setInt(3, age);
            pstatement.execute();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement prstatement = connection.prepareStatement(removeByIdSQL);
            prstatement.setString(1, String.valueOf(id));
            prstatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getAllUsersSQL);
            while (resultSet.next()) {
                long id = resultSet.getInt("user_id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(cleanTableSQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
