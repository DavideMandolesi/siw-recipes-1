package it.uniroma3.siw.model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Recipe {
	public final static String DEFAULT_URL_RECIPE_IMG_="https://img.freepik.com/free-photo/delicious-food-wooden-table_23-2148708281.jpg?semt=ais_hybrid&w=740&q=80";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String shortDescription;
	@Column (length = 10000, nullable = false)
	private String instructions;
	@Column(nullable = false)
	private int prepTime;
	@Column(nullable = false)
	private String difficulty;
	private LocalDate creationDate;
	private String urlImage;
	private Boolean isActive=false;
	@ElementCollection
	private List<Ingredient> ingredients=new ArrayList<>();
	
	/* ==============================
	 * ==========RELAZIONI===========
	 * ============================== */
	@ManyToOne // Collegamento alla nuova entità Category
    private Category category;

	@OneToMany(mappedBy="recipe",cascade = CascadeType.REMOVE)
	private List<Review> reviews;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private User author;

	/* ==============================
	 * ===========METODI=============
	 * ============================== */
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public int getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(int prepTime) {
		this.prepTime = prepTime;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getUrlImage() {
		if(this.urlImage!=null && !this.urlImage.isBlank())
			return this.urlImage;
		else return this.DEFAULT_URL_RECIPE_IMG_;
	}
	
	public void setUrlImage(String urlImage) {
		this.urlImage=urlImage;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}	
}
