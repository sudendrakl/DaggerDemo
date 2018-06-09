package com.blackbeard.dagger.demo.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by sudendra on 2/6/18.
 */
@AutoValue
public abstract class ResponseWrapper {
    public static TypeAdapter<ResponseWrapper> typeAdapter(Gson gson) {
        return new AutoValue_ResponseWrapper.GsonTypeAdapter(gson);
    }


    public abstract Variants variants();
}
