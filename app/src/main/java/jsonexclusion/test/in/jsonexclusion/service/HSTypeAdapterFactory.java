package jsonexclusion.test.in.jsonexclusion.service;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by sudendra on 2/6/18.
 */

@GsonTypeAdapterFactory
public abstract class HSTypeAdapterFactory implements TypeAdapterFactory {
    public static TypeAdapterFactory create() {
        return new AutoValueGson_HSTypeAdapterFactory();
    }
}
