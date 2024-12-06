package com.bookstorestaticwebsite.StaticBookStoreWebsite.order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookOrderRepository extends JpaRepository<BookOrder, Integer> {

    //Get recent orders
    @Query("SELECT bo FROM BookOrder bo ORDER BY bo.orderDate DESC")
    List<BookOrder> findRecentOrders(Pageable pageable);

    @Query("SELECT COUNT(bo) FROM BookOrder bo")
    long countTotalOrders();

    List<BookOrder> findByCustomerCustomerId(int customerId);
}
