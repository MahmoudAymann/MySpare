package com.spectraapps.myspare.navdrawer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.spectraapps.myspare.utility.ListSharedPreference;
import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.model.UpdateProfileModel;
import com.spectraapps.myspare.network.MyRetrofitClient;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity{

    ImageButton editNameBtn, editEmailBtn, editMobileBtn;

    CircleImageView imageView;

    TextView nameTV, emailTV, mobileTV;

    EditText nameET, emailET, mobileET;

    boolean isNameShown=true, isEmailShown=true, isMobileShown=true;
    FButton updateBtn;
    String userId;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(" ");


        initUI();

        initClickListener();

    }//end onCreate

    @Override

    protected void onStart() {
        super.onStart();
        getDataFromIntent();
    }

    private void getDataFromIntent() {

        progressDialog.show();
        nameTV.setText(getIntent().getStringExtra("name"));
        emailTV.setText(getIntent().getStringExtra("email"));
        mobileTV.setText(getIntent().getStringExtra("mobile"));
        userId = getIntent().getStringExtra("id");

        Picasso.with(ProfileActivity.this)
                .load(getIntent().getStringExtra("image"))
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(imageView);
        progressDialog.dismiss();
    }

    private void showChangeStatusDialoge() {
        AlertDialog.Builder alert = new AlertDialog.Builder(ProfileActivity.this);

        final EditText edittext = new EditText(ProfileActivity.this);
        alert.setMessage("hh");
        alert.setTitle("aaa");

        alert.setView(edittext);

        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                String youEditTextValue = edittext.getText().toString();

            }
        });

        alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void initClickListener() {
        editNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNameShown) {
                    setNameEditTextVisibility(true);
                    nameET.setText(nameTV.getText().toString());
                } else {
                    setNameEditTextVisibility(false);
                    nameTV.setText(nameET.getText().toString());
                }
            }
        });

        editEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmailShown) {
                    setEmailEditTextVisibility(true);
                    emailET.setText(emailTV.getText().toString());
                } else {
                    setEmailEditTextVisibility(false);
                    emailTV.setText(emailET.getText().toString());
                }
            }
        });


        editMobileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMobileShown) {
                    setMobileEditTextVisibility(true);
                    mobileET.setText(mobileTV.getText().toString());
                } else {
                    setMobileEditTextVisibility(false);
                    mobileTV.setText(mobileET.getText().toString());
                }
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                serverUpdateUserInfo();
            }
        });


    }

    private void serverUpdateUserInfo() {

        String name = nameTV.getText().toString();
        String email = emailTV.getText().toString();
        String mobile = mobileTV.getText().toString();
        String id = userId;


        Api retrofit = MyRetrofitClient.getBase().create(Api.class);
        Call<UpdateProfileModel> call = retrofit.updateProfile(id,name,email,mobile);

        call.enqueue(new Callback<UpdateProfileModel>() {
            @Override
            public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(ProfileActivity.this, "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();

                    String name = response.body().getData().getName();
                    String email = response.body().getData().getMail();
                    String mobile = response.body().getData().getMobile();

                    saveUserInfo(name, email, mobile);

                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    intent.putExtra("login", 1);
                    startActivity(intent);
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveUserInfo(String name, String email, String mobile) {
//        listSharedPreference.setUName(getApplicationContext(),name);
//        listSharedPreference.setEmail(getApplicationContext(),email);
//        listSharedPreference.setMobile(getApplicationContext(),mobile);

        Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("email",email);
        intent.putExtra("mobile",mobile);

    }

    private void initUI() {
        imageView = findViewById(R.id.profile_imageView);

        editNameBtn = findViewById(R.id.edit_profileName);
        editEmailBtn = findViewById(R.id.edit_profileEmail);
        editMobileBtn = findViewById(R.id.edit_profileMobile);

        nameET = findViewById(R.id.etName_profile);
        nameTV = findViewById(R.id.tvName_profile);

        emailTV = findViewById(R.id.tvEmail_profile);
        emailET = findViewById(R.id.etEmail_profile);

        mobileTV = findViewById(R.id.tvMobile_profile);
        mobileET = findViewById(R.id.etMobile_profile);


        progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(false);

        updateBtn = findViewById(R.id.updateButton_PA);
        updateBtn.setButtonColor(getResources().getColor(R.color.dark_yellow));
        updateBtn.setShadowColor(getResources().getColor(R.color.white_gray_transperant));
        updateBtn.setCornerRadius(10);
        updateBtn.setShadowEnabled(true);
        updateBtn.setShadowHeight(7);
    }

    private void setNameEditTextVisibility(boolean setVis) {
        if (setVis)//true
        {
            isNameShown = false;
            nameTV.setVisibility(View.INVISIBLE);
            editNameBtn.setImageResource(R.drawable.ic_accept_36dp);
            nameET.setVisibility(View.VISIBLE);
        } else {
            isNameShown = true;
            nameTV.setVisibility(View.VISIBLE);
            editNameBtn.setImageResource(R.drawable.ic_edit_36dp);

            nameET.setVisibility(View.INVISIBLE);
        }
    }//end


    private void setEmailEditTextVisibility(boolean setVis) {
        if (setVis)//true
        {
            isEmailShown = false;
            emailTV.setVisibility(View.INVISIBLE);
            editEmailBtn.setImageResource(R.drawable.ic_accept_36dp);
            emailET.setVisibility(View.VISIBLE);
        } else {
            isEmailShown = true;
            emailTV.setVisibility(View.VISIBLE);
            editEmailBtn.setImageResource(R.drawable.ic_edit_36dp);
            emailET.setVisibility(View.INVISIBLE);
        }
    }//end


    private void setMobileEditTextVisibility(boolean setVis) {
        if (setVis)//true
        {
            isMobileShown = false;
            mobileTV.setVisibility(View.INVISIBLE);
            editMobileBtn.setImageResource(R.drawable.ic_accept_36dp);
            mobileET.setVisibility(View.VISIBLE);
        } else {
            isMobileShown = true;
            mobileTV.setVisibility(View.VISIBLE);
            editMobileBtn.setImageResource(R.drawable.ic_edit_36dp);
            mobileET.setVisibility(View.INVISIBLE);
        }
    }//end


}//end class