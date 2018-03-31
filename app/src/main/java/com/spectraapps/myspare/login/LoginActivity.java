package com.spectraapps.myspare.login;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

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
    AlertDialog.Builder alertDialogBuilder;
    ImageView imageView;
    ScrollView mainLayout;
    boolean inputTypeChanged;
    String langStr;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setSharedPreference = new ListSharedPreference.Set(LoginActivity.this.getApplicationContext());
        getSharedPreference = new ListSharedPreference.Get(LoginActivity.this.getApplicationContext());
        langStr = getSharedPreference.getLanguage();
        initUI();
        setLAyoutLanguage();

        initClickListener();
        setAlertDialog();

        mIsLogged = getSharedPreference.getLoginStatus();

    }//end onCreate()

    private void setAlertDialog() {
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setNegativeButton(getString(R.string.done), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    }//end setAlertDialog

    private void setLAyoutLanguage() {
        if (langStr.equals("en")) {
            locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            this.getApplicationContext().getResources().updateConfiguration(config, null);
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            this.setContentView(R.layout.activity_login);
            initUI();
        } else {
            locale = new Locale("ar");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            this.getApplicationContext().getResources().updateConfiguration(config, null);
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            this.setContentView(R.layout.activity_login);
            initUI();
        }
    }

    private void serverLogin() {

        Api retrofit = MyRetrofitClient.getBase().create(Api.class);
        Call<LoginModel> call = retrofit.login(mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString(), "123");

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {

                try {

                    if (response.isSuccessful()) {

                        if (response.body().getData() != null) {
                            String id = response.body().getData().getId();
                            String name = response.body().getData().getName();
                            String email = response.body().getData().getMail();
                            String token = response.body().getData().getToken();
                            String mobile = response.body().getData().getMobile();
                            String image = response.body().getData().getImage();

                            setSharedPreference.setLoginStatus(true);
                            Toast.makeText(LoginActivity.this, getString(R.string.logged_success), Toast.LENGTH_LONG).show();

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
                } catch (Exception e) {
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialogBuilder.setMessage("Error: " + e);
                    alertDialog.show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                try {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "exc:" + t.getMessage(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
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
        mainLayout = findViewById(R.id.login_resource);
        mainLayout.setBackgroundResource(R.drawable.app_background);
        mEmailEditText = findViewById(R.id.emailET);
        mPasswordEditText = findViewById(R.id.passwordET);
        mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isPasswordShown) {
                    if (getSharedPreference.getLanguage().equals("ar")) {
                        if (s.length() > 0) {
                            if (!inputTypeChanged) {

                                // When a character is typed, dynamically change the EditText's
                                // InputType to PASSWORD, to show the dots and conceal the typed characters.
                                mPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT |
                                        InputType.TYPE_TEXT_VARIATION_PASSWORD);

                                // Move the cursor to the correct place (after the typed character)
                                mPasswordEditText.setSelection(s.length());

                                inputTypeChanged = true;
                            }
                        } else {
                            // Reset EditText: Make the "Enter password" hint display on the right
                            mPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

                            inputTypeChanged = false;
                        }
                    }
                }
            }
        });

        mSignInButton = findViewById(R.id.email_sign_in_button);
        mSignInButton.setText(R.string.action_sign_in);

        mRegisterButton = findViewById(R.id.button_Register);
        mSkipButton = findViewById(R.id.button_later);
        mPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
                    .playOn(mPasswordEditText);
            return false;
        }
    }//end isPasswordValid()

}//end class LoginActivity()