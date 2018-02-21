package com.eslamwael74.inq.expmeal.Screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eslamwael74.inq.expmeal.Adapter.FavouriteMealsAdapter;
import com.eslamwael74.inq.expmeal.Model.Meal;
import com.eslamwael74.inq.expmeal.R;
import com.eslamwael74.inq.expmeal.Utils.UtilsFunctions;
import com.eslamwael74.inq.expmeal.Widget.ExpService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eslam Wael on 2/20/2018.
 */

public class FavouriteFragment extends Fragment {

    private static final String ARG_Ex = "FavouriteFragment";
    private String example;

    FirebaseAuth mAuth;
    String uid;
    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    ArrayList<Meal> meals = new ArrayList<>();
    FavouriteMealsAdapter favouriteMealsAdapter;

    int firstToOpen = 0;

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


    void initFirebase() {

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

        if (firstToOpen == 0) {
            UtilsFunctions.showProgressbar(getActivity());
        }


        DatabaseReference databaseReference = firebaseDatabase.getReference("FavouriteMeals/users/" + uid + "/");

        Query query = databaseReference;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Meal meal = snapshot.getValue(Meal.class);
                        if (meal.getFav() == 1)
                            meals.add(meal);
                    }
                    init();

                } else {
                    meals = new ArrayList<>();
                    init();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    void init() {
        ExpService.updateService(meals, getActivity());
        favouriteMealsAdapter = new FavouriteMealsAdapter(meals, getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        favouriteMealsAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(favouriteMealsAdapter);

        meals = new ArrayList<>();

        UtilsFunctions.closeProgressbar();


    }


    @Override
    public void onPause() {
        super.onPause();
        firstToOpen = 1;
    }
}
