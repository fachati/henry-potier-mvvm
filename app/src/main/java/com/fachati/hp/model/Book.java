package com.fachati.hp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by fachati on 08/03/17.
 */

public class Book implements Parcelable {

    public String isbn;
    public String title;
    public int price;
    public String cover;
    public String[] synopsis;

    public Book(){

    }

    protected Book(Parcel in) {
        isbn = in.readString();
        title = in.readString();
        price = in.readInt();
        cover = in.readString();
        synopsis = in.createStringArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(isbn);
        dest.writeString(title);
        dest.writeInt(price);
        dest.writeString(cover);
        dest.writeStringArray(synopsis);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (price != book.price) return false;
        if (!isbn.equals(book.isbn)) return false;
        if (!title.equals(book.title)) return false;
        if (!cover.equals(book.cover)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(synopsis, book.synopsis);

    }

    @Override
    public int hashCode() {
        int result = isbn.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + price;
        result = 31 * result + cover.hashCode();
        result = 31 * result + Arrays.hashCode(synopsis);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", cover='" + cover + '\'' +
                ", synopsis=" + Arrays.toString(synopsis) +
                '}';
    }
}
