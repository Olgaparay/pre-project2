package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());
    private SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE if not exists user (Id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(50), age INT)").addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            logger.info("Таблица пользователей успешно создана.");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Ошибка при создании таблицы пользователей.", ex);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery("drop TABLE if exists user").addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            logger.info("Таблица пользователей успешно удалена.");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Ошибка при удалении таблицы пользователей.", ex);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
            logger.info(String.format("Пользователь %s %s успешно сохранен.", name, lastName));
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Ошибка при сохранении пользователя.", ex);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User();
            session.remove(user);
            transaction.commit();
            logger.info(String.format("Пользователь с id %d успешно удален.", id));
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Ошибка при удалении пользователя по id.", ex);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            users = session.createQuery("from User", User.class).getResultList();
            logger.info("Все пользователи успешно получены.");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Ошибка при получении всех пользователей.", ex);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            session.getTransaction().commit();
            logger.info("Таблица пользователей успешно очищена.");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Ошибка при очистке таблицы пользователей.", ex);
        }
    }
}