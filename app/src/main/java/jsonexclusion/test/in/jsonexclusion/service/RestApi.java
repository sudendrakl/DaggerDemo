package jsonexclusion.test.in.jsonexclusion.service;

import io.reactivex.Single;
import jsonexclusion.test.in.jsonexclusion.model.ResponseWrapper;
import retrofit2.http.GET;

/**
 * Created by sudendra on 2/6/18.
 */

public interface RestApi {

    @GET("bins/121dau")
    Single<ResponseWrapper> variantCategories();
}
