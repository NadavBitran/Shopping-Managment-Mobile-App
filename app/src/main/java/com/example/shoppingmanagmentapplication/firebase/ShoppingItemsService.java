package com.example.shoppingmanagmentapplication.firebase;

import androidx.annotation.NonNull;

import com.example.shoppingmanagmentapplication.model.ActiveUser;
import com.example.shoppingmanagmentapplication.model.ShoppingItem;
import com.example.shoppingmanagmentapplication.model.eShoppingType;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.function.Consumer;

public class ShoppingItemsService {

    public static DatabaseReference getShoppingListDBOfUser(String userId)
    {
        return FirebaseDatabase.getInstance().getReference("shoppingItems").child(userId);
    }

    public static void tryAddShoppingItemOfUserToDatabase(ShoppingItem shoppingItemToAdd, Runnable onSuccess, Consumer<Exception> onFailure) {

        DatabaseReference itemRef = ShoppingItemsService.getShoppingListDBOfUser(ActiveUser.getCurrentActiveUserUid());
        String itemId = itemRef.push().getKey();
        shoppingItemToAdd.setItemId(itemId);

            itemRef.child(itemId).setValue(shoppingItemToAdd)
                    .addOnSuccessListener(unused -> {
                        if (onSuccess != null) {
                            onSuccess.run();
                        }
                    })
                    .addOnFailureListener(e -> {
                        if (onFailure != null) {
                            onFailure.accept(e);
                        }
                    });

    }

    public static void tryRemoveShoppingItemOfUserFromDataBase(String itemId, Runnable onSuccess, Consumer<Exception> onFailure)
    {
        DatabaseReference itemRef = ShoppingItemsService.getShoppingListDBOfUser(ActiveUser.getCurrentActiveUserUid()).child(itemId);

        itemRef.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
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
