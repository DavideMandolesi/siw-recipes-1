package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.repository.ReviewRepository;

@Service
public class ReviewService {
	@Autowired ReviewRepository reviewRepository;
	
	public Review save(Review review) {
		return reviewRepository.save(review);
	}
	
	public Review findReviewById(Long id) {
		return reviewRepository.findById(id).orElse(null);
	}
	
	public void deleteReview(Review review) {
		reviewRepository.delete(review);
	}
	
}
