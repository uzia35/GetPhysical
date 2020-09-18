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

    private EditText email = (EditText)findViewById(R.id.email);
    private EditText password = (EditText)findViewById(R.id.password);
    private EditText username = (EditText)findViewById(R.id.username);

    private OnClickListener signUpListener = v -> {
        Log.w("INFO", "calling signing up with email/pass");
        signUpWithEmailAndPass();
    };

    private OnClickListener signInListener = v -> {
        Log.w("INFO", "confirming sign up");
        EditText confirmationCode = (EditText)findViewById(R.id.confirmation_code);
        Amplify.Auth.confirmSignUp(
                username.getText().toString(),
                confirmationCode.getText().toString(),
                result -> Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
                error -> Log.e("AuthQuickstart", error.toString())
        );
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById(R.id.sign_up_button).setOnClickListener(signUpListener);
        findViewById(R.id.universal_sign_in_button).setOnClickListener(signInListener);
    }

    private void signUpWithEmailAndPass() {
        Amplify.Auth.signUp(
                username.getText().toString(),
                password.getText().toString(),
                AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), email.getText().toString()).build(),
                result -> {Log.i("AuthQuickStart", "Result: " + result.toString());
                           runOnUiThread(() -> openConfirmationUI());
                          },
                error -> Log.e("AuthQuickStart", "Sign up failed", error)
        );
    }

    private void openConfirmationUI() {
        Button sign_up_button = findViewById(R.id.sign_up_button);
        EditText confirmationCode = findViewById(R.id.confirmation_code);
        sign_up_button.setVisibility(View.VISIBLE);
        confirmationCode.setVisibility(View.VISIBLE);
    }

}