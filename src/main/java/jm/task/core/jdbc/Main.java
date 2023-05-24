package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ali","Guseinov", (byte) 22);
        userService.saveUser("Azizkhan","Abdulaev",(byte) 22);
        userService.saveUser("Abumuslim","Safaraliev",(byte) 22);
        userService.saveUser("Magomed","Safaraliev",(byte) 18);
        System.out.println(userService.getAllUsers().toString());
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
