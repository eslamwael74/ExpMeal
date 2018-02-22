package com.eslamwael74.inq.expmeal.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eslamwael74.inq.expmeal.Model.Meal;
import com.eslamwael74.inq.expmeal.R;
import com.eslamwael74.inq.expmeal.Utils.UtilsFunctions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

/**
 * Created by eslamwael74 on 2/4/2018.
 */

public class MealFragment extends Fragment {


    private static final String ARG_Ex = "MealFragment";
    private String example;

    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    Meal meal = new Meal();
    int index;


    @BindView(R.id.tv_meal)
    TextView tvMeal;

    @OnClick(R.id.card)
    void mealClick() {
        MealDetailsFragment mealFragment = MealDetailsFragment.newInstance(meal);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_home, mealFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.btn_generate_new_meal)
    void generateBtnClick() {
        initFirebase();
    }

    @OnClick(R.id.lin_add)
    void addBtnClick() {
        startActivity(new Intent(getActivity(), AddNewMealActivity.class));
    }

    @OnClick(R.id.lin_fav)
    void favBtnClick() {
        FavouriteFragment favouriteFragment = FavouriteFragment.newInstance("");
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_home, favouriteFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public MealFragment() {
    }

    public static MealFragment newInstance(String example) {
        MealFragment firstFragment = new MealFragment();
        Bundle args = new Bundle();
        args.putString(ARG_Ex, example);
        firstFragment.setArguments(args);
        return firstFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        example = getArguments().getString(ARG_Ex);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_meal, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState == null){
            initFirebase();
        }
        else{
            meal = savedInstanceState.getParcelable("meal");
            tvMeal.setText(meal.getName());
        }


        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("meal",meal);
    }

    ArrayList<Meal> meals = new ArrayList<>();
    int count = 2;
    DatabaseReference databaseReference;

    void initFirebase() {

        UtilsFunctions.showProgressbar(getActivity());

        databaseReference = firebaseDatabase.getReference("meals");


        Query query = databaseReference;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Meal meal = snapshot.getValue(Meal.class);
                    meals.add(meal);
                }
                Log.e(TAG, "onChildAdded: " + dataSnapshot.getChildren());
                generateNewMeal(meals);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    void generateNewMeal(ArrayList<Meal> mealList) {

        Random random = new Random();

        index = random.nextInt(mealList.size());
        meal = mealList.get(index);

        tvMeal.setText(meal.getName());

        count = 2;
        meals = new ArrayList<>();

        UtilsFunctions.closeProgressbar();

    }
}
