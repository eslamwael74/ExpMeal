package com.eslamwael74.inq.expmeal;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.text_input_email)
    TextInputLayout textInputEmail;

    @BindView(R.id.edit_text_email)
    TextInputEditText mEditTextEmail;


    @BindView(R.id.text_input_password)
    TextInputLayout textInputPassword;

    @BindView(R.id.edit_text_password)
    TextInputEditText mEditTextPassword;

    @OnClick(R.id.btn_login)
    void LoginBtn() {
        Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @OnClick(R.id.register_now)
    public void tvRegisterClick() {
        Toast.makeText(this, "!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
}
