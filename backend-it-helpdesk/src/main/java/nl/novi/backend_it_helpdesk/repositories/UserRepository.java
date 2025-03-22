package nl.novi.backend_it_helpdesk.repositories;

import nl.novi.backend_it_helpdesk.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    Object findByUsername(String username);
}
