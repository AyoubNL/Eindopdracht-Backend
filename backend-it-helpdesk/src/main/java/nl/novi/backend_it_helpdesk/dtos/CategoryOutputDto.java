package nl.novi.backend_it_helpdesk.dtos;

import nl.novi.backend_it_helpdesk.models.Ticket;

import java.util.ArrayList;
import java.util.List;

public class CategoryOutputDto {

    private int id;
    private String categoryName;
    private String subCategoryName;

    public CategoryOutputDto() {}

    public CategoryOutputDto(int id, String categoryName, String subCategoryName) {
        this.id = id;
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }
}
