package com.example.getphysical.ViewModels;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amazonaws.Response;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Amplify;
import com.example.getphysical.Models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<AuthSignUpResult> authSignUpResult = new MutableLiveData<>();
    private MutableLiveData<AuthSignInResult> authSignInResult = new MutableLiveData<>();

    public LiveData<User> getUser() {
        return user;
    }

    public LiveData<AuthSignUpResult> signUp(String username, String email, String password) {
        Amplify.Auth.signUp(
                username,
                password,
                AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), email).build(),
                result -> {
                    Log.i("AuthQuickStart", "Result: " + result.toString());
                    authSignUpResult = new MutableLiveData<>(result);
                },
                error -> Log.e("AuthQuickStart", "Sign up failed", error)
        );
        return authSignUpResult;
    }

    public LiveData<AuthSignInResult> regularLogin(String username, String password) {
        Amplify.Auth.signIn(username, password,
                result -> { Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");
                            authSignInResult = new MutableLiveData<>(result);
                          },
                error -> Log.e("AuthQuickstart", error.toString()));
        return authSignInResult;
    }

}
