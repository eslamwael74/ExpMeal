package com.eslamwael74.inq.expmeal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

/**
 * Created by eslamwael74 on 2/4/2018.
 */

public class MealDetailsFragment extends Fragment {


    private static final String ARG_Ex = "MealFragment";
    private Meal meal;

    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth;
    String uid;
    boolean isFav = false;

    @BindView(R.id.fav_img)
    ImageView mFavImage;

    @OnClick(R.id.fav_img)
    void favClick() {
//        deAttachDatabaseReadListener();
        if (isFav)
            removeFromFavourite();
        else
            addToFavourites();
    }

    @BindView(R.id.tv_meal_name)
    TextView tvMealName;

    @BindView(R.id.tv_ingredients)
    TextView tvIngredients;

    @BindView(R.id.tv_category)
    TextView tvCategory;

    @BindView(R.id.tv_how_to)
    TextView tvHowTo;

    @BindView(R.id.tv_time)
    TextView tvPreparationTime;


    public MealDetailsFragment() {
    }

    public static MealDetailsFragment newInstance(Meal meal) {
        MealDetailsFragment firstFragment = new MealDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_Ex, meal);
        firstFragment.setArguments(args);
        return firstFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meal = getArguments().getParcelable(ARG_Ex);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_meal_details, container, false);
        ButterKnife.bind(this, view);

        if (meal != null)
            init();
        else
            getActivity().onBackPressed();

        return view;

    }

    void init() {


        initFirebase();

        tvMealName.setText(meal.getName());
        tvIngredients.setText(meal.getIngredients());
        if (meal.getCategory() == 0)
            tvCategory.setText(getString(R.string.breakfast));
        else if (meal.getCategory() == 1)
            tvCategory.setText(getString(R.string.lunch));
        else
            tvCategory.setText(getString(R.string.dinner));
        tvHowTo.setText(meal.gethToPrepare());
        tvPreparationTime.setText(meal.getPerpetrationTime());


    }

    ValueEventListener mValueEventListener;

    DatabaseReference databaseReference;

    void initFirebase() {

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();


        databaseReference = firebaseDatabase.getReference("FavouriteMeals/users/" + uid + "/" + meal.getId());
        Log.d(TAG, "initFirebase: " + meal.getId());
//        databaseReference.child("users/").child(uid);

        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Meal newMeal = dataSnapshot.getValue(Meal.class);

                if (newMeal != null) {
                    if (newMeal.getFav() == 0) {
                        isFav = false;
                        mFavImage.setImageResource(R.drawable.ic_star_border_red_24dp);
                    } else {
                        isFav = true;
                        mFavImage.setImageResource(R.drawable.ic_star_red_24dp);
                    }
                } else {
                    isFav = false;
                    mFavImage.setImageResource(R.drawable.ic_star_border_red_24dp);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addValueEventListener(mValueEventListener);


    }

    void deAttachDatabaseReadListener() {
        if (mValueEventListener != null) {
            databaseReference.removeEventListener(mValueEventListener);
            mValueEventListener = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        deAttachDatabaseReadListener();
    }

    void addToFavourites() {

        DatabaseReference databaseReference = firebaseDatabase.getReference("FavouriteMeals");

        Meal m = new Meal(meal.getId(),
                uid,
                meal.getName(),
                meal.getImage(),
                meal.getIngredients(),
                meal.getCategory(),
                meal.gethToPrepare(),
                meal.getPerpetrationTime(),
                1);
        isFav = true;

        databaseReference.child("users").child(uid).child(meal.getId()).setValue(m).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                isFav = true;
                mFavImage.setImageResource(R.drawable.ic_star_red_24dp);
            }
        });

    }

    void removeFromFavourite() {
        DatabaseReference databaseReference = firebaseDatabase.getReference("FavouriteMeals");

        databaseReference.child("users").child(uid).child(meal.getId()).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                isFav = false;
                mFavImage.setImageResource(R.drawable.ic_star_border_red_24dp);

            }
        });

    }
}
