package fish.payara.users;

import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class UserFacade {
    public static Map<String,  User> users = new HashMap<>();

    static {
        users.put("admin", new User("admin", "123456", List.of("admin")));
        users.put("luis", new User("luis", "123456", List.of("user")));
    }

    public User getUser(String userName) {
        return users.get(userName);
    }

    public boolean verifyUserPassword(String userName, String password) {
        User user = getUser(userName);
        return user.password.equals(password);
    }
}
