package com.example.getphysical;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class LoginFragment extends Fragment {
    public static String LOGIN_SUCCESSFUL = "LOGIN_SUCCESSFUL";

    private UserViewModel userViewModel;
    private SavedStateHandle savedStateHandle;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        savedStateHandle = Navigation.findNavController(view)
                .getPreviousBackStackEntry()
                .getSavedStateHandle();
        savedStateHandle.set(LOGIN_SUCCESSFUL, false);

        EditText usernameEditText = view.findViewById(R.id.username);
        EditText passwordEditText = view.findViewById(R.id.password);
        Button loginButton = view.findViewById(R.id.universal_sign_in_button);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            login(username, password);
        });
    }

    private void login(String username, String password) {
        userViewModel.login(username, password).observe(getViewLifecycleOwner(), (Observer<User>) user -> {
            if (user.getUsername() != null) {
                savedStateHandle.set(LOGIN_SUCCESSFUL, true);
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