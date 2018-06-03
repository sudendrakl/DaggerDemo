package jsonexclusion.test.in.jsonexclusion.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sudendra on 2/6/18.
 */
@AutoValue
public abstract class ExcludeItem {
    public static TypeAdapter<ExcludeItem> typeAdapter(Gson gson) {
        return new AutoValue_ExcludeItem.GsonTypeAdapter(gson);
    }

    @SerializedName("group_id")
    public abstract String groupId();

    @SerializedName("variation_id")
    public abstract String variationId();
}
