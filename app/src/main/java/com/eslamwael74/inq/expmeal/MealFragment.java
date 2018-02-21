package com.eslamwael74.inq.expmeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by eslamwael74 on 2/4/2018.
 */

public class MealFragment extends Fragment {


    private static final String ARG_Ex = "MealFragment";
    private String example;

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


    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    ChildEventListener mChildEventListener;


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

        initFirebase();

        return view;
    }

    ArrayList<Meal> meals = new ArrayList<>();
    int count = 1;
    DatabaseReference databaseReference;
    void initFirebase() {
        databaseReference = firebaseDatabase.getReference("meals");

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                count++;


                Meal meal = dataSnapshot.getValue(Meal.class);
                meals.add(meal);
                if (count <= dataSnapshot.getChildrenCount()) {
                    generateNewMeal(meals);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addChildEventListener(mChildEventListener);

//        String id =  databaseReference.push().getKey();
//
//        Meal meal = new Meal("secondMeal","aaa","eeweweewew",0,"w","50");
//        databaseReference.child(id).setValue(meal);
    }

    void deAttachDatabaseReadListener() {
        if (mChildEventListener != null) {
            databaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        deAttachDatabaseReadListener();
    }

    Meal meal = new Meal();
    int index;

    void generateNewMeal(ArrayList<Meal> mealList) {

        Random random = new Random();

        index = random.nextInt(mealList.size());
        meal = mealList.get(index);

        tvMeal.setText(meal.getName());

    }
}
