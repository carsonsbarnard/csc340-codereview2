package com.bookstorestaticwebsite.StaticBookStoreWebsite.review;

import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.Book;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.Customer;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name="reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    private int rating;
    private String headline;
    private String comment;
    private Date dateReview;

    @ManyToOne
    @JoinColumn(name="bookId", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name="customerId", nullable=false)
    private Customer customer;

    public Review() {
    }

    public Review(int reviewId, int rating, String headline, String comment, Date dateReview, Book book, Customer customer) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.headline = headline;
        this.comment = comment;
        this.dateReview = dateReview;
        this.book = book;
        this.customer = customer;
    }

    public Review(int rating, String headline, String comment, Date dateReview, Book book, Customer customer) {
        this.rating = rating;
        this.headline = headline;
        this.comment = comment;
        this.dateReview = dateReview;
        this.book = book;
        this.customer = customer;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDateReview() {
        return dateReview;
    }

    public void setDateReview(Date dateReview) {
        this.dateReview = dateReview;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
