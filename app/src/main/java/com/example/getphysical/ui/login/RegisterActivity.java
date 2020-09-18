package com.example.getphysical.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        EditText username = (EditText)findViewById(R.id.username);
        Amplify.Auth.signUp(
                username.getText().toString(),
                password.getText().toString(),
                AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), email.getText().toString()).build(),
                result -> {Log.i("AuthQuickStart", "Result: " + result.toString());
                           runOnUiThread(() -> confirmSignUp(username.getText().toString()));
                          },
                error -> Log.e("AuthQuickStart", "Sign up failed", error)
        );
    }

    private void confirmSignUp(String username) {
        EditText confirmationCode = (EditText)findViewById(R.id.confirmation_code);
        Button sign_up_button = (Button)findViewById(R.id.universal_sign_in_button);
        sign_up_button.setVisibility(View.VISIBLE);
        confirmationCode.setVisibility(View.VISIBLE);
        Amplify.Auth.confirmSignUp(
                username,
                confirmationCode.getText().toString(),
                result -> Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
                error -> Log.e("AuthQuickstart", error.toString())
        );
    }

}