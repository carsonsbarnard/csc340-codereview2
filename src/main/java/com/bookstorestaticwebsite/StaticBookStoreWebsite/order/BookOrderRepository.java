package com.bookstorestaticwebsite.StaticBookStoreWebsite.order;

import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.Book;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookOrderRepository extends JpaRepository<BookOrder, Integer> {

    //Get recent orders
    @Query("SELECT bo FROM BookOrder bo ORDER BY bo.orderDate DESC")
    List<BookOrder> findRecentOrders(Pageable pageable);

    @Query("SELECT COUNT(bo) FROM BookOrder bo")
    long countTotalOrders();

    List<BookOrder> findByCustomerCustomerId(int customerId);

    Optional<BookOrder> findByCustomer(Customer customer);

    List<BookOrder> findByCustomerAndStatus(Customer customer, String pending);

    Optional<BookOrder> findByCustomerAndBookAndStatus(Customer customer, Book book, String pending);

    List<BookOrder> findByCustomerAndStatusOrderByOrderDateDesc(Customer customer, String processing);
}
