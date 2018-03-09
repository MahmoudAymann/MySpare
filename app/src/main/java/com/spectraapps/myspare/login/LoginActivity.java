package com.spectraapps.myspare.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import com.github.kimkevin.cachepot.CachePot;
import com.spectraapps.myspare.ListSharedPreference;
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
    ListSharedPreference listSharedPreference = new ListSharedPreference();
    Locale locale;
    boolean mIsLogged;
    private AutoCompleteTextView mEmailEditText;
    private EditText mPasswordEditText;
    private ProgressDialog progressDialog;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setLAyoutLanguage();

        initUI();
        initClickListener();

        if (CachePot.getInstance().pop("islogged") != null)
            mIsLogged = CachePot.getInstance().pop("islogged");

    }//end onCreate()

    private void setLAyoutLanguage() {
        String langStr = listSharedPreference.getLanguage(getApplicationContext());
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

                if (response.isSuccessful()) {

                    Toast.makeText(LoginActivity.this, "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                    if (response.body().getData() != null) {
                        String id = response.body().getData().getId();
                        String name = response.body().getData().getName();
                        String email = response.body().getData().getMail();
                        String token = response.body().getData().getToken();
                        String mobile = response.body().getData().getMobile();
                        String image = response.body().getData().getImage();

                        CachePot.getInstance().push("isloggedy", true);

                        saveUserInfo(id, name, email, mobile, token, image);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("login", 1);
                        intent.putExtra("lok", true);
                        intent.putExtra("uid", id);
                        intent.putExtra("uname", name);
                        intent.putExtra("umail", email);
                        intent.putExtra("utoken", token);
                        intent.putExtra("umobile", mobile);
                        intent.putExtra("uimage", image);
                        startActivity(intent);
                        finish();
                    }

                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "error:" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "exc:" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveUserInfo(String id, String name, String email, String token, String mobile, String image) {
        listSharedPreference.setUId(getApplicationContext(), id);
        listSharedPreference.setUName(getApplicationContext(), name);
        listSharedPreference.setEmail(getApplicationContext(), email);
        listSharedPreference.setToken(getApplicationContext(), token);
        listSharedPreference.setMobile(getApplicationContext(), mobile);
        listSharedPreference.setImage(getApplicationContext(), image);
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
                intent.putExtra("login", 2);
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
                intent.putExtra("login", 3);
                intent.putExtra("lok", false);
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