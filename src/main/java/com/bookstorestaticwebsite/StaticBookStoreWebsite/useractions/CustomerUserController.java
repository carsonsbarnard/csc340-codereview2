package com.bookstorestaticwebsite.StaticBookStoreWebsite.useractions;

import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.Book;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.BookRepository;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.BookService;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.Customer;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.CustomerRepository;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.CustomerService;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.order.BookOrder;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.order.BookOrderRepository;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.review.Review;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.review.ReviewRepository;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerUserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private BookOrderRepository bookOrderRepository;

    @Autowired
    private final BookRepository bookRepository;

    public CustomerUserController(CustomerRepository customerRepository, BookRepository bookRepository, PasswordEncoder passwordEncoder, ReviewRepository reviewRepository) {
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
        this.passwordEncoder = passwordEncoder;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/index")
    public String index(Model model) {
        Book book6 = bookService.getBookById(6);
        Book book7 = bookService.getBookById(7);
        model.addAttribute("book6", book6);
        model.addAttribute("book7", book7);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = authentication.getName(); // Retrieves the logged-in user's email

        // Add the email or other details to the model
        model.addAttribute("email", loggedInEmail);

        return "customer/index";
    }

    @GetMapping("/register")
    public String register() {
        return "customer/register";
    }

    @PostMapping("/add")
    public ResponseEntity<String> createCustomer(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("phone") String phone,
            @RequestParam("addressLine1") String addressLine1,
            @RequestParam("addressLine2") String addressLine2,
            @RequestParam("city") String city,
            @RequestParam("state") String state,
            @RequestParam("zipcode") String zipcode,
            @RequestParam("country") String country) {

        if (!password.equals(confirmPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match.");
        }

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setPhone(phone);
        customer.setAddressLine1(addressLine1);
        customer.setAddressLine2(addressLine2);
        customer.setCity(city);
        customer.setState(state);
        customer.setZipcode(zipcode);
        customer.setCountry(country);
        customer.setRegisterDate(Date.valueOf(LocalDate.now())); // Automatically set the registration date.

        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer registered successfully.");
    }

    // Mapping for the search functionality
    @GetMapping("/search")
    public String searchBooks(@RequestParam("query") String query, Model model) {
        // Search for books by title
        List<Book> books = bookService.searchBooksByTitle(query);
        model.addAttribute("books", books);  // Add the search results to the model
        model.addAttribute("query", query);  // Add the search query to display it in the view
        return "customer/books";  // Return the view that displays the books (the books listing page)
    }

    @GetMapping("/profile")
    public String showUserProfile(Model model, Authentication authentication) {
        // Get the currently logged-in user's email
        String email = authentication.getName();  // This gets the email of the logged-in user

        // Fetch the customer from the database based on their email
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Add customer object to the model
        model.addAttribute("customer", customer);

        // Return the view name
        return "customer/profile";
    }

    private final CustomerRepository customerRepository;

    @GetMapping("/editprofile")
    public String showEditProfilePage(Model model, Authentication authentication) {
        // Fetch currently logged-in customer's email
        String email = authentication.getName();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        model.addAttribute("customer", customer);
        return "customer/editprofile";
    }

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/editprofile")
    public String updateProfile(@ModelAttribute("customer") Customer updatedCustomer,
                                @RequestParam(required = false) String newPassword,
                                Authentication authentication) {
        // Fetch the current customer by email
        String email = authentication.getName();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Update fields
        customer.setFirstName(updatedCustomer.getFirstName());
        customer.setLastName(updatedCustomer.getLastName());
        customer.setPhone(updatedCustomer.getPhone());
        customer.setAddressLine1(updatedCustomer.getAddressLine1());
        customer.setAddressLine2(updatedCustomer.getAddressLine2());
        customer.setCity(updatedCustomer.getCity());
        customer.setState(updatedCustomer.getState());
        customer.setZipcode(updatedCustomer.getZipcode());
        customer.setCountry(updatedCustomer.getCountry());

        // Handle password change if provided
        if (newPassword != null && !newPassword.isEmpty()) {
            customer.setPassword(passwordEncoder.encode(newPassword));
        }

        // Save updated customer details
        customerRepository.save(customer);

        return "redirect:/customer/profile";
    }


    @GetMapping("/about")
    public String about() {
        return "customer/about";
    }
    @GetMapping("/contact")
    public String contact() {
        return "customer/contact";
    }
    @GetMapping("/login")
    public String login() {
        return "customer/login";
    }

    // Endpoint to get available books
    @GetMapping("/books")
    public String getAvailableBooks(Model model) {
        List<Book> books = bookService.getAllBooks(); // Fetch all books from the service
        model.addAttribute("books", books);          // Add the books to the model
        return "customer/all-books";                         // Render the all-books.html template
    }

    @PostMapping("/order")
    public String placeOrder(@RequestParam int bookId, Authentication authentication, Model model) {
        // Get the logged-in customer's email
        String email = authentication.getName();
        Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Get the book details
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));

        // Create a new order
        BookOrder order = new BookOrder();
        order.setBook(book);
        order.setCustomer(customer);
        order.setFirstName(customer.getFirstName());
        order.setLastName(customer.getLastName());
        order.setAddressLine1(customer.getAddressLine1());
        order.setAddressLine2(customer.getAddressLine2());
        order.setCity(customer.getCity());
        order.setState(customer.getState());
        order.setZipcode(customer.getZipcode());
        order.setCountry(customer.getCountry());
        order.setPhone(customer.getPhone());
        order.setSubtotal(book.getPrice());
        order.setTax((book.getPrice() * 0.1)); // Assuming 10% tax
        order.setShippingFee(5.00); // Assuming fixed shipping fee
        order.setTotal((order.getSubtotal() + order.getTax() + order.getShippingFee()));
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentMethod("Credit Card"); // Replace as needed
        order.setStatus("Pending");

        // Save the order
        bookOrderRepository.save(order);

        // Redirect to a confirmation page
        model.addAttribute("order", order);
        return "customer/orderConfirmation";
    }

    @GetMapping("/vieworders")
    public String viewOrders(Model model, Authentication authentication) {
        String email = authentication.getName();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        List<BookOrder> orders = bookOrderRepository.findByCustomerCustomerId(customer.getCustomerId());
        List<Book> books = bookService.getAllBooks();

        // Initialize an empty review object for form binding
        model.addAttribute("review", new Review());
        model.addAttribute("orders", orders);
        model.addAttribute("book", books);
        return "customer/vieworders";
    }


    private final ReviewRepository reviewRepository;

    @PostMapping("/submitReview/{bookId}")
    public String leaveReview(@PathVariable int bookId,  // Get bookId from the URL
                              @RequestParam String headline,
                              @RequestParam String comment,
                              @RequestParam int rating,
                              Authentication authentication) {

        // Fetch the logged-in user
        String email = authentication.getName();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Fetch the book being reviewed
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        // Create the review
        Review review = new Review();
        review.setHeadline(headline);
        review.setComment(comment);
        review.setRating(rating);
        review.setDateReview(Date.valueOf(LocalDate.now()));
        review.setCustomer(customer);  // Associate review with the customer
        review.setBook(book);  // Associate review with the book

        // Save the review
        reviewRepository.save(review);

        // Redirect to the orders page or a page showing reviews
        return "redirect:/customer/vieworders";  // Or a page showing reviews
    }




/*
    // Endpoint for users to write a review for a book
    @PostMapping("/{customerId}/reviews/{bookId}")
    public ResponseEntity<Review> addReview(
            @PathVariable int customerId,
            @PathVariable int bookId,
            @RequestBody Review review) {
        Review savedReview = reviewService.addReview(customerId, bookId, review);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    } */
}
