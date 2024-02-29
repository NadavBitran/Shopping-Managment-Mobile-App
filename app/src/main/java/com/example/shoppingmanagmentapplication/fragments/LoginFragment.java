package com.example.shoppingmanagmentapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.shoppingmanagmentapplication.R;
import com.example.shoppingmanagmentapplication.firebase.UsersService;
import com.example.shoppingmanagmentapplication.model.ActiveUser;
import com.example.shoppingmanagmentapplication.model.User;
import com.example.shoppingmanagmentapplication.utils.LoginClientValidationUtils;
import com.example.shoppingmanagmentapplication.utils.ToastUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginFragment extends Fragment {

    private Button loginOptionBtn;
    private Button registerOptionBtn;
    private Button continueBtn;
    private TextView emailField;
    private TextView passwordField;
    private TextView userNameField;
    private eLoginOption currentLoginOption;

    public LoginFragment() {

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

        initializeUIReferencesAndValues(view);
        initializeButtonsOnClickEvent();

        return view;
    }

    private void initializeUIReferencesAndValues(View view)
    {
        loginOptionBtn = view.findViewById(R.id.Login);
        registerOptionBtn = view.findViewById(R.id.Register);
        registerOptionBtn.setTag(eLoginOption.SignUp);
        continueBtn = view.findViewById(R.id.Continue);
        emailField = view.findViewById(R.id.EmailText);
        passwordField = view.findViewById(R.id.PasswordText);
        userNameField = view.findViewById(R.id.UsernameText);

        currentLoginOption = eLoginOption.SignIn;
        loginOptionBtn.setTag(eLoginOption.SignIn);
    }
    private void initializeButtonsOnClickEvent() {
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

        if(LoginClientValidationUtils.validateLogin(email, password, getContext()))
        {
            UsersService.trySignIn(
                    email,
                    password,
                    () -> Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_menuFragment),
                    (e) -> ToastUtils.createCustomToast("an error happened trying to sign-up, try again", getContext()));
        }
    }

    private void registerUser()
    {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        String username = userNameField.getText().toString();

        if(LoginClientValidationUtils.validateRegister(email, password, username, getContext()))
        {
            User currentUser = new User(username, email);
            UsersService.trySignUp(
                    currentUser,
                    password,
                    () -> Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_menuFragment),
                    (e) -> ToastUtils.createCustomToast("an error happened trying to sign-up, try again", getContext()));
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
}