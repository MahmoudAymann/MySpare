package com.spectraapps.myspare.login;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.http.Api;
import com.spectraapps.myspare.http.MyRetrofitClient;
import com.spectraapps.myspare.model.LoginModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity
{
    //UI references.
    private AutoCompleteTextView mEmailEditText;
    private EditText mPasswordEditText;
    TextInputLayout textInputLayoutEmail, textInputLayoutPassword;
    EditText mail,pass;

    Button mSignInButton, mRegisterButton, mSkipButton;
    LoginModel loginModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
        initButtonListener();



    }//end onCreate()

    private void serverLogin() {

        Api retrofit= MyRetrofitClient.getBase().create(Api.class);

        Call<LoginModel> loginCall = retrofit.login(
                mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString());

        loginCall.enqueue(new Callback<LoginModel>()
        {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response)
            {
                if (response.isSuccessful()){
                    Toast.makeText(LoginActivity.this,""+response.body().getStatus().getTitle()+" ",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(LoginActivity.this,""+response.body().getStatus().getTitle()+" ",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t)
            {
                Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }

    private void initUI() {
        textInputLayoutEmail = findViewById(R.id.textinput_email);
        textInputLayoutPassword = findViewById(R.id.textinput_pass);

        mEmailEditText = findViewById(R.id.emailET);
        mPasswordEditText = findViewById(R.id.passwordET);

        mSignInButton = findViewById(R.id.email_sign_in_button);
        mRegisterButton = findViewById(R.id.button_Register);
        mSkipButton = findViewById(R.id.button_later);

    }//end initUI()

    private void initButtonListener() {
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
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
                finish();
            }
        });
    }//initButtonListener()

    private void attemptLogin() {
        //Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
        if ( isEmailValid(mEmailEditText.getText().toString()) && isPasswordValid(mPasswordEditText.getText().toString())) {
            serverLogin();
        }
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
    }//end isEmailValid()

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
    }//end isPasswordValid()

}//end class LoginActivity()