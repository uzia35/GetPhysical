package com.example.getphysical.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.example.getphysical.R;

public class RegisterActivity extends AppCompatActivity {

    private OnClickListener signUpListener = v -> {
        Log.w("INFO", "calling signing up with email/pass");
        signUpWithEmailAndPass();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById(R.id.sign_up_button).setOnClickListener(signUpListener);
    }

    private void signUpWithEmailAndPass() {
        EditText email = (EditText)findViewById(R.id.email);
        EditText password = (EditText)findViewById(R.id.password);
        Amplify.Auth.signUp(
                email.getText().toString(),
                password.getText().toString(),
                AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), "my@email.com").build(),
                result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
                error -> Log.e("AuthQuickStart", "Sign up failed", error)
        );
    }

    private void confirmSignUp() {
        Amplify.Auth.confirmSignUp(
                "username",
                "the code you received via email",
                result -> Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
                error -> Log.e("AuthQuickstart", error.toString())
        );
    }

}