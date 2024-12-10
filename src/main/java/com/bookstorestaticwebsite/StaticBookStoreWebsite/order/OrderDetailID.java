package com.bookstorestaticwebsite.StaticBookStoreWebsite.order;

import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Embeddable
public class OrderDetailID {
   @Column(name="book_order_id")
    private int bookOrderId;
    @Column(name="bookId")
    private int bookId;
    @Column(name="customerId")
    private int customerId;

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

    public int getCustomerId(){ return customerId; }

    public void setCustomerId(int customerId){ this.customerId = customerId; }

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
