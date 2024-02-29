package com.example.shoppingmanagmentapplication.utils;

import android.content.Context;

import com.example.shoppingmanagmentapplication.utils.ToastUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginClientValidationUtils {
    public static boolean validateLogin(String email , String password, Context context)
    {
        if(!isValidEmail(email)) {
            ToastUtils.createCustomToast("email is not in right format", context);
            return false;
        }

        if(!isValidPassword(password))
        {
            ToastUtils.createCustomToast("password is not in right format", context);
            return false;
        }

        return true;
    }

    public static boolean validateRegister(String email, String password, String username, Context context)
    {
        if(!isValidEmail(email)) {
            ToastUtils.createCustomToast("email is not in right format", context);
            return false;
        }

        if(!isValidPassword(password))
        {
            ToastUtils.createCustomToast("password is not in right format", context);
            return false;
        }

        if(!isValidUsername(username))
        {
            ToastUtils.createCustomToast("username must be as least 3 characters", context);
            return false;
        }

        return true;
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