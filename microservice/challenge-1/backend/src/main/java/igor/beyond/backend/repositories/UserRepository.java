/**
 * 
 */
package igor.beyond.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import igor.beyond.backend.entities.User;

/**
 * @author Igor Tcherniavski
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	List<User> findAll();
	Optional<User> findById(Long userId);
	User findByEmail(String email);
}
