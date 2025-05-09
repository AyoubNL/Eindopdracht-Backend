package nl.novi.backend_it_helpdesk.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.backend_it_helpdesk.config.CaId;
import nl.novi.backend_it_helpdesk.enums.TypeTicketEnum;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Detail {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CaId
    private String id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private TypeTicketEnum type;
}
