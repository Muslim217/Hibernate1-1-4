package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.util.Util;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final String sqlCreate = "CREATE TABLE IF NOT EXISTS `kata_db`.`table` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NULL,\n" +
            "  `lastname` VARCHAR(45) NULL,\n" +
            "  `age` INT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)";

    private final String sqlDropTable = "DROP TABLE IF EXISTS `table`";
    private final String sqlSaveUser = "INSERT INTO `table` (name, lastname, age) VALUES (?,?,?)";
    private final String sqlRemoveUser = "DELETE FROM `table` WHERE id=?";

    private final String sqlGetAllUsers = "SELECT * FROM `table`";


    private final String cleanUsersTable = "DELETE  FROM `table`";

    private final Util util = new Util();


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {


        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate(sqlCreate);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void dropUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate(sqlDropTable);

        } catch (SQLException t) {
            t.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(sqlSaveUser)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(sqlRemoveUser)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement = util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlGetAllUsers);
            while (resultSet.next()) {
                userList.add(
                        new User(resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getByte(4)));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;


    }

    public void cleanUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate(cleanUsersTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
