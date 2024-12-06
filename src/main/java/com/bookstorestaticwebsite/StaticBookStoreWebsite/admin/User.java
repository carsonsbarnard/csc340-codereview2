package com.bookstorestaticwebsite.StaticBookStoreWebsite.admin;


import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    @NonNull
    private String email;
    @NonNull
    private String password;
    private String fullName;

    public User(long userId, @NonNull String email, @NonNull String password, String fullName) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public User(@NonNull String email, @NonNull String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public User() {
    }
    public User(User user){
        this.userId = user.getUserId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
