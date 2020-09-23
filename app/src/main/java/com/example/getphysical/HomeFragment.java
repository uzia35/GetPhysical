package com.example.getphysical;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.getphysical.Models.User;
import com.example.getphysical.ViewModels.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private UserViewModel userViewModel;
    private NavController navController;
    boolean isAuthenticated;
    private NavHostFragment navHostFragment;

    @SuppressLint("ResourceType")
    private OnClickListener viewProfileListener = v -> {
        Log.w("INFO", "calling signing in with email/pass");
            if (isAuthenticated) {
                navController.navigate(R.id.action_homeFragment_to_profileFragment);
            } else {
                Log.w("INFO", "logging in");
                navController.navigate(R.id.action_homeFragment_to_loginFragment);
            }
    };

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        view.findViewById(R.id.view_profile_button).setOnClickListener(viewProfileListener);
        userViewModel.getUser().observe(getViewLifecycleOwner(), (Observer<User>) user -> {
            isAuthenticated = user.getUsername() != null;
        });
    }

}