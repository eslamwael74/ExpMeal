package com.eslamwael74.inq.expmeal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.eslamwael74.inq.expmeal.RegisterActivity.validEmail;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

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
        initFirebase();
    }

    @OnClick(R.id.register_now)
    public void tvRegisterClick() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

    }


    private static final String TAG = "LoginActivity";
    void initFirebase() {

        if (validateData()) {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(this,MainActivity.class));
                            finishAffinity();
                        } else {
                            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "initFirebase: " + task.getException());
                        }
                    });
        }
    }


    String email,password;
    boolean validateData() {

        email = mEditTextEmail.getText().toString().trim();
        password = mEditTextPassword.getText().toString().trim();

        if (validEmail(email)) {
            Toast.makeText(this, getString(R.string.email_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            Toast.makeText(this, getString(R.string.password_error), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
