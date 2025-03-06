package nl.novi.backend_it_helpdesk.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "screenshots")
public class Screenshot {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String contentType;
    private String url;
    private Long size;

    @Lob
    private byte[] contents;

    @ManyToOne(cascade = CascadeType.ALL)
    private Ticket ticket;

    public Screenshot(String title, String contentType, String url,Long size, byte[] bytes) {
        this.title = title;
        this.contentType = contentType;
        this.url = url;
        this.contents = bytes;
        this.size = size;
    }

}
