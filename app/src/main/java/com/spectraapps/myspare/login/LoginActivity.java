package com.spectraapps.myspare.login;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import com.github.kimkevin.cachepot.CachePot;
import com.spectraapps.myspare.SplashScreen;
import com.spectraapps.myspare.utility.ListSharedPreference;
import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.navdrawer.ResetPassword;
import com.spectraapps.myspare.network.MyRetrofitClient;
import com.spectraapps.myspare.model.LoginModel;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button mSignInButton, mRegisterButton, mSkipButton;
    boolean isPasswordShown;
    ImageButton mImagePasswrdVisible;
    TextView textViewForgetPassword;
    ListSharedPreference.Set setSharedPreference;
    ListSharedPreference.Get getSharedPreference;
    Locale locale;
    boolean mIsLogged;
    private AutoCompleteTextView mEmailEditText;
    private EditText mPasswordEditText;
    private ProgressDialog progressDialog;

    AlertDialog.Builder alertDialogBuilder;


    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setSharedPreference = new ListSharedPreference.Set(LoginActivity.this.getApplicationContext());
        getSharedPreference = new ListSharedPreference.Get(LoginActivity.this.getApplicationContext());

        setLAyoutLanguage();

        initUI();
        initClickListener();
        setAlertDialog();

        mIsLogged = getSharedPreference.getLoginStatus();

    }//end onCreate()

    private void setAlertDialog() {
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setNegativeButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    }//end setAlertDialog

    private void setLAyoutLanguage() {
        String langStr = getSharedPreference.getLanguage();
        if (langStr.equals("en")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            this.getApplicationContext().getResources().updateConfiguration(config, null);
        } else if (langStr.equals("ar")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            locale = new Locale("ar");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            this.getApplicationContext().getResources().updateConfiguration(config, null);
        }
    }

    private void serverLogin() {

        Api retrofit = MyRetrofitClient.getBase().create(Api.class);
        Call<LoginModel> call = retrofit.login(mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString(), "123");

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                try {

                    if (response.isSuccessful()) {

                        Toast.makeText(LoginActivity.this, "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                        if (response.body().getData() != null) {
                            String id = response.body().getData().getId();
                            String name = response.body().getData().getName();
                            String email = response.body().getData().getMail();
                            String token = response.body().getData().getToken();
                            String mobile = response.body().getData().getMobile();
                            String image = response.body().getData().getImage();

                            setSharedPreference.setLoginStatus(true);

                            saveUserInfo(id, name, email, mobile, token, image);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                            finish();
                        }

                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "error:" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialogBuilder.setMessage("Error: " + e);
                    alertDialog.show();
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                try {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "exc:" + t.getMessage(), Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialogBuilder.setMessage("Error: " + e);
                    alertDialog.show();
                }
            }
        });
    }

    private void saveUserInfo(String id, String name, String email, String token, String mobile, String image) {
        setSharedPreference.setUId(id);
        setSharedPreference.setUName(name);
        setSharedPreference.setEmail(email);
        setSharedPreference.setToken(token);
        setSharedPreference.setMobile(mobile);
        setSharedPreference.setImage(image);
    }

    private void initUI() {
        imageView = findViewById(R.id.loginImageView);
        imageView.setBackgroundResource(R.drawable.app_logo);

        mEmailEditText = findViewById(R.id.emailET);
        mPasswordEditText = findViewById(R.id.passwordET);

        mSignInButton = findViewById(R.id.email_sign_in_button);
        mSignInButton.setText(R.string.action_sign_in);

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
                attemptLogin();
            }
        });

        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                progressDialog.dismiss();
            }
        });

        mSkipButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view)

            {

                progressDialog.show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
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
        if (isEmailValid(mEmailEditText.getText().toString()) &&
                isPasswordValid(mPasswordEditText.getText().toString())) {
            progressDialog.show();
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
                    .playOn(mEmailEditText);
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
                    .playOn(mPasswordEditText);
            return false;
        }
    }//end isPasswordValid()

}//end class LoginActivity()