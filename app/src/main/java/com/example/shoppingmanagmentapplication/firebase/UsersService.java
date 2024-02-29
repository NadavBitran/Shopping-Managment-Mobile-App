package com.example.shoppingmanagmentapplication.firebase;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.shoppingmanagmentapplication.R;
import com.example.shoppingmanagmentapplication.model.ActiveUser;
import com.example.shoppingmanagmentapplication.model.User;
import com.example.shoppingmanagmentapplication.utils.ToastUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.function.Consumer;

public final class UsersService {

    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public static void trySignIn(String email, String password, Runnable onSuccess, Consumer<Exception> onFailure)
    {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String userId = currentUser.getUid();

                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

                            usersRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists())
                                    {
                                           User user = snapshot.getValue(User.class);
                                           ActiveUser.setCurrentActiveUserDetails(user);
                                           onSuccess.run();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                            onFailure.accept(error.toException());
                                }
                            });
                            }
                    }
                });
    }

    public static void trySignUp(User user, String password, Runnable onSuccess, Consumer<Exception> onFailure) {
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String userId = currentUser.getUid();

                            user.setUid(userId);

                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

                            usersRef.setValue(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            ActiveUser.setCurrentActiveUserDetails(user);
                                            onSuccess.run();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            onFailure.accept(e);
                                        }
                                    });
                        }
                    }
                });

    }
}

