package com.example.shoppingmanagmentapplication.model;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public final class ActiveUser {
    private static User activeUser;

    public static String getCurrentActiveUserUsername() {return activeUser.getUsername();}

    public static String getCurrentActiveUserEmail() {return activeUser.getEmail();}

    public static String getCurrentActiveUserUid() {return activeUser.getUid();}

    public static void setCurrentActiveUserDetails(User newCurrentActiveUserDetails) {activeUser = newCurrentActiveUserDetails;}

}
