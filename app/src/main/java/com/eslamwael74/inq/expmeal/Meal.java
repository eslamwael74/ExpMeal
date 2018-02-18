package com.eslamwael74.inq.expmeal;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eslam Wael on 2/17/2018.
 */

public class Meal implements Parcelable {

    String id;

    String name;

    String image;

    String ingredients;

    int category;

    String hToPrepare;

    String perpetrationTime;

    public Meal() {

    }


    public Meal(String id, String name, String image, String ingredients, int category, String hToPrepare, String perpetrationTime) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.ingredients = ingredients;
        this.category = category;
        this.hToPrepare = hToPrepare;
        this.perpetrationTime = perpetrationTime;
    }


    protected Meal(Parcel in) {
        name = in.readString();
        image = in.readString();
        ingredients = in.readString();
        category = in.readInt();
        hToPrepare = in.readString();
        perpetrationTime = in.readString();
    }


    public static final Creator<Meal> CREATOR = new Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getIngredients() {
        return ingredients;
    }

    public int getCategory() {
        return category;
    }

    public String gethToPrepare() {
        return hToPrepare;
    }

    public String getPerpetrationTime() {
        return perpetrationTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void sethToPrepare(String hToPrepare) {
        this.hToPrepare = hToPrepare;
    }

    public void setPerpetrationTime(String perpetrationTime) {
        this.perpetrationTime = perpetrationTime;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(ingredients);
        dest.writeInt(category);
        dest.writeString(hToPrepare);
        dest.writeString(perpetrationTime);
    }
}
