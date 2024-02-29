package com.example.shoppingmanagmentapplication.utils;

import android.content.Context;

public class AddItemClientValidationUtils {
    public static boolean validateItem(String name, int amount, int type, Context context)
    {
        if(checkIfEmpty(name) || checkIfEmpty(amount) || type == -1)
        {
            ToastUtils.createCustomToast("One of the fields is empty", context);
            return false;
        }

        if(name.length() > 20)
        {
            ToastUtils.createCustomToast("item name must be below 20 letters", context);
            return false;
        }

        if(amount <= 0)
        {
            ToastUtils.createCustomToast("item amount must be above 0", context);
            return false;
        }

        return true;
    }

    private static <T> boolean checkIfEmpty(T itemProperty)
    {
        return itemProperty.toString().trim().isEmpty();
    }
}
