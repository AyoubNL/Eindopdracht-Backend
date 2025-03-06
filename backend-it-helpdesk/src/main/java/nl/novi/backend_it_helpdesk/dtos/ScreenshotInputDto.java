package nl.novi.backend_it_helpdesk.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenshotInputDto {

    private String title;
    private String contentType;
    private String url;
    private Long size;
    private byte[] contents;

}
