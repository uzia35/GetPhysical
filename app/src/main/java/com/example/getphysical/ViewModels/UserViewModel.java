package com.example.getphysical.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amazonaws.Response;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.example.getphysical.Models.User;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<Response> response = new MutableLiveData<>();

    public LiveData<User> getUser() {
        return user;
    }

    public LiveData<Response> getResponse(){
        return response;
    }

    public LiveData<Response> signUp(String username, String email, String password) {
        Amplify.Auth.signUp(
                username,
                password,
                AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), email).build(),
                result -> {
                    Log.i("AuthQuickStart", "Result: " + result.toString());
                    this.response = result;
                },
                error -> Log.e("AuthQuickStart", "Sign up failed", error)
        );
        return response;
    }

    public LiveData<User> regularLogin(String username, String password) {
        return user;
    }

    public LiveData<User> googleLogin(String username, String password) {
        return user ;
    }
}
