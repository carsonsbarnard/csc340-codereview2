package com.bookstorestaticwebsite.StaticBookStoreWebsite.order;

import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.Book;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailID> {
    @Query("SELECT od FROM OrderDetail od WHERE od.bookOrder.bookOrderId = :bookOrderId")
    List<OrderDetail> findByBookOrderBookOrderId(@Param("bookOrderId") int bookOrderId);

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderDetail od WHERE od.orderDetailID.bookOrderId = :bookOrderId AND od.orderDetailID.bookId = :bookId")
    void deleteByBookOrderIdAndBookId(@Param("bookOrderId") int bookOrderId, @Param("bookId") int bookId);

    //Delete order detail
    @Modifying
    @Transactional
    @Query("DELETE FROM OrderDetail od WHERE od.orderDetailID.bookOrderId = :bookOrderId")
    void deleteByBookOrderId(@Param("bookOrderId") int bookOrderId);

    Optional<OrderDetail> findByCustomerAndBook(Customer customer, Book book);

    List<OrderDetail> findByBookOrder_Customer(Customer customer);

    List<OrderDetail> findByBookOrder(BookOrder bookOrder);

    Optional<OrderDetail> findByBookOrderAndBook(BookOrder bookOrder, Book book);

    List<OrderDetail> findByCustomer(Customer customer);
}
