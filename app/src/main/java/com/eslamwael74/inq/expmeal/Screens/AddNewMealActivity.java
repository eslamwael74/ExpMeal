package com.eslamwael74.inq.expmeal.Screens;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.eslamwael74.inq.expmeal.Model.Meal;
import com.eslamwael74.inq.expmeal.R;
import com.eslamwael74.inq.expmeal.Utils.UtilsFunctions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddNewMealActivity extends AppCompatActivity {

    @BindView(R.id.meal_name)
    EditText editTextMealName;

    @BindView(R.id.edit_text_ingredients)
    EditText editTextIngredients;

    @BindView(R.id.edit_text_how_to)
    EditText editTextHowTo;

    @BindView(R.id.edit_text_time)
    EditText editTextTime;

    @BindView(R.id.meal_image)
    CircleImageView mealImage;

    @OnClick({R.id.radio1, R.id.radio2, R.id.radio3})
    void onRadioBtnClick(RadioButton radioButton) {
        // Is the button now checked?
        boolean checked = radioButton.isChecked();

        switch (radioButton.getId()) {
            case R.id.radio1:
                if (checked) {
                    category = 0;
                }
                break;
            case R.id.radio2:
                if (checked) {
                    category = 1;
                }
                break;
            case R.id.radio3:
                if (checked) {
                    category = 2;
                }
                break;
        }
    }

    @OnClick(R.id.btn_add)
    void addBtnClick() {
        if (validate())
            initFirebase();
    }


    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    int category = 74;
    FirebaseAuth mAuth;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_meal);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);
        initAuth();
    }

    void initAuth() {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        uid = firebaseUser.getUid();
    }

    void initFirebase() {

        UtilsFunctions.showProgressbar(this);

        DatabaseReference databaseReference = firebaseDatabase.getReference("meals");

        String id = databaseReference.push().getKey();

        Meal meal = new Meal(
                id,
                uid,
                editTextMealName.getText().toString().trim(),
                "sa",
                editTextIngredients.getText().toString().trim(),
                category,
                editTextHowTo.getText().toString().trim(),
                editTextTime.getText().toString().trim(),
                0
        );
        databaseReference.child(id).setValue(meal).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    UtilsFunctions.closeProgressbar();
                    finish();
                }
            }
        });
    }

    boolean validate() {
        String mealName = editTextMealName.getText().toString().trim();
        String ingredients = editTextIngredients.getText().toString().trim();
        String hToPrepare = editTextHowTo.getText().toString().trim();
        String prepTime = editTextTime.getText().toString().trim();

        if (TextUtils.isEmpty(mealName)) {
            Toast.makeText(this, getString(R.string.name_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(ingredients)) {
            Toast.makeText(this, getString(R.string.ingredients_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (category == 74) {
            Toast.makeText(this, getString(R.string.category_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(hToPrepare)) {
            Toast.makeText(this, getString(R.string.how_to_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(prepTime)) {
            Toast.makeText(this, getString(R.string.perperation_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
