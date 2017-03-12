package com.fachati.hp.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fachati.hp.HpApplication;
import com.fachati.hp.model.Book;
import com.fachati.hp.model.HpService;
import com.fachati.hp.model.OfferEnum;
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

    public ObservableField<String> synopsisTextInitialPrice;
    public ObservableField<String> minusTextInitialPrice;
    public ObservableField<String> sliceTextInitialPrice;
    public ObservableField<String> percentageTextInitialPrice;
    public ObservableField<String> lastPriceTextInitialPrice;

    public ObservableInt sliceOfferVisibility;
    public ObservableInt minusOfferVisibility;
    public ObservableInt percentageOfferVisibility;

    public PriceActivityViewModel(Context context,DataListenerPrice dataListener){
        this.context = context;
        this.dataListener = dataListener;
        this.sliceOfferVisibility=new ObservableInt(View.INVISIBLE);
        this.minusOfferVisibility=new ObservableInt(View.INVISIBLE);
        this.percentageOfferVisibility=new ObservableInt(View.INVISIBLE);

        this.minusTextInitialPrice = new ObservableField<>();
        this.sliceTextInitialPrice = new ObservableField<>();
        this.percentageTextInitialPrice = new ObservableField<>();
        this.lastPriceTextInitialPrice = new ObservableField<>();

        this.synopsisTextInitialPrice = new ObservableField<>();
        this.synopsisTextInitialPrice.set(getTotalPrice()+" €");
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


                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "Error loading GitHub repos ", error);
                    }

                    @Override
                    public void onNext(Offers offers) {
                        Log.i(TAG, "offers " + offers.toString());
                        PriceActivityViewModel.this.offers = offers;
                        offersApply();
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

    public void offersApply(){

        int lastPrice=getTotalPrice();
        for(int i=0;i<this.offers.getOffers().length;i++){
            if(offers.getOffers()[i].getType().compareTo(OfferEnum.MINUS.getText())==0){
                this.minusOfferVisibility.set(View.VISIBLE);
                this.minusTextInitialPrice.set(offers.getOffers()[i].getValue()+" €");
                lastPrice=lastPrice-offers.getOffers()[i].getValue();
            }else if(offers.getOffers()[i].getType().compareTo(OfferEnum.SLICE.getText())==0
                    && (getTotalPrice() >= offers.getOffers()[i].getSliceValue())){
                this.sliceOfferVisibility.set(View.VISIBLE);
                this.sliceTextInitialPrice.set(offers.getOffers()[i].getValue()+" €");
                lastPrice=lastPrice-offers.getOffers()[i].getValue();
            }else if(offers.getOffers()[i].getType().compareTo(OfferEnum.PERCENTAGE.getText())==0){
                this.percentageOfferVisibility.set(View.VISIBLE);
                this.percentageTextInitialPrice.set(offers.getOffers()[i].getValue()+" €");
                lastPrice=lastPrice-offers.getOffers()[i].getValue();
            }
            lastPriceTextInitialPrice.set(lastPrice+" €");

        }
    }

    public int getTotalPrice(){
        int totalPrice=0;

        for(int i=0;i<HpApplication.selectedBook.size();i++){
            totalPrice=totalPrice+HpApplication.selectedBook.get(i).getPrice();
        }
        return totalPrice;
    }







}
