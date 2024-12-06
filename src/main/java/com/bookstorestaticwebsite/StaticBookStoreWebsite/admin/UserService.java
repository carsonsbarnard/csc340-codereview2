package com.bookstorestaticwebsite.StaticBookStoreWebsite.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> getAllUsers(){return userRepository.findAll();}

    public void createNewUser(User user){

            user.setFullName(user.getFullName());
            user.setEmail(user.getEmail());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }

    public void updateUser(User user, long userId){
        User existing = getUserById(userId);
//        if (checkEmailExist(existing)){
//            System.out.println("Already exist");
//            return;
//        }
        existing.setEmail(user.getEmail());
        existing.setPassword(passwordEncoder.encode(user.getPassword()));
        existing.setFullName(user.getFullName());
        userRepository.save(existing);
    }

    public User getUserById(long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public void deleteUserById(long userId) { userRepository.deleteById(userId);
    }
    //check email already exists or not
    public boolean checkEmailExist(User user){
        boolean existed = false;
        List<User> list_user = getAllUsers();
        for (User u : list_user){
            if(user.getEmail().equals(u.getEmail())){
                existed = true;
                break;
            }
        }
        return existed;
    }

    public long getTotalUsers(){
        return userRepository.countTotalUsers();
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + "not found"));
    }

}
