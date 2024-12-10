package com.bookstorestaticwebsite.StaticBookStoreWebsite.order;

import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.Book;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.BookRepository;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.Customer;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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


    @Autowired
    private BookOrderRepository bookOrderRepository;


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    public String addToCart(int bookId, int quantity, String customerEmail) {
        // Fetch the book and customer
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Check if the order already exists (or create a new one)
        Optional<OrderDetail> existingItem = orderDetailRepository.findByCustomerAndBook(customer, book);

        if (existingItem.isPresent()) {
            OrderDetail orderDetail = existingItem.get();
            orderDetail.setQuantity(orderDetail.getQuantity() + quantity);
            orderDetail.setSubtotal((float) (orderDetail.getQuantity() * book.getPrice()));
            orderDetailRepository.save(orderDetail);
        } else {
            // Create a new OrderDetail entry
            OrderDetail newOrderDetail = new OrderDetail();
            newOrderDetail.setCustomer(customer);
            newOrderDetail.setBook(book);
            newOrderDetail.setQuantity(quantity);
            newOrderDetail.setSubtotal((float) (quantity * book.getPrice()));

            // Save to the orderdetails table
            orderDetailRepository.save(newOrderDetail);

        }
        return "redirect:/customer/cart";  // Redirect to cart view
    }

    private BookOrder getOrCreateBookOrder(Customer customer) {
        // Try to find an existing order for the customer
        BookOrder bookOrder = bookOrderRepository.findByCustomer(customer).orElse(new BookOrder());

        if (bookOrder.getCustomer() == null) {
            // If no order exists, create a new one
            bookOrder.setCustomer(customer);
            bookOrder.setOrderDate(LocalDateTime.parse(LocalDateTime.now().toString()));
            bookOrderRepository.save(bookOrder);
        }

        return bookOrder;
    }

    public void updateOrderDetailQuantity(OrderDetailID orderDetailID, int quantity) {
        // Fetch the OrderDetail using the composite key
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailID)
                .orElseThrow(() -> new RuntimeException("OrderDetail not found"));

        // Update the quantity and recalculate the subtotal
        orderDetail.setQuantity(quantity);
        orderDetail.setSubtotal(orderDetail.subtotalOfOrder()); // Assuming this method calculates the subtotal

        // Save the updated OrderDetail back to the database
        orderDetailRepository.save(orderDetail);
    }

    public void removeOrderDetail(OrderDetailID orderDetailID) {
        // Delete the OrderDetail by its composite key
        orderDetailRepository.deleteById(orderDetailID);
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
