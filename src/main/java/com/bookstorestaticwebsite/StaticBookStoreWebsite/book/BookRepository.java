package com.bookstorestaticwebsite.StaticBookStoreWebsite.book;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByCategory_CategoryName(String categoryName);
    //return first top 3 books and all reviews and customers
//     @Query(value= "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.reviews r LEFT JOIN FETCH r.customer c ORDER BY r.dateReview DESC")
//     List<Book> findTop3BooksByReviewDate(Pageable pageable);

    @Query(value = "SELECT b FROM Book b LEFT JOIN FETCH b.reviews r WHERE r.dateReview = (SELECT MAX(r2.dateReview) FROM Review r2 WHERE r2.book = b) ORDER BY r.dateReview DESC")
    List<Book> findTopBooksByLatestReview(Pageable pageable);

    @Query("SELECT COUNT(b) FROM Book b")
    long countTotalBooks();

    // Custom query method to search books by title
    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findAllByOrderByBookIdDesc();  // Correct method to fetch books sorted by bookId in descending order.

    // Query to get the most popular books by counting the occurrences of book_id in bookorders
    @Query(value = "SELECT b.* FROM book b " +
            "JOIN bookorders bo ON b.book_id = bo.book_id " +
            "GROUP BY b.book_id ORDER BY COUNT(bo.book_id) DESC", nativeQuery = true)
    List<Book> findMostPopularBooks();

    List<Book> findByCategory_CategoryId(int categoryId);

}
