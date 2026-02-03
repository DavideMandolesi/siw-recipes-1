package it.uniroma3.siw.model;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Category {
	public final static String DEFAULT_CAT_NAME = "Altro";
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	@NotBlank(message = "{NotBlank.category.name}")
    @Column(unique = true, nullable = false)
    private String name;
    
	@NotBlank(message = "{NotBlank.category.description}")
	@Size(min = 1, max = 255, message = "{Size.category.description}")
    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Recipe> recipes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}
    
    
}
