package com.bookstorestaticwebsite.StaticBookStoreWebsite.book;

import com.bookstorestaticwebsite.StaticBookStoreWebsite.admin.User;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.review.Review;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;
import java.util.Base64;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public void createNewBook(Book book){
        //check if this title already exist or not
        if(checkTitleExist(book)){
            System.out.println("Already exist");
            return;
        }
        String imagePath = "/images/books/"+book.getImagePath();
        book = new Book(book.getTitle(), book.getAuthor(), book.getDescription(), book.getIsbn(), (float) book.getPrice(),book.getPublishDate(), imagePath,null, book.getCategory());
        bookRepository.save(book);
    }

    public Book getBookById(int id){
        return bookRepository.findById(id).orElse(null);
    }

    public void updateBook(Book book, int id){
        Book existing = getBookById(id);
        String imagePath = "/images/books/"+book.getImagePath();
           existing.setTitle(book.getTitle());
           existing.setAuthor(book.getAuthor());
           existing.setCategory(book.getCategory());
           existing.setDescription(book.getDescription());
           existing.setIsbn(book.getIsbn());
           existing.setImagePath(imagePath);
           existing.setPrice(book.getPrice());
           existing.setPublishDate(book.getPublishDate());
           existing.setLastUpdateDate(book.getLastUpdateDate());
        bookRepository.save(existing);

    }

    public void deleteBookById(int id){
        bookRepository.deleteById(id);
    }

    public List<Book> findAllBooksSortedByIdDesc() {
        return bookRepository.findAll(Sort.by(Sort.Direction.DESC, "book_id"));
    }

    public boolean checkTitleExist(Book book){
        boolean existed = false;
        List<Book> list_books = getAllBooks();
        for (Book b : list_books){
            if(book.getTitle().equals(b.getTitle())){
                existed = true;
                break;
            }
        }
        return existed;
    }
    public List<Book> findBookByCategoryName(String categoryName){
        return bookRepository.findByCategory_CategoryName(categoryName);
    }

    //List of review for a book
    public List<Review> getReviewsForBook(int bId){
        Book book = getBookById(bId);
        return reviewRepository.findByBook(book);
    }

    // get first 3 books with all reviews
//    public List<Book> getFirst3Books(){
//        Pageable pageable = PageRequest.of(0, 3);
//        List<Book> list_3_books = bookRepository.findTop3BooksByReviewDate(pageable);
//
//        return list_3_books;
//    }


    public List<Book> getLastestReviewBooks(){
        Pageable pageable = PageRequest.of(0, 5);
        List<Book> list_books = bookRepository.findTopBooksByLatestReview(pageable);
        return list_books;
    }

    public long getTotalBooks(){
        return bookRepository.countTotalBooks();
    }

    // Method to search books by title
    public List<Book> searchBooksByTitle(String title) {
        // Use the repository method to search for books by title
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
}
