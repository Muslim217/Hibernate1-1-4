package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private final String sqlCreate = "CREATE TABLE IF NOT EXISTS `kata_db`.`table1` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NULL,\n" +
            "  `lastname` VARCHAR(45) NULL,\n" +
            "  `age` INT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)";

    private final String sqlDropTable = "DROP TABLE IF EXISTS `table1`";
    private static SessionFactory sessionFactory;


    public UserDaoHibernateImpl() {


    }


    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory(sessionFactory).openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sqlCreate);
            query.executeUpdate();
            transaction.commit();
            session.close();

        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            e.printStackTrace();

        }


    }

    @Override
    public void dropUsersTable() {

        try (Session session = getSessionFactory(sessionFactory).openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sqlDropTable).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSessionFactory(sessionFactory).openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        }


    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory(sessionFactory).getCurrentSession()) {
            session.beginTransaction();
            User userForDelete = session.get(User.class, id);
            session.delete(userForDelete);
            session.getTransaction().commit();
        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        }


    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory(sessionFactory).getCurrentSession()) {
            session.beginTransaction();
            List<User> result = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
            return result;

        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
            return null;

        }

    }

    @Override
    public void cleanUsersTable() {
        try (Session session = getSessionFactory(sessionFactory).getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete User ").executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        }


    }
}
