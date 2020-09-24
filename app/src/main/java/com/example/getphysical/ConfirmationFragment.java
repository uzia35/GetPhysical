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

import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Amplify;
import com.example.getphysical.ViewModels.UserViewModel;

import static com.amplifyframework.auth.result.step.AuthSignInStep.CONFIRM_SIGN_UP;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ConfirmationFragment extends Fragment {

    private UserViewModel userViewModel;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private EditText mfaCode;
    private String username;

    private View.OnClickListener confirmationListener = v -> {
        userViewModel.confirmSignUp(username, mfaCode.getText().toString());
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_confirmation, container, false);
        username = getArguments().getString("username");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        mfaCode = (EditText) view.findViewById(R.id.confirmation_code);
        view.findViewById(R.id.confirmation_button).setOnClickListener(confirmationListener);
        userViewModel.getAuthSignUpResult().observe(getViewLifecycleOwner(), (Observer<AuthSignUpResult>) authSignUpResult -> {
            if (authSignUpResult.isSignUpComplete() && authSignUpResult.getNextStep().equals(CONFIRM_SIGN_UP)) {
                NavHostFragment.findNavController(this).popBackStack(R.id.homeFragment,false);
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