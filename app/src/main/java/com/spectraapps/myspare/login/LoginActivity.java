package com.spectraapps.myspare.login;


import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;

public class LoginActivity extends AppCompatActivity {

    //UI references.
    private AutoCompleteTextView mEmailEditText;
    private EditText mPasswordEditText;
    TextInputLayout textInputLayoutEmail, textInputLayoutPassword;

    Button mSignInButton, mRegisterButton, mSkipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputLayoutEmail = findViewById(R.id.textinput_email);
        textInputLayoutPassword = findViewById(R.id.textinput_pass);

        mEmailEditText = findViewById(R.id.emailET);
        mPasswordEditText = findViewById(R.id.passwordET);

        mSignInButton = findViewById(R.id.email_sign_in_button);
        mRegisterButton = findViewById(R.id.button_Register);
        mSkipButton = findViewById(R.id.button_later);


        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( isEmailValid(mEmailEditText.getText().toString()) && isPasswordValid(mPasswordEditText.getText().toString())) {
                     attemptLogin();
                }
            }
        });

        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });

        mSkipButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

    }

    private void attemptLogin() {
        Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
    }

    private boolean isEmailValid(String email) {
        if ( email.contains("@") )
            return true;
        else {
            mEmailEditText.setError(getString(R.string.error_invalid_email));
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .repeat(1)
                    .playOn(textInputLayoutEmail);
            return false;
        }
    }

    private boolean isPasswordValid(String password) {
       if (password.length() > 4 || password.length() == 0)
           return true;
       else {
           mPasswordEditText.setError(getString(R.string.error_invalid_password));
           YoYo.with(Techniques.Shake)
                   .duration(700)
                   .repeat(1)
                   .playOn(textInputLayoutPassword);
           return false;
       }
    }
}

