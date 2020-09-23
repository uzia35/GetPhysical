package com.example.getphysical;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.getphysical.Models.User;
import com.example.getphysical.ViewModels.UserViewModel;
import com.example.getphysical.temp.RegisterActivity;

public class LoginFragment extends Fragment {

    private UserViewModel userViewModel;
    private SavedStateHandle savedStateHandle;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private EditText username;
    private EditText password;

    private OnClickListener regularLoginListener = v -> {
        regularLogin(username.getText().toString(), password.getText().toString());
    };

    private OnClickListener googleLoginListener = v -> {
        googleLogin(username.getText().toString(), password.getText().toString());
    };

    private OnClickListener registerListener = v -> {
        navController.navigate(R.id.action_loginFragment_to_registrationFragment);
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        view.findViewById(R.id.universal_sign_in_button).setOnClickListener(regularLoginListener);
        view.findViewById(R.id.google_sign_in_button).setOnClickListener(googleLoginListener);;
        view.findViewById(R.id.register).setOnClickListener(registerListener);;
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
    }

    private void regularLogin(String username, String password) {
        userViewModel.regularLogin(username, password).observe(getViewLifecycleOwner(), (Observer<User>) user -> {
            if (user.getUsername() != null) {
                NavHostFragment.findNavController(this).popBackStack();
            } else {
                showErrorMessage();
            }
        });
    }

    private void googleLogin(String username, String password) {
        userViewModel.googleLogin(username, password).observe(getViewLifecycleOwner(), (Observer<User>) user -> {
            if (user.getUsername() != null) {
                NavHostFragment.findNavController(this).popBackStack();
            } else {
                showErrorMessage();
            }
        });
    }

    private void showErrorMessage() {
        // Display a snackbar error message
    }
}