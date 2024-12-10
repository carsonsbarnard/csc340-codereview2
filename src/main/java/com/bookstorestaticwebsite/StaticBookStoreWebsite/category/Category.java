package com.bookstorestaticwebsite.StaticBookStoreWebsite.category;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    @NonNull
    private String categoryName;

    public Category() {
    }

    public Category(int categoryId, @NonNull String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Category(@NonNull String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @NonNull
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(@NonNull String categoryName) {
        this.categoryName = categoryName;
    }
}
