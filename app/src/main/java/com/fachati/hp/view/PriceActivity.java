package com.fachati.hp.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fachati.hp.Adapter.BookInPriceAdapter;
import com.fachati.hp.R;
import com.fachati.hp.databinding.ActivityPriceBinding;
import com.fachati.hp.model.Book;
import com.fachati.hp.viewmodel.PriceActivityViewModel;

import java.util.List;


public class PriceActivity extends AppCompatActivity implements PriceActivityViewModel.DataListenerPrice{

    public static ActivityPriceBinding binding;
    private PriceActivityViewModel priceActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_price);
        priceActivityViewModel = new PriceActivityViewModel(this,this);
        binding.setVm(priceActivityViewModel);
        setupRecyclerView(binding.bookRecyclerView);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.actionbar_space_icon_price);
        getSupportActionBar().setTitle(getString(R.string.titleActivityPrice));

    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        BookInPriceAdapter adapter = new BookInPriceAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void applyBooksInRecycleView(List<Book> books) {
        BookInPriceAdapter adapter =
                (BookInPriceAdapter) binding.bookRecyclerView.getAdapter();
        adapter.setRepositories(books);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        priceActivityViewModel.destroy();
    }


}
