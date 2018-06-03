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
public abstract class Variants {
    public static TypeAdapter<Variants> typeAdapter(Gson gson) {
        return new AutoValue_Variants.GsonTypeAdapter(gson);
    }

    @SerializedName("variant_groups")
    public abstract List<VariantGroups> variantGroups();

    @SerializedName("exclude_list")
    public abstract List<List<ExcludeItem>> excludeList();
}
