package com.blackbeard.dagger.demo.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sudendra on 2/6/18.
 */
@AutoValue
public abstract class Variations {
    public static TypeAdapter<Variations> typeAdapter(Gson gson) {
        return new AutoValue_Variations.GsonTypeAdapter(gson);
    }

    public abstract String name();

    public abstract int price();

    @SerializedName("default")
    public abstract int defaultVal();

    public abstract String id();

    public abstract int inStock();

    @Nullable
    public abstract String isVeg();

}
