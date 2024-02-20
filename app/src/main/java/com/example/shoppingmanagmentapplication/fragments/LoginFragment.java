package com.example.shoppingmanagmentapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.shoppingmanagmentapplication.R;
import com.example.shoppingmanagmentapplication.model.ShoppingItem;
import com.example.shoppingmanagmentapplication.model.ShoppingItemList;
import com.example.shoppingmanagmentapplication.model.User;
import com.example.shoppingmanagmentapplication.model.UserList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class LoginFragment extends Fragment {

    private Button loginOptionBtn;
    private Button registerOptionBtn;
    private Button continueBtn;

    private TextView emailField;
    private TextView passwordField;
    private TextView userNameField;

    private eLoginOption currentLoginOption;
    private final String LOGGED_USER_EMAIL = "logged_email";
    private final String LOGGED_USER_NAME = "logged_username";
    private final String LOGGED_USER_PASSWORD = "logged_password";

    public LoginFragment() {

    }


    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginOptionBtn = view.findViewById(R.id.Login);
        loginOptionBtn.setTag(eLoginOption.SignIn);
        registerOptionBtn = view.findViewById(R.id.Register);
        registerOptionBtn.setTag(eLoginOption.SignUp);

        continueBtn = view.findViewById(R.id.Continue);

        emailField = view.findViewById(R.id.EmailText);
        passwordField = view.findViewById(R.id.PasswordText);
        userNameField = view.findViewById(R.id.UsernameText);

        currentLoginOption = eLoginOption.SignIn;

        initializeLoginButtons();

        UserList.initializeFakeUserList();
        ShoppingItemList.initializeFakeShoppingItemList();


        return view;
    }


    private void initializeLoginButtons() {
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(currentLoginOption)
                {
                    case SignIn:
                        loginUser();
                        break;
                    case SignUp:
                        registerUser();
                        break;
                }
            }
        });

        loginOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchForm(v);
            }
        });

        registerOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchForm(v);
            }
        });
    }




    private void loginUser()
    {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if(!LoginClientValidationUtils.validateLogin(email , password))
        {
            createCustomToast("email or password are invalid");
            return;
        }

        if(!UserList.isUserExist(email , password))
        {
            createCustomToast("email or password are invalid");
            return;
        }

        User loggedUser = UserList.getUserFromEmailAndPassword(email , password);

        Bundle bundle = new Bundle();
        bundle.putString(LOGGED_USER_EMAIL , loggedUser.getEmail());
        bundle.putString(LOGGED_USER_NAME , loggedUser.getUsername());
        bundle.putString(LOGGED_USER_PASSWORD , loggedUser.getPassword());

        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_menuFragment , bundle);
    }

    private void registerUser()
    {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        String username = userNameField.getText().toString();


        if(!LoginClientValidationUtils.isValidEmail(email))
        {
            createCustomToast("Email invalid in current format");
            return;
        }

        if(!LoginClientValidationUtils.isValidPassword(password))
        {
            createCustomToast("Password invalid in current format");
            return;
        }

        if(!LoginClientValidationUtils.isValidUsername(username))
        {
            createCustomToast("Username must be at least 3 letters");
            return;
        }

        if(UserList.isUserWithEmailExist(email))
        {
            createCustomToast("User already exist with your email");
            return;
        }

        User newUser = new User(username , email , password);

        try {
            UserList.addUser(newUser);

            Bundle bundle = new Bundle();
            bundle.putString(LOGGED_USER_EMAIL , email);
            bundle.putString(LOGGED_USER_NAME , username);
            bundle.putString(LOGGED_USER_PASSWORD , password);

            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_menuFragment , bundle);
        }
        catch(Exception e)
        {
            createCustomToast("Unable to add user due to outside error, try again");
        }
    }

    private void switchForm(View view)
    {
        Button clickedOptionBtn = (Button)view;


        switch((eLoginOption)clickedOptionBtn.getTag())
        {
            case SignIn:
                loginOptionBtn.setBackgroundResource(R.color.gray);
                registerOptionBtn.setBackgroundResource(R.color.black);
                userNameField.setVisibility(View.INVISIBLE);
                currentLoginOption = eLoginOption.SignIn;
                break;
            case SignUp:
                loginOptionBtn.setBackgroundResource(R.color.black);
                registerOptionBtn.setBackgroundResource(R.color.gray);
                userNameField.setVisibility(View.VISIBLE);
                currentLoginOption = eLoginOption.SignUp;
                break;
        }
    }

    private void createCustomToast(String message)
    {
        Toast.makeText(getContext() , message , Toast.LENGTH_LONG).show();
    }
}