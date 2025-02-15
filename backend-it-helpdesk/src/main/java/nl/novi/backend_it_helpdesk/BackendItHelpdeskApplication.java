package nl.novi.backend_it_helpdesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BackendItHelpdeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendItHelpdeskApplication.class, args);
	}

}
