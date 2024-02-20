package com.example.shoppingmanagmentapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.shoppingmanagmentapplication.R;
import com.example.shoppingmanagmentapplication.model.ShoppingItem;
import com.example.shoppingmanagmentapplication.model.ShoppingItemList;
import com.example.shoppingmanagmentapplication.model.User;
import com.example.shoppingmanagmentapplication.model.UserList;

import java.util.List;


public class MenuFragment extends Fragment {

    private String username;
    private String email;
    private String password;

    private TextView welcomeMessage;
    private ImageButton addItemBtn;
    private RecyclerView itemRecyclerView;
    private CustomShoppingItemAdapter itemAdapter;
    private final String LOGGED_USER_EMAIL = "logged_email";
    private final String LOGGED_USER_NAME = "logged_username";
    private final String LOGGED_USER_PASSWORD = "logged_password";

    public MenuFragment() {

    }

    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_menu, container, false);

        if(getArguments() != null)
        {
            username = getArguments().getString(LOGGED_USER_NAME);
            email = getArguments().getString(LOGGED_USER_EMAIL);
            password = getArguments().getString(LOGGED_USER_PASSWORD);
        }

        welcomeMessage = view.findViewById(R.id.welcomeMessage);
        addItemBtn = view.findViewById(R.id.addItem);
        itemRecyclerView = view.findViewById(R.id.itemList);

        welcomeMessage.setText(String.format("Hello %s!" , username));

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(LOGGED_USER_EMAIL , email);
                bundle.putString(LOGGED_USER_NAME, username);
                bundle.putString(LOGGED_USER_PASSWORD, password);
                Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_addItemFragment , bundle);
            }
        });

        initializeRecyclerList();


        return view;
    }

    private void initializeRecyclerList()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        User loggedUser = UserList.getUserFromEmailAndPassword(email ,password);
        itemAdapter = new CustomShoppingItemAdapter(loggedUser , ShoppingItemList.getShoppingItemListOfUser(loggedUser));


        itemRecyclerView.setLayoutManager(layoutManager);
        itemRecyclerView.setItemAnimator(new DefaultItemAnimator());
        itemRecyclerView.setAdapter(itemAdapter);
    }

}