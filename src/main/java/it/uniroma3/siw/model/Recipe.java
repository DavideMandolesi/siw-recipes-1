package it.uniroma3.siw.model;


import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String shortDescription;
	@Column (length = 5000, nullable = false)
	private String instructions;
	@Column(nullable = false)
	private int prepTime;
	@Column(nullable = false)
	private int difficulty;
	private LocalDate creationDate;
	
	
	/* ==============================
	 * ==========RELAZIONI===========
	 * ============================== */
	@ManyToOne // Collegamento alla nuova entità Category
    private Category category;
	
	@OneToMany(mappedBy="recipe")
	private List<Review> reviews;
	
	@OneToMany(mappedBy="recipe",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Ingredient> ingredients;
	
	@ManyToOne(cascade = CascadeType.ALL)
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

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
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
