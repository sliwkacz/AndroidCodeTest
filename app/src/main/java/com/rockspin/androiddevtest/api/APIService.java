package com.rockspin.androiddevtest.api;

import com.rockspin.androiddevtest.api.model.CosmonautActivity;

import java.util.List;

import retrofit2.http.GET;
import io.reactivex.Observable;

public interface APIService {

    /**
     * Perform a call to fetch a list of Extra-Vehicular Cosmonaut Activity starting from 1965
     * @return The Retrofit Call
     */
    @GET("9kcy-zwvn.json?$order=date&$limit=20")
    Observable<List<CosmonautActivity>> getEVList();
}
