package nl.novi.backend_it_helpdesk.mappers;

import nl.novi.backend_it_helpdesk.dtos.CategoryInputDto;
import nl.novi.backend_it_helpdesk.dtos.CategoryOutputDto;
import nl.novi.backend_it_helpdesk.models.Category;

import java.util.ArrayList;
import java.util.List;

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

    public static List<CategoryOutputDto> transferCategoryListToDtoList(List<Category> categories) {

        List<CategoryOutputDto> categoryDtoList = new ArrayList<>();

        for(Category category : categories) {
            CategoryOutputDto outputDto = transferToDto(category);

            outputDto.setId(category.getId());
            outputDto.setCategoryName(category.getCategoryName());
            outputDto.setSubCategoryName(category.getSubCategoryName());
            categoryDtoList.add(outputDto);
        }
        return categoryDtoList;


    }


}
