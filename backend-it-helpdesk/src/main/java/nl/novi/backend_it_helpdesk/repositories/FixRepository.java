package nl.novi.backend_it_helpdesk.repositories;

import nl.novi.backend_it_helpdesk.models.Fix;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixRepository extends JpaRepository<Fix, Long> {
}
