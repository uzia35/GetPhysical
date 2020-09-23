package com.example.getphysical;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.amazonaws.Response;
import com.example.getphysical.Models.User;
import com.example.getphysical.ViewModels.UserViewModel;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {

    private UserViewModel userViewModel;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private EditText username;
    private EditText password;
    private EditText email;

    private View.OnClickListener signUpListener = v -> {
        signUp(username.getText().toString(), email.getText().toString(), password.getText().toString());
    };

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        view.findViewById(R.id.sign_up_button).setOnClickListener(signUpListener);
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        email = (EditText) view.findViewById(R.id.email);
    }

    private void signUp(String username, String email, String password) {
        userViewModel.signUp(username, email, password).observe(getViewLifecycleOwner(), (Observer< Response>) response -> {
            if (response.getHttpResponse() != null) {
                navController.navigate(R.id.action_registrationFragment_to_confirmationFragment);
            } else {
                showErrorMessage();
            }
        });
    }

    private void showErrorMessage() {
        // Display a snackbar error message
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}