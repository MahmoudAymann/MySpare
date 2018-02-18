package com.spectraapps.myspare.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        serverRegister();
    }

    private void initUI() {
        mNameET = findViewById(R.id.reg_nameET);
        mMobileET = findViewById(R.id.reg_phoneET);
        mPasswordET = findViewById(R.id.reg_passwordET);
        mEmailET = findViewById(R.id.reg_emailET);
        mSignUpButton = findViewById(R.id.button_register);

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
        Call<RegisterModel> registerCall = retrofit.register(name, mail, mobile, password, "token_empty");
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
                } else {
                    Toast.makeText(RegisterActivity.this, "" + response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "FailResponse", Toast.LENGTH_SHORT).show();
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

}
