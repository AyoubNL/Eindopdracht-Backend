package nl.novi.backend_it_helpdesk.repositories;

import nl.novi.backend_it_helpdesk.models.Screenshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenshotRepository extends JpaRepository<Screenshot, String> {
}
