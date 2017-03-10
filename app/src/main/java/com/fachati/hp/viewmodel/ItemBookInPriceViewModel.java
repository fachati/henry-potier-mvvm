package com.fachati.hp.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.fachati.hp.HpApplication;
import com.fachati.hp.R;
import com.fachati.hp.model.Book;
import com.fachati.hp.view.PriceActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemBookInPriceViewModel extends BaseObservable{

    private Book book;
    private Context context;


    public ItemBookInPriceViewModel(Context context , Book book) {
        this.book = book;
        this.context = context;

    }

    public void setBook(Book book) {
        this.book = book;
        notifyChange();
    }

    public String getTitle(){
        return book.title;
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

    public interface DataListener {
        void applyBooksInRecycleView(List<Book> books);
    }


}
