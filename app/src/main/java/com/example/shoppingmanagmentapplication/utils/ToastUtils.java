package com.example.shoppingmanagmentapplication.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    public static void createCustomToast(String message, Context context)
    {
        Toast.makeText(context , message , Toast.LENGTH_LONG).show();
    }
}
