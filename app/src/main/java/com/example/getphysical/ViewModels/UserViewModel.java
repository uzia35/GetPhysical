package com.example.getphysical.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Amplify;
import com.example.getphysical.Models.User;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<AuthSignUpResult> authSignUpResult = new MutableLiveData<>();
    private MutableLiveData<AuthSignInResult> authSignInResult = new MutableLiveData<>();

    public LiveData<User> getUser() {
        return user;
    }

    public void signUp(String username, String email, String password) {
        Amplify.Auth.signUp(
                username,
                password,
                AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), email).build(),
                result -> authSignUpResult.postValue(result),
                error -> Log.e("Error", error.toString()));
    }

    public void regularLogin(String username, String password) {
        Amplify.Auth.signIn(username, password,
                result -> authSignInResult.postValue(result),
                error -> Log.e("Error", error.toString()));
    }

    public void updateUser(String username, String email) {
        user.setValue(new User(username, email));
    }

    public void confirmSignUp(String username, String mfaCode) {
        Amplify.Auth.confirmSignUp(username, mfaCode,
                result ->authSignUpResult.postValue(result),
                error -> Log.e("Error", error.toString()));
    }
}
