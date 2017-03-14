package com.fachati.hp.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.fachati.hp.Adapter.BookInPriceAdapter;
import com.fachati.hp.eventBus.Events;
import com.fachati.hp.Application;
import com.fachati.hp.R;
import com.fachati.hp.model.Book;
import com.fachati.hp.view.PriceActivity;
import com.squareup.picasso.Picasso;

public class ItemBookInPriceViewModel extends BaseObservable {

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

    public void onClickRemove(View view) {
        Application.selectedBook.remove(book);
        BookInPriceAdapter adapter =
                (BookInPriceAdapter) PriceActivity.binding.bookRecyclerView.getAdapter();
        adapter.setRepositories(Application.selectedBook);
        adapter.notifyDataSetChanged();

        Application.get(context).bus().send(new Events.ChangedListNotify());
    }




}
