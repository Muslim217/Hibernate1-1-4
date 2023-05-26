package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
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


    public UserDaoHibernateImpl() {


    }

    private Session session;


    @Override
    public void createUsersTable() {
        session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery(sqlCreate);
        query.executeUpdate();
        transaction.commit();
        session.close();


    }

    @Override
    public void dropUsersTable() {

        session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery(sqlDropTable).addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session = getSessionFactory().openSession();
        session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        session.getTransaction().commit();
        session.close();


    }

    @Override
    public void removeUserById(long id) {
        session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        User userForDelete = session.get(User.class, id);
        session.delete(userForDelete);
        session.getTransaction().commit();


    }

    @Override
    public List<User> getAllUsers() {
        session = Util.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<User> result = session.createQuery("from User").getResultList();
        session.getTransaction().commit();
        return result;
    }

    @Override
    public void cleanUsersTable() {
        session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete User ").executeUpdate();
        session.getTransaction().commit();


    }
}
