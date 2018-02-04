package com.eslamwael74.inq.expmeal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by eslamwael74 on 2/4/2018.
 */

public class MealDetailsFragment extends Fragment {


    private static final String ARG_Ex = "MealFragment";
    private String example;

    @BindView(R.id.tv_meal)
    TextView tvMeal;

    @OnClick(R.id.btn_generate_new_meal)
    void generateBtnClick() {
        Toast.makeText(getActivity(), "Generate new meal", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.lin_add)
    void addBtnClick() {

    }

    @OnClick(R.id.lin_fav)
    void favBtnClick() {

    }


    public MealDetailsFragment() {
    }

    public static MealDetailsFragment newInstance(String example) {
        MealDetailsFragment firstFragment = new MealDetailsFragment();
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

        View view = inflater.inflate(R.layout.fragment_meal_details, container, false);

        return view;
    }
}
