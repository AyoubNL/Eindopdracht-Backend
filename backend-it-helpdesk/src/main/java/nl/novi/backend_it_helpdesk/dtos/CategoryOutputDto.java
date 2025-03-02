package nl.novi.backend_it_helpdesk.dtos;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryOutputDto {

    private Long id;
    private String categoryName;
    private String subCategoryName;

}
