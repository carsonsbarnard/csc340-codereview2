package com.bookstorestaticwebsite.StaticBookStoreWebsite.order;

import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.List;

@Entity
@Table(name="orderdetails")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailID orderDetailID = new OrderDetailID();

    @NonNull
    private int quantity;
    @NonNull
    private float subtotal;


    @ManyToOne
    @JoinColumn(name="bookId",insertable = false, updatable = false, nullable = false)
    private Book book;


    @ManyToOne
    @JoinColumn(name="bookOrderId", insertable = false, updatable = false,nullable = false)
    private BookOrder bookOrder;


    public BookOrder getBookOrder() {
        return bookOrder;
    }

    public void setBookOrder(BookOrder bookOrder) {
        this.bookOrder = bookOrder;
    }

    public OrderDetail() {
    }

    public OrderDetail(OrderDetailID orderDetailID) {
        this.orderDetailID = orderDetailID;
    }



    public OrderDetailID getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(OrderDetailID orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public float subtotalOfOrder(){
        return (float) (this.quantity * getBook().getPrice());
    }

}
