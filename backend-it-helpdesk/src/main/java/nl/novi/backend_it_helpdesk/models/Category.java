package nl.novi.backend_it_helpdesk.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String categoryName;
    private String subCategoryName;

    @OneToMany(mappedBy = "category")
    List<Ticket> tickets = new ArrayList<>();


}
