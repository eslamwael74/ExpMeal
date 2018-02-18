package com.eslamwael74.inq.expmeal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
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
        initFirebase();
    }


    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    int category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_meal);

        ButterKnife.bind(this);
    }

    void initFirebase() {
        DatabaseReference databaseReference = firebaseDatabase.getReference("meals");

        String id = databaseReference.push().getKey();

        Meal meal = new Meal(
                id,
                editTextMealName.getText().toString().trim(),
                "sa",
                editTextIngredients.getText().toString().trim(),
                category,
                editTextHowTo.getText().toString().trim(),
                editTextTime.getText().toString().trim()
        );
        databaseReference.child(id).setValue(meal);
    }
}
