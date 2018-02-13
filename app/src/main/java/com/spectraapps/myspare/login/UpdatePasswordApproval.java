package com.spectraapps.myspare.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.spectraapps.myspare.R;

public class UpdatePasswordApproval extends AppCompatActivity {

    boolean isPasswordShown;

    ImageButton mImagePasswrdVisible,mImagePasswrdVisible2;
    EditText mEditTextPassOld,mEditTextPassNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password_approval);

        initUI();
        initButtonClickListener();
    }

    private void initUI() {
        mImagePasswrdVisible = findViewById(R.id.image_visible);
        mImagePasswrdVisible2 = findViewById(R.id.image_visible2);

        mEditTextPassOld = findViewById(R.id.oldpasswordET);
        mEditTextPassNew = findViewById(R.id.newpasswordET);

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
                if (isPasswordShown) {
                    mImagePasswrdVisible2.setImageResource(R.drawable.ic_visibility_white_24dp);
                    mEditTextPassNew.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isPasswordShown = false;
                } else {
                    mImagePasswrdVisible2.setImageResource(R.drawable.ic_visibility_off_white_24dp);
                    mEditTextPassNew.setInputType(InputType.TYPE_CLASS_TEXT);
                    isPasswordShown = true;
                }
            }
        });
    }
}
