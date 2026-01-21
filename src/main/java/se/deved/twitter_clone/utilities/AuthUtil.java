package se.deved.twitter_clone.utilities;

import se.deved.twitter_clone.models.User;

public class AuthUtil {
    
    public static boolean validatePassword(User user, String password) {
        return user.getPassword().equals(password);
    }
}
