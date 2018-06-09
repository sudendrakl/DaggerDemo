package com.blackbeard.dagger.demo.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import com.blackbeard.dagger.demo.model.ExcludeItem;
import com.blackbeard.dagger.demo.model.ResponseWrapper;
import com.blackbeard.dagger.demo.model.Variants;
import com.blackbeard.dagger.demo.service.ApiServiceBuilder;

/**
 * Created by sudendra on 2/6/18.
 */

public class MainVM extends ViewModel {

    private final MutableLiveData<Variants> variantsLD;
    private final MutableLiveData<String> error;
    private final MutableLiveData<Boolean> progress;
    private final PublishSubject<Pair<String, Boolean>> groupIdObserver;
    private final HashMap<String, Boolean> selections;
    private final MutableLiveData<Boolean> choiceObserver;

    private final HashMap<String, List<String>> exclusions;

    public MainVM() {
        variantsLD = new MutableLiveData<>();
        error = new MutableLiveData<>();
        progress = new MutableLiveData<>();
        groupIdObserver = PublishSubject.create();
        selections = new HashMap<>(5);
        choiceObserver = new MutableLiveData<>();
        choiceObserver.setValue(false);
        groupIdObserver
                .doOnNext(pair -> Log.d("selection", "selected -> " + (pair.first + ":" + pair.second)))
                .map(pair -> {
                    selections.put(pair.first, pair.second);
                    return pair;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ignore -> {
                    choiceObserver.setValue(!choiceObserver.getValue());
                });
        exclusions = new HashMap<>();
    }

    public void fetchApi(Context context) {
        progress.setValue(true);
        ApiServiceBuilder.provideAPIService(context)
                .variantCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::response, this::error);
    }

    private void error(Throwable throwable) {
        Log.e("error", "msg", throwable);
        progress.setValue(false);
        error.setValue("failed to load api ... " + throwable.getMessage());
    }

    private void response(ResponseWrapper responseWrapper) {
        for (List<ExcludeItem> subList : responseWrapper.variants().excludeList()) {
            for (ExcludeItem item : subList) {
                List<String> varExcList = exclusions.get(item.variationId());
                if (varExcList == null) {
                    varExcList = new ArrayList<>();
                } else {
                    varExcList.add(item.variationId());
                }
                exclusions.put(item.variationId(), varExcList);
            }
        }
        progress.setValue(false);
        if (responseWrapper.variants() != null) {
            variantsLD.setValue(responseWrapper.variants());
        } else {
            error.setValue("no data");
        }
    }

    public MutableLiveData<Variants> getVariantsLD() {
        return variantsLD;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getProgress() {
        return progress;
    }

    public PublishSubject<Pair<String, Boolean>> selectionObserver() {
        return groupIdObserver;
    }

    public MutableLiveData<Boolean> getChoiceObserver() {
        return choiceObserver;
    }

    public HashMap<String, Boolean> getSelections() {
        return selections;
    }

    public HashMap<String, List<String>> getExclusions() {
        return exclusions;
    }
}
