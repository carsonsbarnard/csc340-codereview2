package com.bookstorestaticwebsite.StaticBookStoreWebsite.order;


import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.Book;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.category.Category;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.common.BaseController;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.common.CommonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin/order")
public class BookOrderController extends BaseController {
    @Autowired
    private BookOrderService bookOrderService;
    @Autowired OrderDetailService orderDetailService;

    @GetMapping("/all")
    public String getAllOrders( Model model){
        model.addAttribute("title", "Orders List");
        model.addAttribute("ordersList", bookOrderService.getAllBookOrder());
        return "admin/order-list";
    }

    @GetMapping("/detail/{oId}")
    public String getOrderDetailById(@PathVariable int oId, Model model ){
        model.addAttribute("title", "Order ID: " + oId );
        model.addAttribute("bookOrder", bookOrderService.getBookOrderById(oId));
        return "admin/order-detail";
    }

    @GetMapping("/edit/{oId}")
    public String editOrder(@PathVariable int oId, Model model){
        BookOrder bookOrder = bookOrderService.getBookOrderById(oId);
        List<String> statusList = CommonConfig.STATUS_LIST;
        model.addAttribute("title", "Edit Order");
        model.addAttribute("bookOrder", bookOrder);
        model.addAttribute("statusList", statusList);
        return "admin/order-edit";
    }
    @PostMapping("/update/{oId}")
    public String updateBookOrder(@ModelAttribute BookOrder bookOrder, @PathVariable int oId){
        System.out.println("Received BookOrder: " + bookOrder);
        if (bookOrder.getOrderDetails() != null) {
            for (OrderDetail detail : bookOrder.getOrderDetails()) {
                System.out.println("Detail: BookId = " + detail.getOrderDetailID().getBookId() + ", Quantity: " + detail.getQuantity()); }
        } else {
            System.out.println("Order details are null.");
        }
        bookOrderService.updateOrder(bookOrder, oId);
        return  "redirect:/admin/order/all";
    }


    @GetMapping("/delete/{oId}")
    public String deleteOrder(@PathVariable int oId){
        //Check if order is completed
        BookOrder order = bookOrderService.getBookOrderById(oId);
        //If status = Cancelled , it can be deleted
        if(order.getStatus().equals(CommonConfig.STATUS_LIST.get(4))){
            //Delete all books in order
            orderDetailService.deleteOrderDetail(oId);
            //then delete order
            bookOrderService.deleteOrder(oId);
            return "admin/order-list";
        }
            System.out.println("Can not delete this order because this order is in process");

        return "admin/order-list";
    }

    @GetMapping("/removeBook")
    public String removeBookFromOrder(@RequestParam int bookOrderId, @RequestParam int bookId){
        orderDetailService.removeBookFromOrder(bookOrderId, bookId);
        return "redirect:/admin/order/edit/" + bookOrderId;
    }

}
