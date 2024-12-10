package com.bookstorestaticwebsite.StaticBookStoreWebsite.customer;


import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.Book;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
//@RestController
@Controller
@RequestMapping("/admin/customer")
public class CustomerController extends BaseController {
    @Autowired
    private CustomerService service;

    @GetMapping("/all")
    public String getAllCustomers(Model model){
        List<Customer> customersList = service.getAllCustomers();
        List<String> fullNames = customersList.stream().map(Customer::getFullName).collect(Collectors.toList());
        model.addAttribute("customersList", customersList);
        model.addAttribute("fullNames", fullNames);
        model.addAttribute("title", "Customer List");
        return "admin/customer-list";

    }

    @PostMapping("/edit/{cusId}")
    public String updateCustomer(@PathVariable int cusId, @ModelAttribute Customer customer){
        service.updateCustomer(customer, cusId);
        return "redirect:/admin/customer/all";
    }

    @GetMapping("/edit/{cusId}")
    public String editCustomer(@PathVariable int cusId, Model model){
        System.out.println( service.getCustomerById(cusId).toString());
        model.addAttribute("customer", service.getCustomerById(cusId));
        model.addAttribute("title", "Edit Customer");
        return "admin/customer-edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable int id){
        service.deleteCustomer(id);
        return"redirect:/admin/customer/all";
    }

    //For api
//    //Create new cus
//    @PostMapping("/create")
//    public List<Customer> createNewCustomer(@RequestBody Customer cus){
//        service.createCustomer(cus);
//        return service.getAllCustomers();
//    }
//    //list all cus
//    @GetMapping("/all")
//    public List<Customer> getAllCustomers(Model model) {
//        List<Customer> customersList = service.getAllCustomers();
//        return customersList;
//    }
//    //edit cus
//    @PutMapping("edit/{cusId}")
//    public Customer updateCus(@PathVariable int cusId, @RequestBody Customer cus)
//    {
//        service.updateCustomer(cus, cusId);
//        return service.getCustomerById(cusId);
//    }
//    @DeleteMapping("delete/{cusId}")
//    public List<Customer> deleteCus(@PathVariable int cusId){
//        service.deleteCustomer(cusId);
//        return service.getAllCustomers();
//    }
//    //search by Id
//    @GetMapping("/search/{cId}")
//    public Customer getCusById(@PathVariable int cId){
//        return service.getCustomerById(cId);
//    }

}
