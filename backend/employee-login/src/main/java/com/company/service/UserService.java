
package com.company.service;

import org.mindrot.jbcrypt.BCrypt;

import com.company.dao.UserDAO;
import com.company.entity.User;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public String register(String name, String email, String password) {

        if (name == null || name.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.length() < 8) {
            return "Invalid input. Enter a name, email, and password of at least 8 characters.";
        }

        name = name.trim();
        email = email.trim().toLowerCase();

        if (userDAO.findByEmail(email) != null) {
            return "Email is already registered.";
        }

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(name, email, passwordHash);

        boolean registered = userDAO.registerUser(user);

        return registered
                ? "Registration successful."
                : "Registration failed. Please try again.";
    }

    public User login(String email, String password) {

        if (email == null || email.trim().isEmpty()
                || password == null || password.isEmpty()) {
            return null;
        }

        email = email.trim().toLowerCase();

        User user = userDAO.findByEmail(email);

        if (user != null
                && BCrypt.checkpw(password, user.getPasswordHash())) {
            return user;
        }

        return null;
    }
}
