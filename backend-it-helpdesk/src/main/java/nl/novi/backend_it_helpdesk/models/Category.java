package nl.novi.backend_it_helpdesk.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;
    private String subCategoryName;

    @OneToMany(mappedBy = "category")
    List<Ticket> tickets = new ArrayList<>();


}
