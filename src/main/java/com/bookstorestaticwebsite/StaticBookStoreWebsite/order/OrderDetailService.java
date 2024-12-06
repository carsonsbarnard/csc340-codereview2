package com.bookstorestaticwebsite.StaticBookStoreWebsite.order;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> getAllOrderDetail(){return orderDetailRepository.findAll();}

    public OrderDetail getOrderDetailById(OrderDetailID orderDetailID){
        return orderDetailRepository.findById(orderDetailID).orElse(null);
    }
    @Transactional
    public void deleteOrderDetail(int bookOrderId){
        orderDetailRepository.deleteByBookOrderId(bookOrderId);
    }
    @Transactional
    public void removeBookFromOrder(int bookOrderId, int bookId){
        orderDetailRepository.deleteByBookOrderIdAndBookId(bookOrderId,bookId );
    }



//    @Transactional
//    public void removeBookFromOrder(int bookOrderId, int bookId){
//        OrderDetailID orderDetailID = new OrderDetailID(bookOrderId, bookId);
//        orderDetailRepository.deleteById(orderDetailID);
//        System.out.println("Deleted");
//    }

//    //For API
//    public void createOrder(OrderDetail order){
//        orderDetailRepository.save(order);
//    }








}
