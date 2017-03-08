package com.fachati.hp.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.fachati.hp.R;
import com.fachati.hp.model.Book;
import com.squareup.picasso.Picasso;

/**
 * Created by fachati on 08/03/17.
 */

public class ItemBookViewModel extends BaseObservable{

    private Book book;
    private Context context;

    public ItemBookViewModel(Context context ,Book book) {
        this.book = book;
        this.context = context;
    }

    public void setBook(Book book) {
        this.book = book;
        notifyChange();
    }

    public String getTitle(){
        String title;
        int index=book.title.indexOf(' ',7);
        title=book.title.substring(0,index)+"\n"+book.title.substring(index,book.title.length());
        return title;
    }

    public String getSynopsis(){
        String synopsis=book.synopsis[0];
        for(int i=1;i<book.synopsis.length;i++)
            synopsis="\n"+book.synopsis[i];
        return synopsis;
    }

    public String getCover(){
        return book.cover;
    }

    public int getPrice(){
        return book.price;
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }
}
