package com.example.shoppingmanagmentapplication.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

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
import com.example.shoppingmanagmentapplication.model.ShoppingItem;
import com.example.shoppingmanagmentapplication.model.ShoppingItemList;
import com.example.shoppingmanagmentapplication.model.User;
import com.example.shoppingmanagmentapplication.model.eShoppingType;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class AddItemFragment extends Fragment {

    private String username;
    private String email;
    private String password;
    private EditText itemName;
    private EditText itemAmount;
    private TextView itemTypeSelector;
    private AlertDialog.Builder itemTypeSelectorDialog;
    private Button addItem;

    private final String LOGGED_USER_EMAIL = "logged_email";
    private final String LOGGED_USER_NAME = "logged_username";
    private final String LOGGED_USER_PASSWORD = "logged_password";
    private final String[] shoppingTypes = Arrays.stream(eShoppingType.values())
            .map(Enum::name)
            .toArray(String[]::new);

    private int selectedShoppingTypePosition;

    public AddItemFragment() {
        // Required empty public constructor
    }

    public static AddItemFragment newInstance(String param1, String param2) {
        AddItemFragment fragment = new AddItemFragment();

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
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        if(getArguments() != null)
        {
            initializeLoggedUserInformation();
        }

        initializeUIReferences(view);
        initializeItemTypeSelectorDialog();

        return view;
    }

    private void initializeLoggedUserInformation()
    {
        username = getArguments().getString(LOGGED_USER_NAME);
        email = getArguments().getString(LOGGED_USER_EMAIL);
        password = getArguments().getString(LOGGED_USER_PASSWORD);
    }
    private void initializeUIReferences(View view)
    {
        itemName = view.findViewById(R.id.itemNameField);
        itemAmount = view.findViewById(R.id.itemAmountField);
        itemTypeSelector = view.findViewById(R.id.selectItemType);
        addItem = view.findViewById(R.id.addBtn);

        itemTypeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemTypeSelectorDialog.show();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateUserInputData())
                {
                    ShoppingItemList.addShoppingItemOfUser(
                            new User(username, email, password) ,
                            new ShoppingItem(Enum.valueOf(eShoppingType.class,shoppingTypes[selectedShoppingTypePosition]) , Integer.parseInt(itemAmount.getText().toString()) , itemName.getText().toString()));
                    Bundle bundle = new Bundle();
                    bundle.putString(LOGGED_USER_NAME , username);
                    bundle.putString(LOGGED_USER_EMAIL , email);
                    bundle.putString(LOGGED_USER_PASSWORD , password);
                    Navigation.findNavController(requireView()).navigate(R.id.action_addItemFragment_to_menuFragment , bundle);
                }
                else
                {
                    Toast.makeText(getContext(), "One of the field are invalid",
                            Toast.LENGTH_SHORT).show();
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

    private boolean validateUserInputData()
    {
        if(checkIfEmpty(itemName.getText()) || checkIfEmpty(itemAmount.getText()) || itemTypeSelector.getText().toString().equals("Select Item Type"))
        {
            return false;
        }

        if(itemName.getText().toString().length() > 20)
        {
            return false;
        }

        if(Integer.parseInt(itemAmount.getText().toString()) <= 0)
        {
            return false;
        }

        return true;
    }

    private <T> boolean checkIfEmpty(T itemProperty)
    {
        return itemProperty.toString().trim().isEmpty();
    }
}