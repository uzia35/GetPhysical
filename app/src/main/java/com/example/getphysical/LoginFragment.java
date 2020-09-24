package com.example.getphysical;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.getphysical.ViewModels.UserViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginFragment extends Fragment {

    private UserViewModel userViewModel;
    private GoogleSignInClient mGoogleSignInClient;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private EditText username;
    private EditText password;
    private int GOOGLE_SIGN_IN_REQUEST_ID = 1;

    private OnClickListener regularLoginListener = v -> {
        regularLogin(username.getText().toString(), password.getText().toString());
    };

    private OnClickListener googleLoginListener = v -> {
        signInWithGoogle();
    };

    private OnClickListener registerListener = v -> {
        navController.navigate(R.id.action_loginFragment_to_registrationFragment);
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                     .requestEmail()
                                     .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }

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
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
    }

    private void regularLogin(String username, String password) {
        userViewModel.regularLogin(username, password).observe(getViewLifecycleOwner(), authSignInResult -> {
            if (authSignInResult.isSignInComplete()) {
                NavHostFragment.findNavController(this).popBackStack();
            } else {
                showErrorMessage();
            }
        });
    }

    private void signInWithGoogle() {
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), GOOGLE_SIGN_IN_REQUEST_ID);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN_REQUEST_ID) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.w("INFO", "signInResult:success code= " + account.getGivenName());
                userViewModel.updateUser(account.getGivenName(), account.getEmail());
                NavHostFragment.findNavController(this).popBackStack();
            } catch (ApiException e) {
                Log.w("INFO", "signInResult:failed code= " + e);
                //updateUI(null);
            }
        }
    }

    private void showErrorMessage() {
        // Display a snackbar error message
    }
}