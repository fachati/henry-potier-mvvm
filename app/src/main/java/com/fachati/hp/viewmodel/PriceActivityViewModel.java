package com.fachati.hp.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fachati.hp.HpApplication;
import com.fachati.hp.model.Book;
import com.fachati.hp.model.HpService;
import com.fachati.hp.model.Offers;
import com.fachati.hp.view.PriceActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by fachati on 08/03/17.
 */

public class PriceActivityViewModel implements ViewModel{

    private static final String TAG = "PriceActivityViewModel";

    private Context context;
    private Subscription subscription;
    private Offers offers;
    private DataListenerPrice dataListener;
    private DataListenerOffers dataListenerOffers;

    public String test="";
    public PriceActivityViewModel(Context context,DataListenerPrice dataListener){
        this.context = context;
        this.dataListener = dataListener;
        loadOffers();
    }

    @Override
    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        subscription = null;
        context = null;
    }

    private void loadOffers() {
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        HpApplication application = HpApplication.get(context);
        HpService service = application.getService();
        subscription = service.commercialOffer(getIsbnSelectedBooks())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<Offers>() {
                    @Override
                    public void onCompleted() {
                        if (dataListener != null) dataListener.applyBooksInRecycleView(HpApplication.selectedBook);
                        if (dataListenerOffers != null) dataListenerOffers.applyOffresInRecycleView(offers);

                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "Error loading GitHub repos ", error);
                    }

                    @Override
                    public void onNext(Offers offers) {
                        Log.i(TAG, "offers " + offers.toString());
                        PriceActivityViewModel.this.offers = offers;
                    }
                });
    }


    public String getIsbnSelectedBooks(){
        String isbnParams = "";
        if(HpApplication.selectedBook.size()>0){
            if(HpApplication.selectedBook.size()==1)
                isbnParams=HpApplication.selectedBook.get(0).getIsbn();
            else{
                isbnParams=HpApplication.selectedBook.get(0).getIsbn();
                for(int i=1;i<HpApplication.selectedBook.size();i++)
                    isbnParams=isbnParams+","+HpApplication.selectedBook.get(i).getIsbn();
            }
            Log.e(TAG,isbnParams);
        }

        return isbnParams;
    }


    public interface DataListenerPrice {
        void applyBooksInRecycleView(List<Book> books);
    }

    public interface DataListenerOffers {
        void applyOffresInRecycleView(Offers offers);
    }





}
