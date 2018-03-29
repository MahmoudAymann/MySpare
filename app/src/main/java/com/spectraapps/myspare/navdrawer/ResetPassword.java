package com.spectraapps.myspare.navdrawer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.model.ResetPasswordModel;
import com.spectraapps.myspare.network.MyRetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {

    EditText editText;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editText = findViewById(R.id.reg_nameET);
        sendButton = findViewById(R.id.button_sendreset);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverLogin();
            }
        });

    }

    private void serverLogin() {

        Api retrofit= MyRetrofitClient.getBase().create(Api.class);

        Call<ResetPasswordModel> resetPasswordCall = retrofit.resetPassword(
                editText.getText().toString());

        resetPasswordCall.enqueue(new Callback<ResetPasswordModel>()
        {
            @Override
            public void onResponse(@NonNull Call<ResetPasswordModel> call, @NonNull Response<ResetPasswordModel> response)
            {
                if (response.isSuccessful()){
                    Toast.makeText(ResetPassword.this, ""+response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ResetPassword.this, MainActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(ResetPassword.this, ""+response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResetPasswordModel> call, @NonNull Throwable t)
            {
                Toast.makeText(ResetPassword.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }

}
