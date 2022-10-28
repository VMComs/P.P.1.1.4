package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        List<User> list = Arrays.asList(new User("Ololo", "Waterman", (byte) 14)
                , new User("Jesus", "Black", (byte) 45)
                , new User("Souls", "Dark", (byte) 32)
                , new User("Karl", "Gustav", (byte) 70));
        list.forEach((x) -> userService.saveUser(x.getName(), x.getLastName(), x.getAge()));

        List<User> resultList = userService.getAllUsers();
        resultList.forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
