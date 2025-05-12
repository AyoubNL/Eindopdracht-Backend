package nl.novi.backend_it_helpdesk.models;

import jakarta.persistence.*;
import lombok.*;
import nl.novi.backend_it_helpdesk.config.CaId;
import org.hibernate.annotations.GenericGenerator;

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
    @CaId
    private String id;
    private String categoryName;
    private String subCategoryName;

}
