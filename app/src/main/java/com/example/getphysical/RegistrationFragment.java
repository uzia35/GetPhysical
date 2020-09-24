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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.amazonaws.Response;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.example.getphysical.Models.User;
import com.example.getphysical.ViewModels.UserViewModel;

import static com.amplifyframework.auth.result.step.AuthSignUpStep.CONFIRM_SIGN_UP_STEP;


/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {

    private UserViewModel userViewModel;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private EditText username;
    private EditText password;
    private EditText email;

    private OnClickListener signUpListener = v -> {
        userViewModel.signUp(username.getText().toString(), email.getText().toString(), password.getText().toString());
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

        userViewModel.getAuthSignUpResult().observe(getViewLifecycleOwner(), (Observer<AuthSignUpResult>) authSignUpResult -> {
            if (authSignUpResult.isSignUpComplete()) {
                Bundle args = new Bundle();
                args.putString("username", username.getText().toString());
                navController.navigate(R.id.action_registrationFragment_to_confirmationFragment,args);
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