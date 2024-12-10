package com.bookstorestaticwebsite.StaticBookStoreWebsite.book;

import com.bookstorestaticwebsite.StaticBookStoreWebsite.category.Category;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.review.Review;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name="book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
    @Column(unique = true)
    private String title;
    @NonNull
    private String author;
    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String description;
    @NonNull
    private String isbn;
    @NonNull
    @Lob
    private byte[] image;
    @NonNull
    private double price;
    @NonNull
    private Date publishDate;


    @NonNull
    String imagePath;

    private  Date lastUpdateDate;
    @ManyToOne
    @JoinColumn(name="categoryId", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Book() {
    }

    //create book contructor with and without id

    public Book(int bookId, String title, @NonNull String author, String description, @NonNull String isbn, float price, @NonNull Date publishDate, @NonNull String imagePath, Date lastUpdateDate, Category category) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.isbn = isbn;
        this.price = price;
        this.publishDate = publishDate;
        this.imagePath = imagePath;
        this.lastUpdateDate = lastUpdateDate;
        this.category = category;
    }

    public Book(String title, @NonNull String author, String description, @NonNull String isbn, float price, @NonNull Date publishDate, @NonNull String imagePath, Category category) {
        this(title, author, description, isbn, price, publishDate, imagePath, null, category);
    }

    public Book(String title, @NonNull String author, String description, @NonNull String isbn, float price, @NonNull Date publishDate, @NonNull String imagePath, Date lastUpdateDate, Category category) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.isbn = isbn;
        this.price = price;
        this.publishDate = publishDate;
        this.imagePath = imagePath;
        this.lastUpdateDate = lastUpdateDate;
        this.category = category;
    }





    @NonNull
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(@NonNull String imagePath) {
        this.imagePath = imagePath;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    public String getAuthor() {
        return author;
    }

    public void setAuthor(@NonNull String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(@NonNull String isbn) {
        this.isbn = isbn;
    }

    @NonNull
    public byte[] getImage() {
        return image;
    }

    public void setImage(@NonNull byte[] image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @NonNull
    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(@NonNull Date publishDate) {
        this.publishDate = publishDate;
    }


    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate( Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
