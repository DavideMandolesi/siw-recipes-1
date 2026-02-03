package it.uniroma3.siw.model;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message="{NotBlank.review.text}")
	@Size(min = 1, max = 5000, message = "{Size.review.text}")
	@Column(length = 5000, nullable = false)
	private String text;
	
	@Min(value=0, message="{Min.review.rating}")
	@Max(value=5, message="{Max.review.rating}")
	@Column(nullable = false)
	private int rating;
	private LocalDate creationDate;
	
	/* ==============================
	 * ==========RELAZIONI===========
	 * ============================== */
	
	@ManyToOne
	private User author;
	
	@ManyToOne
	private Recipe recipe;

	
	/* ==============================
	 * ===========METODI=============
	 * ============================== */
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}	
}
