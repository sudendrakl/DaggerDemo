package com.blackbeard.dagger.demo.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.subjects.PublishSubject;
import jsonexclusion.test.in.jsonexclusion.databinding.ListItemBinding;

import com.blackbeard.dagger.demo.model.VariantGroups;

/**
 * Created by sudendra on 2/6/18.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainVH> {

    private final List<VariantGroups> variants;
    private PublishSubject<Pair<String, Boolean>> choiceObserver;
    private HashMap<String, Boolean> choices;
    private HashMap<String, List<String>> exclusions;

    public MainAdapter(PublishSubject<Pair<String, Boolean>> choiceObserver) {
        this.choiceObserver = choiceObserver;
        variants = new ArrayList<>();
    }

    @NonNull
    @Override
    public MainVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding binding = ListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        MainVH mainVH = new MainVH(binding);
        setUpChildAdapter(mainVH);
        return mainVH;
    }

    @Override
    public void onBindViewHolder(@NonNull MainVH holder, int position) {
        VariantGroups variantGroups = variants.get(position);
        holder.binding.setVariantGroups(variantGroups);
        ChoiceAdapter adapter = (ChoiceAdapter) holder.binding.choices.getAdapter();
        adapter.setList(variantGroups, exclusions, choices);
        holder.binding.executePendingBindings();
    }

    private void setUpChildAdapter(@NonNull MainVH holder) {
        ChoiceAdapter adapter = (ChoiceAdapter) holder.binding.choices.getAdapter();
        if (adapter == null) {
            adapter = new ChoiceAdapter(choiceObserver);
        }
        holder.binding.choices.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return variants.size();
    }

    public void setChoices(HashMap<String, Boolean> choices) {
        this.choices = choices;
        notifyDataSetChanged();
    }

    public void setVariants(List<VariantGroups> variantGroups, HashMap<String, List<String>> exclusions) {
        this.exclusions = exclusions;
        this.variants.clear();
        this.variants.addAll(variantGroups);
        notifyDataSetChanged();
    }

    static class MainVH extends RecyclerView.ViewHolder {

        private final ListItemBinding binding;

        MainVH(ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}