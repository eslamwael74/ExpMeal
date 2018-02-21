package com.eslamwael74.inq.expmeal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by Eslam Wael on 2/20/2018.
 */

public class FavouriteFragment extends Fragment {

    private static final String ARG_Ex = "FavouriteFragment";
    private String example;

    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    public static FavouriteFragment newInstance(String example) {
        FavouriteFragment firstFragment = new FavouriteFragment();
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

        View view = inflater.inflate(R.layout.fragment_favourite_meals, container, false);
        ButterKnife.bind(this, view);

        initFirebase();

        return view;
    }

    FirebaseAuth mAuth;
    String uid;
    ArrayList<Meal> meals = new ArrayList<>();
    int count = 0;

    FavouriteMealsAdapter favouriteMealsAdapter;

    void initFirebase() {

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

        DatabaseReference databaseReference = firebaseDatabase.getReference("FavouriteMeals/users/" + uid + "/");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                count = (int) dataSnapshot.getChildrenCount();


                Meal meal = dataSnapshot.getValue(Meal.class);

                if (meal.getFav() == 1)
                    meals.add(meal);

                init();


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
        });


    }

    void init() {
        if (meals.size() != 0) {
            favouriteMealsAdapter = new FavouriteMealsAdapter(meals, getActivity());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);
            favouriteMealsAdapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(favouriteMealsAdapter);

        }
    }

}
