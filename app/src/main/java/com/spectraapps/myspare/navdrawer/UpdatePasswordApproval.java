package com.spectraapps.myspare.navdrawer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.login.LoginActivity;
import com.spectraapps.myspare.model.UpdatePasswordModel;
import com.spectraapps.myspare.network.MyRetrofitClient;
import com.spectraapps.myspare.utility.ListSharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordApproval extends AppCompatActivity {

    boolean isPasswordShown;

    ImageButton mImagePasswrdVisible,mImagePasswrdVisible2;
    EditText mEditTextPassOld,mEditTextPassNew;


    ListSharedPreference.Set setSharedPreference;
    ListSharedPreference.Get getSharedPreference;

    Button mButton;
    private ProgressDialog progressDialog;
    AlertDialog.Builder alertDialogBuilder;
    private boolean isPasswordShown2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password_approval);

        initUI();
        initButtonClickListener();
    }

    private void initUI() {
        mImagePasswrdVisible = findViewById(R.id .image_visible);
        mImagePasswrdVisible2 = findViewById(R.id.image_visible2);

        setSharedPreference = new ListSharedPreference.Set(UpdatePasswordApproval.this.getApplicationContext());
        getSharedPreference = new ListSharedPreference.Get(UpdatePasswordApproval.this.getApplicationContext());

        mEditTextPassOld = findViewById(R.id.oldpasswordET);
        mEditTextPassNew = findViewById(R.id.newpasswordET);

        progressDialog = new ProgressDialog(UpdatePasswordApproval.this);
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(false);

        mButton = findViewById(R.id.button_updateApprovPass);

        alertDialogBuilder = new AlertDialog.Builder(UpdatePasswordApproval.this);
        alertDialogBuilder.setNegativeButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    }

    private void initButtonClickListener() {
        mImagePasswrdVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordShown) {
                    mImagePasswrdVisible.setImageResource(R.drawable.ic_visibility_white_24dp);
                    mEditTextPassOld.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isPasswordShown = false;
                } else {
                    mImagePasswrdVisible.setImageResource(R.drawable.ic_visibility_off_white_24dp);
                    mEditTextPassOld.setInputType(InputType.TYPE_CLASS_TEXT);
                    isPasswordShown = true;
                }
            }
        });

        mImagePasswrdVisible2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordShown2) {
                    mImagePasswrdVisible2.setImageResource(R.drawable.ic_visibility_white_24dp);
                    mEditTextPassNew.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isPasswordShown2 = false;
                } else {
                    mImagePasswrdVisible2.setImageResource(R.drawable.ic_visibility_off_white_24dp);
                    mEditTextPassNew.setInputType(InputType.TYPE_CLASS_TEXT);
                    isPasswordShown2 = true;
                }
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   serverUpdatePassword();
            }
        });

    }

    private void serverUpdatePassword() {
        progressDialog.show();
        Api retrofit = MyRetrofitClient.getBase().create(Api.class);
        Call<UpdatePasswordModel> call = retrofit.updatePassword(getSharedPreference.getUId(),mEditTextPassOld.getText().toString(),mEditTextPassNew.getText().toString());

        call.enqueue(new Callback<UpdatePasswordModel>() {
            @Override
            public void onResponse(Call<UpdatePasswordModel> call, Response<UpdatePasswordModel> response) {

                try {

                    if (response.isSuccessful()) {
                        if (response.body().getData() != null) {
                            progressDialog.dismiss();
                            Toast.makeText(UpdatePasswordApproval.this,""+response.body().getStatus().getTitle(),
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdatePasswordApproval.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(UpdatePasswordApproval.this, "error:" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialogBuilder.setMessage("Error: " + e);
                    alertDialog.show();
                }//end catch

            }//edn onResponse

            @Override
            public void onFailure(Call<UpdatePasswordModel> call, Throwable t) {
                try {
                    progressDialog.dismiss();
                    Toast.makeText(UpdatePasswordApproval.this, "exc:" + t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    progressDialog.dismiss();
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialogBuilder.setMessage("Error: " + e);
                    alertDialog.show();
                }
            }
        });
    }
}
