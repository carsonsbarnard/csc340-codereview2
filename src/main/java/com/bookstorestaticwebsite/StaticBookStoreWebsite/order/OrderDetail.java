package com.bookstorestaticwebsite.StaticBookStoreWebsite.order;

import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.Book;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.Customer;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "orderdetails")
public class OrderDetail{

    @EmbeddedId
    private OrderDetailID orderDetailID = new OrderDetailID();

    @NonNull
    private int quantity;

    @NonNull
    private float subtotal;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customerId", referencedColumnName = "customerId", nullable = false, insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bookId", referencedColumnName = "bookId", nullable = false, insertable = false, updatable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "book_order_id", referencedColumnName = "book_order_id", insertable = false, updatable = false, nullable = false)
    private BookOrder bookOrder;

    // Getters and Setters
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
        this.orderDetailID.setBookId(book.getBookId()); // Set the bookId in the composite key
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.orderDetailID.setCustomerId(customer.getCustomerId()); // Set the customerId in the composite key
    }

    public BookOrder getBookOrder() {
        return bookOrder;
    }

    public void setBookOrder(BookOrder bookOrder) {
        this.bookOrder = bookOrder;
        this.orderDetailID.setBookOrderId(bookOrder.getBookOrderId()); // Set the bookOrderId in the composite key
    }

    // Additional method to calculate subtotal
    public float subtotalOfOrder() {
        return (float) (this.quantity * getBook().getPrice());
    }
}
