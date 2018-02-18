package com.spectraapps.myspare.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
        initClickListener();

    }//end onCreate()

    private void serverLogin() {

        Api retrofit = MyRetrofitClient.getBase().create(Api.class);
        Call<LoginModel> call = retrofit.login(mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString(), "123");

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(LoginActivity.this, "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                    String id = response.body().getData().getId();
                    String name = response.body().getData().getName();
                    String email = response.body().getData().getMail();
                    String token = response.body().getData().getToken();
                    String mobile = response.body().getData().getMobile();

                    saveUserInfo(id, email, name, mobile, token);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(LoginActivity.this, "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveUserInfo(String id, String name, String email, String token, String mobile) {
        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        prefEditor.putString("id", id);
        prefEditor.putString("name", name);
        prefEditor.putString("email", email);
        prefEditor.putString("token", token);
        prefEditor.putString("mobile", mobile);
        prefEditor.putBoolean("isLoggedIn", true);
        prefEditor.apply();
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

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(false);
    }//end initUI()


    private void initClickListener() {
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                attemptLogin();
            }
        });

        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                progressDialog.dismiss();
            }
        });

        mSkipButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                progressDialog.dismiss();
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