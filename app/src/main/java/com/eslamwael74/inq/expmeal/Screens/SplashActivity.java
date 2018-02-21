package com.eslamwael74.inq.expmeal.Screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eslamwael74.inq.expmeal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);

        mAuth = FirebaseAuth.getInstance();

//        new Handler().postDelayed(() -> {

        mAuthStateListener = firebaseAuth -> {

            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            if (firebaseUser == null) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finishAffinity();
            } else {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finishAffinity();
            }

        };

//        }, 1000);

    }


    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null)
            mAuth.removeAuthStateListener(mAuthStateListener);
    }

}
