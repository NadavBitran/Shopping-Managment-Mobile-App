package com.example.shoppingmanagmentapplication.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class UserList {
    private final static Map<String , User> userList = new HashMap<>();
    private UserList() {}

    public static void initializeFakeUserList()
    {
        User u1 = new User("dani" , "dani@gmail.com" , "@Dani123");
        User u2 = new User("avi" , "avi@gmail.com" , "@Avi123");
        User u3 = new User("shlomi" , "shlomi@gmail.com" , "@Shlomi123");

        userList.put("dani@gmail.com" , u1);
        userList.put("avi@gmail.com" , u2);
        userList.put("shlomi@gmail.com" , u3);
    }
    public static void addUser(User user) throws Exception
    {
        if(user == null) throw new Exception("tried to add empty user");

        userList.put(user.getEmail() , user);
    }
    public static boolean isUserExist(String userEmail , String password)
    {
        User user = userList.get(userEmail);

        if(user == null) return false;

        return user.getEmail().equals(userEmail) && user.getPassword().equals(password);
    }
    public static boolean isUserWithEmailExist(String userEmail)
    {
        User user = userList.get(userEmail);

        return user != null;
    }

    public static User getUserFromEmailAndPassword(String userEmail , String password)
    {
        User user = userList.get(userEmail);

        if(user == null) return null;

        if(!user.getPassword().equals(password)) return null;

        return user;
    }
}
