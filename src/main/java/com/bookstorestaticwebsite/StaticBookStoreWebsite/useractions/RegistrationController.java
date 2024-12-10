package com.bookstorestaticwebsite.StaticBookStoreWebsite.useractions;

import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.Customer;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class RegistrationController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Display the registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new Customer()); // Add an empty Customer object to the model
        return "register"; // Render the "register.html" template
    }

    @PostMapping("/register")
    public String registerCustomer(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phone,
            @RequestParam String addressLine1,
            @RequestParam(required = false) String addressLine2,
            @RequestParam String city,
            @RequestParam String state,
            @RequestParam String country,
            @RequestParam String zipcode
    ) {
        // Check if email already exists
        if (customerRepository.findByEmail(email).isPresent()) {
            // Redirect back to the registration form with an error
            return "redirect:/register?error=emailExists";
        }

        // Create a new Customer
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(passwordEncoder.encode(password)); // Encode the password
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPhone(phone);
        customer.setAddressLine1(addressLine1);
        customer.setAddressLine2(addressLine2);
        customer.setCity(city);
        customer.setState(state);
        customer.setCountry(country);
        customer.setZipcode(zipcode);

        // Convert LocalDateTime to Date and set the registration date
        customer.setRegisterDate(Date.valueOf(LocalDate.now()));

        // Save the customer to the database
        customerRepository.save(customer);

        // Redirect to the login page with a success message
        return "redirect:/login?registered=true";
    }

}
