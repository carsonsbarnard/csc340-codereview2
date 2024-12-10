package com.bookstorestaticwebsite.StaticBookStoreWebsite.review;

import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review , Integer> {
    List<Review> findByBook(Book book);

    @Query("SELECT COUNT(r) FROM Review r")
    long countTotalReviews();
}
