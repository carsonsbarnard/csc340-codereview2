package com.bookstorestaticwebsite.StaticBookStoreWebsite.security;

import com.bookstorestaticwebsite.StaticBookStoreWebsite.admin.User;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.admin.UserRepository;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.Customer;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Check admin repository first
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            return buildAdminUserDetails(user);
        }

        // Check customer repository if not found in admin
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(email + " not found"));
        return buildCustomerUserDetails(customer);
    }

    private UserDetails buildAdminUserDetails(User user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if ("superadmin@gmail.com".equalsIgnoreCase(user.getEmail())) {
            authorities.add(new SimpleGrantedAuthority("SUPER_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    private UserDetails buildCustomerUserDetails(Customer customer) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("CUSTOMER"));
        return new org.springframework.security.core.userdetails.User(customer.getEmail(), customer.getPassword(), authorities);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
