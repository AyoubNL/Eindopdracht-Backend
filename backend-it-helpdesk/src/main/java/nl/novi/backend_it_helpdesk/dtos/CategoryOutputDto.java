package nl.novi.backend_it_helpdesk.dtos;


public class CategoryOutputDto {

    private Long id;
    private String categoryName;
    private String subCategoryName;

    public CategoryOutputDto() {}

    public CategoryOutputDto(Long id, String categoryName, String subCategoryName) {
        this.id = id;
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
