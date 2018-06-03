package jsonexclusion.test.in.jsonexclusion.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.subjects.PublishSubject;
import jsonexclusion.test.in.jsonexclusion.databinding.ChoiceItemBinding;
import jsonexclusion.test.in.jsonexclusion.model.VariantGroups;
import jsonexclusion.test.in.jsonexclusion.model.Variations;

/**
 * Created by sudendra on 2/6/18.
 */

public class ChoiceAdapter extends RecyclerView.Adapter<ChoiceAdapter.ChoiceVH> {

    private final List<Variations> list;
    private String groupId;
    private HashMap<String, List<String>> excludeList;
    private HashMap<String, Boolean> choices;
    private PublishSubject<Pair<String, Boolean>> choiceObserver;

    public ChoiceAdapter(PublishSubject<Pair<String, Boolean>> choiceObserver) {
        this.choiceObserver = choiceObserver;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ChoiceVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChoiceItemBinding binding = ChoiceItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChoiceVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoiceVH holder, int position) {
        Variations variation = list.get(position);
        holder.binding.setVariation(variation);
        holder.binding.setGroupId(groupId);
        holder.binding.setObserver(choiceObserver);
        holder.binding.executePendingBindings();
        boolean disabled = isDisabled(variation);
        Log.d("disabled", "onBindViewHolder: " + disabled);
        holder.binding.setDisabled(disabled);
    }

    private boolean isDisabled(Variations variation) {
        for (Map.Entry<String,Boolean> entry: choices.entrySet()) {
            if (entry.getValue()) {
                List<String> myExcludeList = excludeList.get(entry.getKey());
                if (myExcludeList != null && myExcludeList.contains(variation.id())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(VariantGroups variantGroups, HashMap<String, List<String>> excludeList, HashMap<String, Boolean> choices) {
        this.groupId = variantGroups.groupId();
        this.excludeList = excludeList;
        this.choices = choices;
        this.list.clear();
        this.list.addAll(variantGroups.variations());
        notifyDataSetChanged();
    }

    static class ChoiceVH extends RecyclerView.ViewHolder {

        private final ChoiceItemBinding binding;

        ChoiceVH(ChoiceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}