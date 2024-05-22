package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.persistence.Id;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    private Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sqlCommand = "CREATE TABLE if not exists users (Id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(50), age INT)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
            logger.info("Таблица пользователей успешно создана.");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Ошибка при создании таблицы пользователей.", ex);
        }
    }

    public void dropUsersTable() {
        String sqlCommand = "drop TABLE if exists users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
            logger.info("Таблица пользователей успешно удалена.");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Ошибка при удалении таблицы пользователей.", ex);
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into users (name,lastName,age) values (?,?,?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();  // выполняем запрос на сохранение
            logger.info(String.format("Пользователь %s %s успешно сохранен.", name, lastName));

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Ошибка при сохранении пользователя.", ex);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from users where Id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            logger.info(String.format("Пользователь с id %d успешно удален.", id));

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Ошибка при удалении пользователя по id.", ex);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {

                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                Byte age = resultSet.getByte(4);
                users.add(new User(name, lastName, age));
            }
            logger.info("Все пользователи успешно получены.");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Ошибка при получении всех пользователей.", ex);
        }
        return users;
    }

    public void cleanUsersTable() {
        String sqlCommand = "truncate table users";
        try (Statement statement = connection.createStatement();) {
            statement.executeUpdate(sqlCommand);
            logger.info("Таблица пользователей успешно очищена.");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Ошибка при очистке таблицы пользователей.", ex);
        }
    }
}
