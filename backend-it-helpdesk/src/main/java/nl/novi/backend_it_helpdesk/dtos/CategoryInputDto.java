package nl.novi.backend_it_helpdesk.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInputDto {

    @JsonProperty("categoryname")
    @NotNull(message = "Category is required")
    @Size(min=1, max=50, message = "Category too long (max 50 characters)")
    private String categoryName;
    @JsonProperty("subcategoryname")
    @Size(min=1, max=50, message = "Subcategory too long (max 50 characters)")
    @NotNull(message = "Subcategory is required")
    private String subCategoryName;

}
