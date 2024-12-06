package com.bookstorestaticwebsite.StaticBookStoreWebsite.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookOrderService {
    @Autowired
    private BookOrderRepository bookOrderRepository;


    public List<BookOrder> getAllBookOrder(){return bookOrderRepository.findAll();}

    public BookOrder getBookOrderById(int id){
        return bookOrderRepository.findById(id).orElse(null);
    }

    public List<BookOrder> getRecentOrders(){
        Pageable pageable = PageRequest.of(0, 5);
        return bookOrderRepository.findRecentOrders(pageable);
    }

    public long getTotalOrders(){
        return bookOrderRepository.countTotalOrders();
    }

    public void deleteOrder(int oId){
        bookOrderRepository.deleteById(oId);
    }






}