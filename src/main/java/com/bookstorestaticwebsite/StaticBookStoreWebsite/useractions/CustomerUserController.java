package com.bookstorestaticwebsite.StaticBookStoreWebsite.useractions;

import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.Book;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.BookRepository;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.BookService;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.category.Category;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.category.CategoryRepository;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.Customer;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.CustomerRepository;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.CustomerService;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.order.*;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public CustomerUserController(CustomerRepository customerRepository, BookRepository bookRepository, CategoryRepository categoryRepository, PasswordEncoder passwordEncoder, OrderDetailRepository orderDetailRepository, ReviewRepository reviewRepository, OrderDetailService orderDetailService, OrderDetailService orderDetailService1) {
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.passwordEncoder = passwordEncoder;
        this.orderDetailRepository = orderDetailRepository;
        this.reviewRepository = reviewRepository;
        this.orderDetailService = orderDetailService1;
    }

    @GetMapping("/index")
    public String index(Model model) {
        // Fetch all books and add to the model
        model.addAttribute("books", bookRepository.findAll());

        // Fetch all categories and add to the model
        model.addAttribute("categories", categoryRepository.findAll());

        // Retrieve the logged-in user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = authentication.getName(); // Retrieves the logged-in user's email
        // Fetch the customer from the database based on their email
        Customer customer = customerRepository.findByEmail(loggedInEmail)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        String customerName = customer.getFirstName();
        model.addAttribute("email", loggedInEmail);
        model.addAttribute("cusName", customerName);

        return "customer/index"; // Render the updated index page
    }

    @GetMapping("/mostpopular")
    public String mostPopularBooks(Model model) {
        List<Book> mostPopularBooks = bookRepository.findMostPopularBooks();
        model.addAttribute("books", mostPopularBooks);
        return "customer/mostpopular";
    }


    @GetMapping("/newest")
    public String getNewestBooks(Model model) {
        List<Book> books = bookRepository.findAllByOrderByBookIdDesc(); // Fetch books sorted by ID descending
        model.addAttribute("books", books);
        return "customer/newest"; // Return the Thymeleaf template name
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

    private final CategoryRepository categoryRepository;
    // This method handles category-specific page requests
    @GetMapping("/category/{categoryId}")
    public String getBooksByCategory(@PathVariable int categoryId, Model model) {
        // Fetch the category name based on the categoryId
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        // Retrieve all books for the selected category
        List<Book> booksInCategory = bookRepository.findByCategory_CategoryId(categoryId);

        // Add category and book data to the model
        model.addAttribute("category", category);
        model.addAttribute("books", booksInCategory);

        return "customer/category"; // This will link to category.html
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

    private final OrderDetailRepository orderDetailRepository;

    @GetMapping("/customer/confirmOrder")
    public String confirmOrder(Model model) {
        // Fetch cart details from session or database
        List<OrderDetail> cartItems = orderDetailRepository.findAll(); // Replace with appropriate query
        double total = cartItems.stream()
                .mapToDouble(item -> item.getSubtotal())
                .sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        return "/customer/confirmOrder";
    }

    @PostMapping("/customer/confirmOrder")
    public String placeOrder(Authentication authentication) {
        // Fetch customer
        String email = authentication.getName();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Fetch cart items (replace with actual cart-fetching logic)
        List<OrderDetail> cartItems = orderDetailRepository.findAll();

        // Create a new order
        BookOrder bookOrder = new BookOrder();
        bookOrder.setCustomer(customer);
        bookOrder.setOrderDate((LocalDateTime.now()));
        bookOrder.setStatus("Pending");
        bookOrder.setTotal(cartItems.stream().mapToDouble(OrderDetail::getSubtotal).sum());
        bookOrderRepository.save(bookOrder);

        // Link cart items to the order
        for (OrderDetail item : cartItems) {
            item.setBookOrder(bookOrder);
            orderDetailRepository.save(item);
        }

        // Clear the cart (if using a session-based cart)
        orderDetailRepository.deleteAll(); // Or your cart clearing logic

        return "redirect:/customer/vieworders";
    }

    @GetMapping("/book/{id}")
    public String getBookDetails(@PathVariable("id") int bookId, Model model) {
        // Fetch the book details
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        // Fetch reviews for this book
        List<Review> reviews = reviewRepository.findByBook(book);

        // Add book and reviews to the model
        model.addAttribute("book", book);
        model.addAttribute("reviews", reviews);

        return "customer/book-details";
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
                              Authentication authentication,
                              RedirectAttributes redirectAttributes) { // Use RedirectAttributes

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

        // Add flash attribute for success message
        redirectAttributes.addFlashAttribute("reviewSuccess", "Your review has been successfully submitted!");

        // Redirect to the orders page or a page showing reviews
        return "redirect:/customer/vieworders";  // This will trigger a redirect with the flash attribute
    }

    @GetMapping("/cart")
    public String viewCart(Authentication authentication, Model model) {
        // Fetch the customer details based on the authenticated user
        String customerEmail = authentication.getName();
        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Fetch the BookOrder with "Pending" status for the customer
        List<BookOrder> pendingOrders = bookOrderRepository.findByCustomerAndStatus(customer, "Pending");

        // Fetch the OrderDetails associated with these pending BookOrders
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (BookOrder bookOrder : pendingOrders) {
            List<OrderDetail> details = orderDetailRepository.findByBookOrder(bookOrder);
            orderDetails.addAll(details);
        }

        // Add order details and pending order info to the model
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("pendingOrders", pendingOrders);

        return "customer/cart";  // Redirect to cart page to show the user's cart
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam int bookId, @RequestParam int quantity, Authentication authentication) {
        // Fetch the book by ID
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        // Fetch the customer using authentication
        String email = authentication.getName();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Check for an existing BookOrder for this book and customer
        Optional<BookOrder> existingOrder = bookOrderRepository.findByCustomerAndBookAndStatus(customer, book, "Pending");

        BookOrder bookOrder;

        if (existingOrder.isPresent()) {
            // If an order already exists for this book, update it
            bookOrder = existingOrder.get();

            // Update the OrderDetail quantity and subtotal
            Optional<OrderDetail> existingOrderDetail = orderDetailRepository.findByBookOrderAndBook(bookOrder, book);

            if (existingOrderDetail.isPresent()) {
                OrderDetail orderDetail = existingOrderDetail.get();
                orderDetail.setQuantity(orderDetail.getQuantity() + quantity);
                orderDetail.setSubtotal((float) (orderDetail.getQuantity() * book.getPrice()));
                orderDetailRepository.save(orderDetail);
            }
        } else {
            // If no existing order, create a new one
            bookOrder = createNewBookOrder(customer, book, quantity);

            // Create a new OrderDetail
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setBook(book);
            orderDetail.setCustomer(customer);
            orderDetail.setQuantity(quantity);
            orderDetail.setSubtotal((float) (quantity * book.getPrice()));
            orderDetail.setBookOrder(bookOrder);

            orderDetailRepository.save(orderDetail);
        }

        return "redirect:/customer/cart"; // Redirect to the cart page
    }

    private BookOrder createNewBookOrder(Customer customer, Book book, int quantity) {
        BookOrder bookOrder = new BookOrder();

        // Set customer details
        bookOrder.setCustomer(customer);
        bookOrder.setFirstName(customer.getFirstName());
        bookOrder.setLastName(customer.getLastName());
        bookOrder.setAddressLine1(customer.getAddressLine1());
        bookOrder.setAddressLine2(customer.getAddressLine2());
        bookOrder.setCity(customer.getCity());
        bookOrder.setState(customer.getState());
        bookOrder.setZipcode(customer.getZipcode());
        bookOrder.setCountry(customer.getCountry());
        bookOrder.setPhone(customer.getPhone());

        // Set book and order details
        bookOrder.setBook(book);
        bookOrder.setSubtotal(book.getPrice());

        // Tax and shipping fee
        bookOrder.setTax(book.getPrice() * quantity * 0.1); // Assuming 10% tax
        bookOrder.setShippingFee(5.00); // Fixed shipping fee
        bookOrder.setTotal((bookOrder.getSubtotal() * quantity) + bookOrder.getTax() + bookOrder.getShippingFee());

        // Set additional details
        bookOrder.setOrderDate(LocalDateTime.now());
        bookOrder.setPaymentMethod("Credit Card"); // Default payment method
        bookOrder.setStatus("Pending"); // Initial status

        return bookOrderRepository.save(bookOrder);
    }

    private final OrderDetailService orderDetailService;

    @PostMapping("/cart/update")
    public String updateCartItem(
            @RequestParam int bookId,
            @RequestParam int customerId,
            @RequestParam int bookOrderId,
            @RequestParam int quantity) {

        OrderDetailID orderDetailID = new OrderDetailID();
        orderDetailID.setBookId(bookId);
        orderDetailID.setCustomerId(customerId);
        orderDetailID.setBookOrderId(bookOrderId);

        orderDetailService.updateOrderDetailQuantity(orderDetailID, quantity);
        return "redirect:/customer/cart";
    }

    @PostMapping("/cart/remove")
    public String removeCartItem(
            @RequestParam int bookId,
            @RequestParam int customerId,
            @RequestParam int bookOrderId
    ) {
        // Construct the composite key
        OrderDetailID orderDetailID = new OrderDetailID();
        orderDetailID.setBookId(bookId);
        orderDetailID.setCustomerId(customerId);
        orderDetailID.setBookOrderId(bookOrderId);

        // Check if the associated BookOrder has any remaining items
        BookOrder bookOrder = bookOrderRepository.findById(bookOrderId)
                .orElseThrow(() -> new IllegalArgumentException("BookOrder not found"));

        bookOrderRepository.delete(bookOrder);

        // Call the service to remove the item
        orderDetailService.removeOrderDetail(orderDetailID);

        // Redirect back to the cart page
        return "redirect:/customer/cart";
    }

    @PostMapping("/cart/checkout")
    public String checkout(Authentication authentication) {
        // Fetch the customer based on authenticated user
        String email = authentication.getName();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Fetch all pending orders for this customer
        List<BookOrder> pendingOrders = bookOrderRepository.findByCustomerAndStatus(customer, "Pending");

        for (BookOrder bookOrder : pendingOrders) {
            // Calculate total from OrderDetails
            float total = 0f;
            List<OrderDetail> orderDetails = orderDetailRepository.findByBookOrder(bookOrder);
            for (OrderDetail orderDetail : orderDetails) {
                total += orderDetail.getSubtotal();
            }

            // Update the BookOrder
            bookOrder.setTotal((double) total);
            bookOrder.setStatus("Processing");
            bookOrderRepository.save(bookOrder);
        }

        return "redirect:/customer/cart/checkout-success";
    }

    @GetMapping("/cart/checkout-success")
    public String checkoutSuccess(Model model, Authentication authentication) {
        // Fetch the customer based on authenticated user
        String email = authentication.getName();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        return "customer/checkout-success"; // Name of the HTML file
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
