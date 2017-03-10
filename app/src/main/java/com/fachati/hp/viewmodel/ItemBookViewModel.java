package com.fachati.hp.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableChar;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.fachati.hp.HpApplication;
import com.fachati.hp.R;
import com.fachati.hp.model.Book;
import com.squareup.picasso.Picasso;

public class ItemBookViewModel extends BaseObservable{

    private Book book;
    private Context context;

    public ObservableInt synopsisVisibility;
    public ObservableInt synopsisColor;
    public ObservableField<String> synopsisTextButton;
    public ObservableField<String> buyTextButton;




    public ItemBookViewModel(Context context ,Book book) {
        this.book = book;
        this.context = context;
        this.synopsisVisibility = new ObservableInt(View.INVISIBLE);
        this.synopsisColor = new ObservableInt(Color.WHITE);
        this.synopsisTextButton = new ObservableField<>();
        this.buyTextButton = new ObservableField<>();

        this.synopsisTextButton.set(context.getString(R.string.text_button_synopsis_show));
        this.buyTextButton.set(context.getString(R.string.text_button_buy));

    }

    public void setBook(Book book) {
        this.book = book;
        notifyChange();
    }

    public String getTitle(){
        String title;
        int index=book.title.indexOf(' ',7);
        title=book.title.substring(0,index)+book.title.substring(index,book.title.length());
        return title;
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

    /*@BindingAdapter({"background"})
    public static void background(View view, int color) {
        view.setBackgroundColor(color);
        viewCard=view;
    }*/

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }

    public void onClickShowSynopsis(View view) {
        Log.e("click","onClickShowSynopsis");
        if(synopsisVisibility.get()==View.VISIBLE) {
            synopsisVisibility.set(View.INVISIBLE);
            synopsisTextButton.set(context.getString(R.string.text_button_synopsis_show));

        }else {
            synopsisVisibility.set(View.VISIBLE);
            synopsisTextButton.set(context.getString(R.string.text_button_synopsis_hide));
        }

    }

    public void onClickBuy(View view) {
        Log.e("click","onClickBuy");

        if(HpApplication.selectedBook.contains(book)) {
            HpApplication.selectedBook.remove(book);
            this.synopsisColor.set(Color.WHITE);
            this.buyTextButton.set(context.getString(R.string.text_button_buy));
        }else {
            HpApplication.selectedBook.add(book);
            this.buyTextButton.set(context.getString(R.string.text_button_buy_cancel));
            this.synopsisColor.set(context.getColor(R.color.colorGreen));
        }

        for(int i=0;i<HpApplication.selectedBook.size();i++){
            Log.e("tag"+i,HpApplication.selectedBook.get(i).toString());
        }
    }





}
