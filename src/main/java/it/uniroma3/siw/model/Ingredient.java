package it.uniroma3.siw.model;


import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Embeddable
public class Ingredient {
	
	@NotBlank(message="{NotBlank.ingredient.name}")
	private String name;
	
	@Positive(message = "{Positive.ingredient.quantity}")
	private float quantity;
	
	@NotBlank(message="{NotBlank.ingredient.unit}")
	private String unit;
	
	public Ingredient() {}
	
	public Ingredient(String name, float quantity, String unit) {
		this.name = name;
		this.quantity=quantity;
		this.unit=unit;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
