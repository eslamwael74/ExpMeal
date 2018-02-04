package com.eslamwael74.inq.expmeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.text_input_name)
    TextInputLayout textInputName;

    @BindView(R.id.edit_text_name)
    TextInputEditText mEditTextName;


    @BindView(R.id.text_input_email)
    TextInputLayout textInputEmail;

    @BindView(R.id.edit_text_email)
    TextInputEditText mEditTextEmail;


    @BindView(R.id.text_input_phone)
    TextInputLayout textInputPhone;

    @BindView(R.id.edit_text_phone)
    TextInputEditText mEditTextPhone;


    @BindView(R.id.text_input_password)
    TextInputLayout textInputPassword;

    @BindView(R.id.edit_text_password)
    TextInputEditText mEditTextPassword;


    @OnClick(R.id.btn_register)
    void registerBtn() {

    }

    @OnClick(R.id.login_now)
    void tvLoginClick() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

    }
}
