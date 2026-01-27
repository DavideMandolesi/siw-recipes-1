package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.User;

public interface UserRepository extends CrudRepository<User,Long> {
	
	@Query("SELECT u FROM User u WHERE :searchParam IS NULL OR (" +
			"LOWER(u.firstName) LIKE :searchParam OR " +
			"LOWER(u.lastName) LIKE :searchParam OR " +
			"LOWER(u.credentials.username) LIKE :searchParam)" +
			"ORDER BY u.firstName")
	List<User> findBySearchParam(@Param("searchParam") String searchParam);
	
	Optional<User> findByCredentialsUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);

}
