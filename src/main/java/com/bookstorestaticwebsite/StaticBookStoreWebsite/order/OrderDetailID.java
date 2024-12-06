package com.bookstorestaticwebsite.StaticBookStoreWebsite.order;

import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Embeddable
public class OrderDetailID {
   @Column(name="bookOrderId")
    private int bookOrderId;
    @Column(name="bookId")
    private int bookId;

    public OrderDetailID() {
    }

    public OrderDetailID(int bookOrderId, int bookId) {
    }


    public int getBookOrderId() {
        return bookOrderId;
    }

    public void setBookOrderId(int bookOrderId) {
        this.bookOrderId = bookOrderId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        OrderDetailID orderDetailID = (OrderDetailID) obj;
        return bookOrderId == orderDetailID.bookOrderId && bookId == orderDetailID.bookId;
    }

    @Override
    public int hashCode(){
        return Objects.hash(bookOrderId, bookId);
    }

}
