package com.bookstorestaticwebsite.StaticBookStoreWebsite.review;

import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews(){return reviewRepository.findAll();}

    public Review getReviewById(int id){
        return reviewRepository.findById(id).orElse(null);
    }

    public void updateReview(Review review, int id){
        Review existing = getReviewById(id);

        existing.setHeadline(review.getHeadline());
        existing.setComment(review.getComment());
        reviewRepository.save(existing);

    }

    public void deleteReview(int id){
        reviewRepository.deleteById(id);
    }

    public long getTotalReviews(){
        return reviewRepository.countTotalReviews();
    }
    //For api
//    public  void createNewReview(Review review){
//        reviewRepository.save(review);
//    }



}
