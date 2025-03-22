package nl.novi.backend_it_helpdesk.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.backend_it_helpdesk.enums.UserRoleEnum;

import java.io.Serializable;

@Table(name = "authorities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AuthorityKey.class)
@Entity
public class Authority implements Serializable {

    @Id
    @Column(nullable = false)
    private String username;

    @Id
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;
}

