package com.spectraapps.myspare.login;


import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.model.Data;
import com.spectraapps.myspare.navdrawer.ResetPassword;
import com.spectraapps.myspare.network.MyRetrofitClient;
import com.spectraapps.myspare.model.LoginModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    //UI references.
    private AutoCompleteTextView mEmailEditText;
    private EditText mPasswordEditText;
    TextInputLayout textInputLayoutEmail, textInputLayoutPassword;

    Button mSignInButton, mRegisterButton, mSkipButton;
    boolean isPasswordShown;
    ImageButton mImagePasswrdVisible;
    TextView textViewForgetPassword;
    login loginModel = new login();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
        initClickListener();

    }//end onCreate()

    private void serverLogin() {

        Api retrofit = MyRetrofitClient.getBase().create(Api.class);
        Call<login> call = retrofit.login(mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString(), "123");

        call.enqueue(new Callback<login>() {
            @Override
            public void onResponse(Call<login> call, Response<login> response) {
                if (response.body().getStatus().getTitle().equals("success")) {
                    Toast.makeText(LoginActivity.this, "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                    loginModel.setData(response.body().getData());
                    loginModel.setStatus(response.body().getStatus());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("LoginModel",loginModel);
                    startActivity(intent);

                }else {
                    Toast.makeText(LoginActivity.this, "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<login> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

//        call.enqueue(new Callback<Data>() {
//            @Override
//            public void onResponse(Call<Data> call, Response<Data> response) {
//            }
//        });
//
////
////        Call<LoginModel> loginCall = retrofit.login(
////                mEmailEditText.getText().toString(),
////                mPasswordEditText.getText().toString(),"123");
//
//        loginCall.enqueue(new Callback<LoginModel>() {
//            @Override
//            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(LoginActivity.this, "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
//                    loginModel.setData(response.body().getData());
//                    loginModel.setStatus(response.body().getStatus());
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//
//                } else {
//                    Toast.makeText(LoginActivity.this, "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginModel> call, Throwable t) {
//                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//
//            }
//        });
    }

    private void initUI() {
        textInputLayoutEmail = findViewById(R.id.textinput_email);
        textInputLayoutPassword = findViewById(R.id.textinput_pass);

        mEmailEditText = findViewById(R.id.emailET);
        mPasswordEditText = findViewById(R.id.passwordET);

        mSignInButton = findViewById(R.id.email_sign_in_button);
        mRegisterButton = findViewById(R.id.button_Register);
        mSkipButton = findViewById(R.id.button_later);

        mImagePasswrdVisible = findViewById(R.id.image_visible);

        textViewForgetPassword = findViewById(R.id.forgot_password_text);


    }//end initUI()


    private void initClickListener() {
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

        mImagePasswrdVisible.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordShown) {
                    mImagePasswrdVisible.setImageResource(R.drawable.ic_visibility_white_24dp);
                    mPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isPasswordShown = false;
                } else {
                    mImagePasswrdVisible.setImageResource(R.drawable.ic_visibility_off_white_24dp);
                    mPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                    isPasswordShown = true;
                }
            }
        });

        textViewForgetPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ResetPassword.class);
                startActivity(intent);
            }
        });

    }//initClickListener()

    private void attemptLogin() {
        if (isEmailValid(mEmailEditText.getText().toString()) && isPasswordValid(mPasswordEditText.getText().toString())) {
            serverLogin();
        }
    }

    private boolean isEmailValid(String email) {
        if (email.contains("@"))
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
        if (password.length() > 2 || password.length() == 0)
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