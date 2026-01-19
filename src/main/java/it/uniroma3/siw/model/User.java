package it.uniroma3.siw.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	public static final String DEFAULT_URL_PROFILE_PIC = "https://icons.iconarchive.com/icons/fa-team/fontawesome/256/FontAwesome-Circle-User-icon.png";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	
	private String urlImage;
	
	private Boolean isBanned = false;
	
	/* ==============================
	 * ==========RELAZIONI===========
	 * ============================== */
	
	@OneToMany(mappedBy = "author")
	private List<Recipe> recipes;
	
	/*@OneToMany(mappedBy = "author")
	List<Review> reviews;
	forse non necessario se inserisco il riferimento unidirezionale da review utente come campo "autore".
	la bidirezionalità serve per avere una lista di recensioni nella visualizzazione del profilo in maniera immediata senza query
	*/
	
	
	@OneToOne(mappedBy="user", cascade = CascadeType.ALL)
	private Credentials credentials;
	
	/* ==============================
	 * ===========METODI=============
	 * ============================== */
	
	public Boolean getIsBanned() {
		return isBanned;
	}

	public void setIsBanned(Boolean isBanned) {
		this.isBanned = isBanned;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUrlImage() {
		if(this.urlImage!=null && !this.urlImage.isBlank())
			return this.urlImage;
		else return this.DEFAULT_URL_PROFILE_PIC;
	}
	
	public void setUrlImage(String urlImage) {
		this.urlImage=urlImage;
	}
	
	public Credentials getCredentials() {
		return credentials;
	}
	
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}	
}