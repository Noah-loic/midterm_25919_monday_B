package auca.ac.rw.restfullApiAssignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import auca.ac.rw.restfullApiAssignment.modal.User;
import auca.ac.rw.restfullApiAssignment.modal.ERole;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameAndPassword(String username, String password);
    List<User> findByRole(ERole role);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
