package com.blackbeard.dagger.demo.service;

import io.reactivex.Single;
import com.blackbeard.dagger.demo.model.ResponseWrapper;
import retrofit2.http.GET;

/**
 * Created by sudendra on 2/6/18.
 */

public interface RestApi {

    @GET("bins/121dau")
    Single<ResponseWrapper> variantCategories();
}
