package com.spectraapps.myspare.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.network.MyRetrofitClient;
import com.spectraapps.myspare.model.RegisterModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText mNameET, mMobileET, mPasswordET;
    AutoCompleteTextView mEmailET;
    Button mSignUpButton;
    ProgressDialog progressDialog;

    TextInputLayout emailTextInputLayout,passTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initUI();
        initButtonClickListeners();
    }

    private void initButtonClickListeners() {
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                attemmptSignUp();
            }
        });
    }

    private void attemmptSignUp() {
        if (isEmailValid(mEmailET.getText().toString()) && isPasswordValid(mPasswordET.getText().toString())) {
            serverRegister();
        }
    }

    private void initUI() {
        mNameET = findViewById(R.id.reg_nameET);
        mMobileET = findViewById(R.id.reg_phoneET);
        mPasswordET = findViewById(R.id.reg_passwordET);
        mEmailET = findViewById(R.id.reg_emailET);
        mSignUpButton = findViewById(R.id.button_register);

        emailTextInputLayout = findViewById(R.id.reg_emailTextInput);
        passTextInputLayout = findViewById(R.id.reg_passTextInput);

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void serverRegister() {
        String name = mNameET.getText().toString();
        String mail = mEmailET.getText().toString();
        String mobile = mMobileET.getText().toString();
        String password = mPasswordET.getText().toString();

        Api retrofit = MyRetrofitClient.getBase().create(Api.class);
        Call<RegisterModel> registerCall = retrofit.register(name, mail, mobile, password, "123");
        registerCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "" + response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    String id = response.body().getData().getId();
                    String name = response.body().getData().getName();
                    String email = response.body().getData().getMail();
                    String token = response.body().getData().getToken();
                    String mobile = response.body().getData().getMobile();

                    saveUserInfo(id, email, name, mobile, token);
                    startActivity(intent);
                    progressDialog.dismiss();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "" + response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }//end serverRegister()

    private void saveUserInfo(String id, String name, String email, String token, String mobile) {
        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        prefEditor.putString("id", id);
        prefEditor.putString("name", name);
        prefEditor.putString("email", email);
        prefEditor.putString("token", token);
        prefEditor.putString("mobile", mobile);
        prefEditor.putBoolean("isLoggedIn",true);

        prefEditor.apply();
    }

    private boolean isEmailValid(String email) {
        if (email.contains("@"))
            return true;
        else {
            mEmailET.setError(getString(R.string.error_invalid_email));
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .repeat(1)
                    .playOn(emailTextInputLayout);
            return false;
        }
    }//end isEmailValid()

    private boolean isPasswordValid(String password) {
        if (password.length() > 2 || password.length() == 0)
            return true;
        else {
            mPasswordET.setError(getString(R.string.error_invalid_password));
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .repeat(1)
                    .playOn(passTextInputLayout);
            return false;
        }
    }//end isPasswordValid()

}//end RegisterActivity()