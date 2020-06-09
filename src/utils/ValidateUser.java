package utils;

import user.User;
import user.hashGenerator.HashGeneratorException;
import user.hashGenerator.SHA1Generator;
import utils.dao.UserDAO;
import utils.dataSource.database.Database;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ValidateUser {
    public static boolean checkUser(User user, Database database) {
        try {
            return checkLogin(user, database) && checkPassword(user, database);
        } catch (HashGeneratorException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String register(User user, Database database) {
        if (!checkLogin(user, database)) {
            PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                    .useDigits(true)
                    .useLower(true)
                    .useUpper(true)
                    .build();
            String password = passwordGenerator.generate(8);
            Thread mailThread = new Thread() {
                public void run() {
                    SendMail.getInstance().sendLoginAndPassword(user.getLogin(), password, user.getEmail());
                }
            };
            mailThread.start();
            try {
                user.setPassword(new SHA1Generator().generateHashWithPepperAndSalt(password));
            } catch (HashGeneratorException e) {
                e.printStackTrace();
            }
            UserDAO userDAO = new UserDAO(database);
            userDAO.insert(user);
            return "Successful registration";
        } else {
            return "Login already exists";
        }
    }

    private static boolean checkLogin(User user, Database database) {
        String login = user.getLogin();
        UserDAO userDAO = new UserDAO(database);
        User userFromTable = userDAO.getByLogin(login);
        return userFromTable != null;
    }

    private static boolean checkPassword(User user, Database database) throws HashGeneratorException {
        String login = user.getLogin();
        UserDAO userDAO = new UserDAO(database);
        User userFromTable = userDAO.getByLogin(login);
        return new SHA1Generator().generateHashWithPepperAndSalt(user.getPassword()).equals(userFromTable.getPassword());
    }
}
