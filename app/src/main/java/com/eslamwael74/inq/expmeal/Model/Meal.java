package com.eslamwael74.inq.expmeal.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eslam Wael on 2/17/2018.
 */

public class Meal implements Parcelable {

    String id;

    String uid;

    String name;

    String image;

    String ingredients;

    int category;

    String hToPrepare;

    String perpetrationTime;

    int fav;

    public Meal() {

    }

    public Meal(String id, String uid, String name, String image, String ingredients, int category, String hToPrepare, String perpetrationTime, int fav) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.image = image;
        this.ingredients = ingredients;
        this.category = category;
        this.hToPrepare = hToPrepare;
        this.perpetrationTime = perpetrationTime;
        this.fav = fav;
    }

    protected Meal(Parcel in) {
        id = in.readString();
        uid = in.readString();
        name = in.readString();
        image = in.readString();
        ingredients = in.readString();
        category = in.readInt();
        hToPrepare = in.readString();
        perpetrationTime = in.readString();
        fav = in.readInt();
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

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

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

    public int getFav() {
        return fav;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(uid);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(ingredients);
        dest.writeInt(category);
        dest.writeString(hToPrepare);
        dest.writeString(perpetrationTime);
        dest.writeInt(fav);
    }
}
