package com.example.shoppingmanagmentapplication.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingmanagmentapplication.R;
import com.example.shoppingmanagmentapplication.firebase.ShoppingItemsService;
import com.example.shoppingmanagmentapplication.model.ShoppingItem;
import com.example.shoppingmanagmentapplication.model.ActiveUser;
import com.example.shoppingmanagmentapplication.model.eShoppingType;
import com.example.shoppingmanagmentapplication.utils.AddItemClientValidationUtils;
import com.example.shoppingmanagmentapplication.utils.ToastUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;


public class AddItemFragment extends Fragment {
    private EditText itemName;
    private EditText itemAmount;
    private TextView itemTypeSelector;
    private AlertDialog.Builder itemTypeSelectorDialog;
    private Button addItem;
    private final String[] shoppingTypes = Arrays.stream(eShoppingType.values())
            .map(Enum::name)
            .toArray(String[]::new);
    private int selectedShoppingTypePosition = -1;

    public AddItemFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        initializeUIReferences(view);
        initializeButtonsOnClick();
        initializeItemTypeSelectorDialog();

        return view;
    }


    private void initializeUIReferences(View view)
    {
        itemName = view.findViewById(R.id.itemNameField);
        itemAmount = view.findViewById(R.id.itemAmountField);
        itemTypeSelector = view.findViewById(R.id.selectItemType);
        addItem = view.findViewById(R.id.addBtn);
    }

    private void initializeButtonsOnClick()
    {
        itemTypeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemTypeSelectorDialog.show();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = itemName.getText().toString();
                int amount = Integer.parseInt(itemAmount.getText().toString());

                if(AddItemClientValidationUtils.validateItem(name, amount, selectedShoppingTypePosition, getContext()))
                {
                    eShoppingType shoppingType = Enum.valueOf(eShoppingType.class,shoppingTypes[selectedShoppingTypePosition]);

                    ShoppingItem newShoppingItem = new ShoppingItem(
                            shoppingType,
                            amount,
                            name);

                    
                    ShoppingItemsService.tryAddShoppingItemOfUserToDatabase(
                            newShoppingItem,
                            () -> Navigation.findNavController(requireView()).navigate(R.id.action_addItemFragment_to_menuFragment),
                            (e)-> ToastUtils.createCustomToast("A failure occurred when trying to add the item", getContext()));
                }
                else
                {
                    ToastUtils.createCustomToast("One of the field are invalid",getContext());
                }
            }
        });
    }
    private void initializeItemTypeSelectorDialog()
    {
        itemTypeSelectorDialog = new AlertDialog.Builder(getContext());
        itemTypeSelectorDialog.setTitle("Select Item Type");
        itemTypeSelectorDialog.setCancelable(false);

        itemTypeSelectorDialog.setSingleChoiceItems(shoppingTypes, selectedShoppingTypePosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedShoppingTypePosition = which;
            }
        });

        itemTypeSelectorDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemTypeSelector.setText(shoppingTypes[selectedShoppingTypePosition]);
                dialog.dismiss();
            }
        });

        itemTypeSelectorDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }
}