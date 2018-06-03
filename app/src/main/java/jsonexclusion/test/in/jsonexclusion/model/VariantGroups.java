package jsonexclusion.test.in.jsonexclusion.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sudendra on 2/6/18.
 */

@AutoValue
public abstract class VariantGroups {

    public static TypeAdapter<VariantGroups> typeAdapter(Gson gson) {
        return new AutoValue_VariantGroups.GsonTypeAdapter(gson);
    }

    @SerializedName("group_id")
    public abstract String groupId();

    public abstract String name();

    public abstract List<Variations> variations();

}
