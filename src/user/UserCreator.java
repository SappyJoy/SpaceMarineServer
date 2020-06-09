package user;

import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class UserCreator {
    private Scanner in;

    public UserCreator(Scanner in) {
        this.in = in;
    }

    public boolean inputValue(String message, boolean required, Predicate<String> lambda) {
        while (true) {
            System.out.println(message);
            String input = in.nextLine();
            if (input == null) {
                System.out.print(System.lineSeparator() + "Wrong format ");
            } else if (input.equals("cancel")) {
                System.out.print("Cancel creating a new user");
                return false;
            } else if (input.equals("")) {
                if (required) System.out.print("Required field ");
                else return true;
            } else if (lambda.test(input)) {
                return true;
            }
        }
    }

    public User create() {
        System.out.println("Enter login and password or write \"cancel\"");
        User user = new User();

        if (!inputValue("Login: ", true,
                input -> {
                    if (Pattern.matches("[0-9A-Za-z- ]+$", input)) {
                        user.setLogin(input);
                        return true;
                    }
                    System.out.println("Wrong value");
                    return false;
                })) {
            return null;
        }

        return user;
    }
}
