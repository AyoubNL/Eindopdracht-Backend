package nl.novi.backend_it_helpdesk.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScreenshotOutputDto {

    private Long id;
    private String title;
    private String contentType;
    private String url;
    private Long size;

}
