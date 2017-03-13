package com.fachati.hp.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.fachati.hp.Events;
import com.fachati.hp.Application;
import com.fachati.hp.R;
import com.fachati.hp.model.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ItemBookViewModel extends BaseObservable{

    private Book book;
    private Context context;

    public ObservableInt synopsisVisibility;
    public ObservableInt synopsisColor;
    public ObservableField<String> synopsisTextButton;
    public ObservableField<String> buyTextButton;

    private Subscription busSubscription;




    public ItemBookViewModel(Context context ,Book book) {
        this.book = book;
        this.context = context;
        this.synopsisVisibility = new ObservableInt(View.INVISIBLE);
        this.synopsisColor = new ObservableInt(Color.WHITE);
        this.synopsisTextButton = new ObservableField<>();
        this.buyTextButton = new ObservableField<>();

        this.synopsisTextButton.set(context.getString(R.string.text_button_synopsis_show));
        this.buyTextButton.set(context.getString(R.string.text_button_buy));

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

    }

    private void handlerBus(Object o) {
        if (o instanceof Events.UpdateListBookOnResume) {

            List<Book> listSelectedBook= Application.selectedBook;
            if(!listSelectedBook.contains(book)){
                this.synopsisVisibility.set(View.INVISIBLE);
                this.synopsisColor.set(Color.WHITE);
                this.synopsisTextButton.set(context.getString(R.string.text_button_synopsis_show));
                this.buyTextButton.set(context.getString(R.string.text_button_buy));
            }
        }
    }

    public void setBook(Book book) {
        this.book = book;
        notifyChange();
    }

    public String getTitle(){
        return book.title;
    }

    public String getSynopsis(){
        String synopsis="- "+book.synopsis[0];
        for(int i=1;i<book.synopsis.length;i++)
            synopsis=synopsis+"\n- "+book.synopsis[i];
        return synopsis;
    }

    public String getCover(){
        return book.cover;
    }

    public String getPrice(){
        return context.getString(R.string.text_price, book.price);
    }


    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }

    public void onClickShowSynopsis(View view) {
        if(synopsisVisibility.get()==View.VISIBLE) {
            synopsisVisibility.set(View.INVISIBLE);
            synopsisTextButton.set(context.getString(R.string.text_button_synopsis_show));

        }else {
            synopsisVisibility.set(View.VISIBLE);
            synopsisTextButton.set(context.getString(R.string.text_button_synopsis_hide));
        }

    }

    public void onClickBuy(View view) {
        if(Application.selectedBook.contains(book)) {
            Application.selectedBook.remove(book);
            this.synopsisColor.set(Color.WHITE);
            this.buyTextButton.set(context.getString(R.string.text_button_buy));
        }else {
            Application.selectedBook.add(book);
            this.buyTextButton.set(context.getString(R.string.text_button_buy_cancel));
            this.synopsisColor.set(context.getColor(R.color.colorGreen));
        }

        for(int i = 0; i< Application.selectedBook.size(); i++){
            Log.e("tag"+i, Application.selectedBook.get(i).toString());
        }
    }


}
