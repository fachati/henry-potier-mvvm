package com.fachati.hp.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.fachati.hp.EndlessRecyclerOnScrollListener;
import com.fachati.hp.HpApplication;
import com.fachati.hp.R;
import com.fachati.hp.model.Book;
import com.fachati.hp.model.HpService;
import com.fachati.hp.view.PriceActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by fachati on 08/03/17.
 */

public class BookActivityViewModel implements ViewModel{

    private static final String TAG = "BookActivityViewModel";

    private Context context;
    private Subscription subscription;
    private List<Book> books;
    private DataListener dataListener;


    public BookActivityViewModel(Context context,DataListener dataListener){
        this.context = context;
        this.dataListener = dataListener;

        loadBooks();
    }

    @Override
    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        subscription = null;
        context = null;
    }

    private void loadBooks() {
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        HpApplication application = HpApplication.get(context);
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
                        Log.e(TAG, "Error loading GitHub repos ", error);
                    }

                    @Override
                    public void onNext(List<Book> books) {
                        for(int i=0;i<books.size();i++){
                            Log.i(TAG, "Repos loaded " + books.get(i).toString());
                        }
                        BookActivityViewModel.this.books = books;
                    }
                });
    }

    public interface DataListener {
        void applyBooksInRecycleView(List<Book> books);
    }

    public void onclickPurchase(View view) {
        Log.e("click","onclickPurchase");
        context.startActivity(new Intent(context, PriceActivity.class));

    }


}
