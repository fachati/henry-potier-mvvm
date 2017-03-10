package com.fachati.hp;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fachati.hp.databinding.ItemBookBinding;
import com.fachati.hp.databinding.ItemBookInPriceBinding;
import com.fachati.hp.model.Book;
import com.fachati.hp.viewmodel.ItemBookInPriceViewModel;
import com.fachati.hp.viewmodel.ItemBookViewModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by fachati on 08/03/17.
 */

public class BookInPriceAdapter extends RecyclerView.Adapter<BookInPriceAdapter.BookInPriceViewHolder>{

    private List<Book> books;

    public BookInPriceAdapter() {
        this.books = Collections.emptyList();
    }

    public BookInPriceAdapter(List<Book> books) {
        this.books = books;
    }

    public void setRepositories(List<Book> books) {
        this.books = books;
    }

    @Override
    public BookInPriceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemBookInPriceBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_book_in_price,
                parent,
                false);
        return new BookInPriceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BookInPriceViewHolder holder, int position) {
        holder.bindBook(books.get(position));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class BookInPriceViewHolder extends RecyclerView.ViewHolder{

        final ItemBookInPriceBinding binding;

        public BookInPriceViewHolder(ItemBookInPriceBinding binding) {
            super(binding.cardView);
            this.binding = binding;
        }

        public void bindBook(Book book){
            if (binding.getVm() == null) {
                binding.setVm(new ItemBookInPriceViewModel(itemView.getContext(),book));
            } else {
                binding.getVm().setBook(book);
            }
        }
    }
}
