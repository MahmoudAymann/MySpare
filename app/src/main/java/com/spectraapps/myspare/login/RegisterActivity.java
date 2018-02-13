package com.spectraapps.myspare.login;

import android.content.Intent;
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
                    intent.putExtra("reg_name", response.body().getData().getName());
                    intent.putExtra("reg_mail", response.body().getData().getMobile());
                    intent.putExtra("reg_mobile", response.body().getData().getMobile());
                    intent.putExtra("reg_token", response.body().getData().getToken());
                    startActivity(intent);
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
    }


}
