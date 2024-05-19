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

public class UserDaoHibernateImpl implements UserDao {
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
            System.out.println("создала таблицу");
        } catch (Exception ex) {
            System.out.println("111111111111");

            System.out.println(ex);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery("drop TABLE if exists user").addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            System.out.println("удаление таблицы");
        } catch (Exception ex) {
            System.out.println("22222");

            System.out.println(ex);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
            System.out.println("добавили в таблицу поля user");
        } catch (Exception ex) {
            System.out.println("3333333");

            System.out.println(ex);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User();
            session.remove(user);
            transaction.commit();
            System.out.println("удаление по id user определенного");
        } catch (Exception ex) {
            System.out.println("44444");

            System.out.println(ex);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            users = session.createQuery("from User", User.class).getResultList();
            System.out.println("запрос на вывод данных в таблице user");
        } catch (Exception ex) {
            System.out.println("55555");

            System.out.println(ex);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("запрос на удаление полностью таблицы");
        } catch (Exception ex) {
            System.out.println("6666");

            System.out.println(ex);
        }
    }
}
