package com.example.shoppingmanagmentapplication.fragments;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginClientValidationUtils {
    public static boolean validateLogin(String email , String password)
    {
        return isValidEmail(email) && isValidPassword(password);
    }
    public static boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty() && username.trim().length() >= 3;
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty())
            return false;
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";
        return password.matches(passwordRegex);
    }
}