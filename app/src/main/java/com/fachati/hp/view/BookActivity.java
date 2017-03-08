package com.fachati.hp.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fachati.hp.BookAdapter;
import com.fachati.hp.R;
import com.fachati.hp.databinding.ActivityBookBinding;
import com.fachati.hp.model.Book;
import com.fachati.hp.viewmodel.BookActivityViewModel;

import java.util.List;


public class BookActivity extends AppCompatActivity implements BookActivityViewModel.DataListener{

    private String TAG="BookActivity";
    private ActivityBookBinding binding;
    private BookActivityViewModel bookActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_book);
        bookActivityViewModel = new BookActivityViewModel(this,this);
        binding.setVm(bookActivityViewModel);
        setupRecyclerView(binding.bookRecyclerView);

    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        BookAdapter adapter = new BookAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void applyBooksInRecycleView(List<Book> books) {
        BookAdapter adapter =
                (BookAdapter) binding.bookRecyclerView.getAdapter();
        adapter.setRepositories(books);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bookActivityViewModel.destroy();
    }
}
