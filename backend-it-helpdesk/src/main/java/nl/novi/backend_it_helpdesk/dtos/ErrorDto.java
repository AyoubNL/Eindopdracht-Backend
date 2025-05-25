package nl.novi.backend_it_helpdesk.dtos;


import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorDto {

    private String error;
    private String message;
    private LocalDateTime timestamp;
    private int status;

}
