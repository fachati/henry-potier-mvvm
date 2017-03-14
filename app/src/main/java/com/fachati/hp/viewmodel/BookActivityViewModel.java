package com.fachati.hp.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fachati.hp.eventBus.Events;
import com.fachati.hp.Application;
import com.fachati.hp.R;
import com.fachati.hp.model.Book;
import com.fachati.hp.model.HpService;
import com.fachati.hp.view.PriceActivity;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by fachati on 08/03/17.
 */

public class BookActivityViewModel implements ViewModel{

    private static final String TAG = "BookActivityViewModel";

    private Context context;
    private Subscription subscription;
    private List<Book> books;
    private DataListener dataListener;

    public ObservableInt errorVisibility;
    public ObservableInt buttonPurchaseVisibility;

    public ObservableInt synopsisVisibility;
    public ObservableField<String> synopsisText;

    private Subscription busSubscription;


    public BookActivityViewModel(Context context,DataListener dataListener){
        this.context = context;
        this.dataListener = dataListener;
        this.errorVisibility = new ObservableInt(View.INVISIBLE);
        this.buttonPurchaseVisibility = new ObservableInt(View.INVISIBLE);
        this.synopsisVisibility = new ObservableInt(View.INVISIBLE);

        this.synopsisText = new ObservableField<>();

        TextView textView = (TextView) ((Activity) context).findViewById(R.id.text5);
        textView.setMovementMethod(new ScrollingMovementMethod());
        busSubscription = Application.get().bus().toObserverable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<Object>() {
                            @Override
                            public void call(Object o) {
                                handlerBus(o);
                            }
                        }
                );

        loadBooks();
    }

    private void handlerBus(Object o) {
        if (o instanceof Events.ScrollDirection) {
            if(((Events.ScrollDirection) o).direction==0)
                buttonPurchaseVisibility.set(View.INVISIBLE);

            else if(((Events.ScrollDirection) o).direction==1)
                buttonPurchaseVisibility.set(View.VISIBLE);

        }else if (o instanceof Events.ShowDialogSynopsis) {
            this.synopsisVisibility.set(View.VISIBLE);
            this.synopsisText.set(((Events.ShowDialogSynopsis) o).synopsis);

        }
    }

    @Override
    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        subscription = null;
        context = null;
    }

    @Override
    public void resume() {
        Application.get().bus().send(new Events.UpdateListBookOnResume());
    }

    private void loadBooks() {
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        Application application = Application.get(context);
        HpService service = application.getService();
        subscription = service.booksList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<Book>>() {
                    @Override
                    public void onCompleted() {
                        if (dataListener != null) dataListener.applyBooksInRecycleView(books);
                    }

                    @Override
                    public void onError(Throwable error) {
                        errorVisibility.set(View.VISIBLE);
                        buttonPurchaseVisibility.set(View.INVISIBLE);
                    }

                    @Override
                    public void onNext(List<Book> books) {
                        for(int i=0;i<books.size();i++){
                            Log.i(TAG, books.get(i).toString());
                        }
                        buttonPurchaseVisibility.set(View.VISIBLE);
                        BookActivityViewModel.this.books = books;
                    }
                });
    }

    public interface DataListener {
        void applyBooksInRecycleView(List<Book> books);
    }

    public void onclickPurchase(View view) {
        context.startActivity(new Intent(context, PriceActivity.class));

    }

    public void onclickClose(View view) {
        this.synopsisVisibility.set(View.INVISIBLE);

    }



}
