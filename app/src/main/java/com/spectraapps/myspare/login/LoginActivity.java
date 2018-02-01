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
import com.spectraapps.myspare.helper.Api;
import com.spectraapps.myspare.model.Login;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.spectraapps.myspare.helper.Api.BASE_URL;

public class LoginActivity extends AppCompatActivity
{
    //UI references.
    private AutoCompleteTextView mEmailEditText;
    private EditText mPasswordEditText;
    TextInputLayout textInputLayoutEmail, textInputLayoutPassword;

    Button mSignInButton, mRegisterButton, mSkipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
        initButtonListener();




    }//end onCreate()

    private void getHeroes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Login>> call = api.authenticate();

        call.enqueue(new Callback<List<Login>>() {
            @Override
            public void onResponse(@NonNull Call<List<Login>> call, @NonNull Response<List<Login>> response) {
                List<Login> heroList = response.body();

                //Creating an String array for the ListView
                String[] heroes = new String[heroList.size()];

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < heroList.size(); i++) {
                    heroes[i] = heroList.get(i).getName();
                }

                //displaying the string array into listview
                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, heroes));
            }

            @Override
            public void onFailure(Call<List<Login>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
                if ( isEmailValid(mEmailEditText.getText().toString()) && isPasswordValid(mPasswordEditText.getText().toString())) {
                    attemptLogin();
                }
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
            }
        });
    }//initButtonListener()

    private void attemptLogin() {
        Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
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