package com.fachati.hp.model;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by fachati on 08/03/17.
 */

public interface HpService {

    @GET("books")
    Observable<List<Book>> booksList();

    @GET("books/{isbn}/commercialOffers")
    Observable<Offers> commercialOffer(@Path("isbn") String userUrl);


    class Factory {
        public static HpService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://henri-potier.xebia.fr/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(HpService.class);
        }
    }
}
