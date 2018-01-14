package com.spectraapps.myspare.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.spectraapps.myspare.R;

public class RegisterActivity extends AppCompatActivity {

    EditText mNameET,mMobileET,mPasswordET;
    AutoCompleteTextView mEmailET;
    Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNameET = findViewById(R.id.reg_nameET);
        mMobileET = findViewById(R.id.reg_phoneET);
        mPasswordET = findViewById(R.id.reg_passwordET);
        mEmailET = findViewById(R.id.reg_emailET);

        mSignUpButton = findViewById(R.id.button_register);

    }
}
