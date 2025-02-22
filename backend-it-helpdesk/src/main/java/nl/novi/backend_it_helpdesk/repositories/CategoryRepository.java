package nl.novi.backend_it_helpdesk.repositories;

import nl.novi.backend_it_helpdesk.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
