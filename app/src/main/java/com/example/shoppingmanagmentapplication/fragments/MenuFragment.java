package com.example.shoppingmanagmentapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingmanagmentapplication.R;
import com.example.shoppingmanagmentapplication.firebase.ShoppingItemsService;
import com.example.shoppingmanagmentapplication.model.ActiveUser;
import com.example.shoppingmanagmentapplication.model.ShoppingItem;
import com.example.shoppingmanagmentapplication.utils.ToastUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MenuFragment extends Fragment {


    private TextView welcomeMessage;
    private ImageButton addItemBtn;
    private RecyclerView itemRecyclerView;
    private CustomShoppingItemAdapter itemAdapter;

    public MenuFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_menu, container, false);

        initializeUIReferencesAndValues(view);
        initializeButtonsOnClickEvent(view);
        initializeRecyclerList();

        return view;
    }

    private void initializeUIReferencesAndValues(View view)
    {
        welcomeMessage = view.findViewById(R.id.welcomeMessage);
        addItemBtn = view.findViewById(R.id.addItem);
        itemRecyclerView = view.findViewById(R.id.itemList);

        welcomeMessage.setText(String.format("Hello %s!" , ActiveUser.getCurrentActiveUserUsername()));
    }

    private void initializeButtonsOnClickEvent(View view)
    {
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_addItemFragment);
            }
        });
    }

    private void initializeRecyclerList()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        itemRecyclerView.setLayoutManager(layoutManager);
        itemRecyclerView.setItemAnimator(new DefaultItemAnimator());
        List<ShoppingItem> usersShoppingItems = new ArrayList<>();
        itemAdapter = new CustomShoppingItemAdapter(usersShoppingItems, getContext());
        itemRecyclerView.setAdapter(itemAdapter);
        String currentUserId = ActiveUser.getCurrentActiveUserUid();


        DatabaseReference userItemsRef = ShoppingItemsService.getShoppingListDBOfUser(currentUserId);
        userItemsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ShoppingItem newShoppingItem = snapshot.getValue(ShoppingItem.class);
                usersShoppingItems.add(newShoppingItem);
                itemAdapter.notifyItemInserted(0);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ToastUtils.createCustomToast("an unexpected error occurred", getContext());
            }
        });
    }
}