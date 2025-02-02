package nl.novi.backend_it_helpdesk.mappers;

import nl.novi.backend_it_helpdesk.dtos.CategoryInputDto;
import nl.novi.backend_it_helpdesk.dtos.CategoryOutputDto;
import nl.novi.backend_it_helpdesk.models.Category;

public class CategoryMapper {


    public static CategoryOutputDto transferToDto(Category category) {

        CategoryOutputDto dto = new CategoryOutputDto();

        dto.setId(category.getId());
        dto.setCategoryName(category.getCategoryName());
        dto.setSubCategoryName(category.getSubCategoryName());

        return dto;

    }

    public static Category transferToCategory(CategoryInputDto categoryInputDto) {

        Category category = new Category();

        category.setCategoryName(categoryInputDto.getCategoryName());
        category.setSubCategoryName(categoryInputDto.getSubCategoryName());

        return category;


    }


}
