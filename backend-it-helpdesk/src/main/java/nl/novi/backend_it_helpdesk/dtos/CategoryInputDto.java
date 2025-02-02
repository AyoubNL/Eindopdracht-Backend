package nl.novi.backend_it_helpdesk.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nl.novi.backend_it_helpdesk.models.Ticket;

import java.util.ArrayList;
import java.util.List;

public class CategoryInputDto {

    @NotNull(message = "Category is required")
    @Size(min=1, max=50, message = "Category too long (max 50 characters)")
    private String categoryName;
    @Size(min=1, max=50, message = "Subcategory too long (max 50 characters)")
    @NotNull(message = "Subcategory is required")
    private String subCategoryName;

    public CategoryInputDto() {}

    public CategoryInputDto(String categoryName, String subCategoryName) {
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
    }

    public @NotNull(message = "Category is required") @Size(min = 1, max = 50, message = "Category too long (max 50 characters)") String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(@NotNull(message = "Category is required") @Size(min = 1, max = 50, message = "Category too long (max 50 characters)") String categoryName) {
        this.categoryName = categoryName;
    }

    public @Size(min = 1, max = 50, message = "Subcategory too long (max 50 characters)") @NotNull(message = "Subcategory is required") String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(@Size(min = 1, max = 50, message = "Subcategory too long (max 50 characters)") @NotNull(message = "Subcategory is required") String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }
}
