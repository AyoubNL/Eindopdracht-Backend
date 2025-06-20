package nl.novi.backend_it_helpdesk.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.backend_it_helpdesk.config.CaId;
import nl.novi.backend_it_helpdesk.enums.StatusTicketEnum;

@Entity
@Table(name = "fixes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fix {

    @Id
    @CaId
    private String id;
    private String solution;
    private String feedback;
    @Enumerated(EnumType.STRING)
    private StatusTicketEnum status;


}
