package com.eslamwael74.inq.expmeal;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Eslam Wael on 2/20/2018.
 */

public class FavouriteMealsAdapter extends RecyclerView.Adapter<FavouriteMealsAdapter.MyViewHolder> {

    ArrayList<Meal> meals;
    FragmentActivity fragmentActivity;

    public FavouriteMealsAdapter(ArrayList<Meal> meals, FragmentActivity fragmentActivity) {
        this.meals = meals;
        this.fragmentActivity = fragmentActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(fragmentActivity)
                .inflate(R.layout.meal_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Meal meal = meals.get(position);

        if (meal.getImage() != null) {
            Picasso.with(fragmentActivity)
                    .load(meal.getImage())
                    .fit()
                    .centerCrop()
                    .error(R.drawable.meal_place_holder)
                    .placeholder(R.drawable.meal_place_holder)
                    .into(holder.mImage);
        }
        holder.tvName.setText(meal.getName());

        if (meal.getCategory() == 0)
            holder.tvCategory.setText(fragmentActivity.getString(R.string.breakfast));
        else if(meal.getCategory() == 1)
            holder.tvCategory.setText(fragmentActivity.getString(R.string.lunch));
        else
            holder.tvCategory.setText(fragmentActivity.getString(R.string.dinner));

        holder.mCardView.setOnClickListener(v -> {
            MealDetailsFragment mealDetailsFragment = MealDetailsFragment.newInstance(meal);
            FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fav_frame, mealDetailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_category)
        TextView tvCategory;

        @BindView(R.id.card)
        CardView mCardView;

        @BindView(R.id.image)
        CircleImageView mImage;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


}
