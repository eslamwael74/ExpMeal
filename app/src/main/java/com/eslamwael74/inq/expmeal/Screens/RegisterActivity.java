package com.eslamwael74.inq.expmeal.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eslamwael74.inq.expmeal.R;
import com.eslamwael74.inq.expmeal.Model.User;
import com.eslamwael74.inq.expmeal.Utils.UtilsFunctions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    String name;
    String email;
    String phone;
    String password;

    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

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
        initFirebase();
    }

    @OnClick(R.id.login_now)
    void tvLoginClick() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

    }

    void addNewUser(String name, String email, String phone, String password, String uid) {
        DatabaseReference databaseReference = database.getReference("users");

        User user = new User(name, email, phone, password);

        databaseReference.child(name).child(uid).setValue(user).
                addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        UtilsFunctions.closeProgressbar();
                        startActivity(new Intent(this, MainActivity.class));
                        finishAffinity();

                    } else {
                        Log.e(TAG, "addNewUser: " + task.getException());
                    }
                });

    }

    private static final String TAG = "RegisterActivity";

    void initFirebase() {

        if (validateData()) {
            UtilsFunctions.showProgressbar(this);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            firebaseUser = task.getResult().getUser();

                            addNewUser(name,
                                    email,
                                    phone,
                                    password,
                                    firebaseUser.getUid()
                            );
                        } else {
                            Log.e(TAG, "initFirebase: " + task.getException());
                        }
                    });
        }
    }

    boolean validateData() {

        name = mEditTextName.getText().toString().trim();
        email = mEditTextEmail.getText().toString().trim();
        phone = mEditTextPhone.getText().toString().trim();
        password = mEditTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name) || name.length() < 6) {
            Toast.makeText(this, getString(R.string.name_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(phone) || phone.length() < 9) {
            Toast.makeText(this, getString(R.string.phone_error), Toast.LENGTH_SHORT).show();
            return false;
        }
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

    static boolean validEmail(String email) {
        return email.matches("[A-Z0-9._%+-][A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{3}");
    }

}
