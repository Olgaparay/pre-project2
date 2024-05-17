package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.persistence.Id;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sqlCommand = "CREATE TABLE if not exists users (Id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(50), age INT)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
            System.out.println("база создана");

        } catch (Exception ex) {
            System.out.println("111111111111");

            System.out.println(ex);
        }
    }

    public void dropUsersTable() {
        String sqlCommand = "drop TABLE if exists users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
            System.out.println("база данных удалена!");

        } catch (Exception ex) {
            System.out.println("2222222222222");

            System.out.println(ex);
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into users (name,lastName,age) values (?,?,?)")) {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();  // выполняем запрос на сохранение
            System.out.println("user сохранен");

        } catch (Exception ex) {
            System.out.println("3333333333333333");

            System.out.println(ex);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from users where Id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("user удален");

        } catch (Exception ex) {
            System.out.println("444444444444444444");

            System.out.println(ex);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()){

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while(resultSet.next()){

                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                Byte age = resultSet.getByte(4);
                users.add(new User(name,lastName,age));
            }
        } catch (Exception ex) {
            System.out.println("5555555555555");

            System.out.println(ex);
        }
        return users;
    }

    public void cleanUsersTable() {
        String sqlCommand = "truncate table users";
        try (Statement statement = connection.createStatement();) {
            statement.executeUpdate(sqlCommand);
            System.out.println("таблица удалена");

        } catch (Exception ex) {
            System.out.println("66666666666666");

            System.out.println(ex);
        }
    }
}
