package nl.novi.backend_it_helpdesk.models;

import jakarta.persistence.*;
import nl.novi.backend_it_helpdesk.enums.UserRoleEnum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    private UserRoleEnum role;

    private String email;

    @OneToMany(mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<Authority>();

    @OneToMany(mappedBy = "User")
    List<Ticket> tickets = new ArrayList<>();


}
