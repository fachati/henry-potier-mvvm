package com.fachati.hp.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;


import com.fachati.hp.Events;
import com.fachati.hp.Application;
import com.fachati.hp.model.Book;
import com.fachati.hp.model.HpService;
import com.fachati.hp.model.OfferEnum;
import com.fachati.hp.model.Offers;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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

    private Subscription busSubscription;


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


        busSubscription = Application.get(context).bus().toObserverable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<Object>() {
                            @Override
                            public void call(Object o) {
                                handlerBus(o);
                            }
                        }
                );

    }

    @Override
    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        subscription = null;
        context = null;
    }

    @Override
    public void resume() {

    }

    private void loadOffers() {
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        Application application = Application.get();
        HpService service = application.getService();
        subscription = service.commercialOffer(getIsbnSelectedBooks())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<Offers>() {
                    @Override
                    public void onCompleted() {
                        if (dataListener != null) dataListener.applyBooksInRecycleView(Application.selectedBook);


                    }

                    @Override
                    public void onError(Throwable error) {
                        minusOfferVisibility.set(View.INVISIBLE);
                        sliceOfferVisibility.set(View.INVISIBLE);
                        percentageOfferVisibility.set(View.INVISIBLE);
                        lastPriceTextInitialPrice.set(00+" €");
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
        if(Application.selectedBook.size()>0){
            if(Application.selectedBook.size()==1)
                isbnParams= Application.selectedBook.get(0).getIsbn();
            else{
                isbnParams= Application.selectedBook.get(0).getIsbn();
                for(int i = 1; i< Application.selectedBook.size(); i++)
                    isbnParams=isbnParams+","+ Application.selectedBook.get(i).getIsbn();
            }
            Log.e(TAG,isbnParams);
        }
        return isbnParams;
    }




    public interface DataListenerPrice {
        void applyBooksInRecycleView(List<Book> books);
    }

    public void offersApply(){

        minusOfferVisibility.set(View.INVISIBLE);
        sliceOfferVisibility.set(View.INVISIBLE);
        percentageOfferVisibility.set(View.INVISIBLE);

        int lastPrice=getTotalPrice();
        for(int i=0;i<this.offers.getOffers().length;i++){
            if(offers.getOffers()[i].getType().compareTo(OfferEnum.MINUS.getText())==0){
                minusTextInitialPrice.set(offers.getOffers()[i].getValue()+" €");
                minusOfferVisibility.set(View.VISIBLE);
                lastPrice=lastPrice-offers.getOffers()[i].getValue();

            }else if(offers.getOffers()[i].getType().compareTo(OfferEnum.SLICE.getText())==0
                    && (getTotalPrice() >= offers.getOffers()[i].getSliceValue())){
                sliceTextInitialPrice.set(offers.getOffers()[i].getValue()+" €");
                sliceOfferVisibility.set(View.VISIBLE);
                lastPrice=lastPrice-offers.getOffers()[i].getValue();

            }else if(offers.getOffers()[i].getType().compareTo(OfferEnum.PERCENTAGE.getText())==0){
                percentageTextInitialPrice.set(offers.getOffers()[i].getValue()+" €");
                percentageOfferVisibility.set(View.VISIBLE);
                lastPrice=lastPrice-offers.getOffers()[i].getValue();
            }
            lastPriceTextInitialPrice.set(lastPrice+" €");
        }
    }

    public int getTotalPrice(){
        int totalPrice=0;

        for(int i = 0; i< Application.selectedBook.size(); i++){
            totalPrice=totalPrice+ Application.selectedBook.get(i).getPrice();
        }
        return totalPrice;
    }


    private void handlerBus(Object o) {
        if (o instanceof Events.ChangedListNotify) {
            synopsisTextInitialPrice.set(getTotalPrice()+" €");
            loadOffers();
        }
    }


}
