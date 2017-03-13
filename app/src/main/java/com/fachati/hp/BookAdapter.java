package com.fachati.hp;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fachati.hp.databinding.ItemBookBinding;
import com.fachati.hp.model.Book;
import com.fachati.hp.viewmodel.ItemBookViewModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by fachati on 08/03/17.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{

    private List<Book> books;

    public BookAdapter() {
        this.books = Collections.emptyList();
    }

    public BookAdapter(List<Book> books) {
        this.books = books;
    }

    public void setRepositories(List<Book> books) {
        this.books = books;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemBookBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_book,
                parent,
                false);
        return new BookViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.bindBook(books.get(position));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder{

        final ItemBookBinding binding;

        public BookViewHolder(ItemBookBinding binding) {
            super(binding.cardView);
            this.binding = binding;
        }

        public void bindBook(Book book){
            if (binding.getVm() == null) {
                binding.setVm(new ItemBookViewModel(itemView.getContext(),book));
            } else {
                binding.getVm().setBook(book);
            }
        }
    }
}
