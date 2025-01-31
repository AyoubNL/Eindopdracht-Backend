package nl.novi.backend_it_helpdesk.mappers;

import nl.novi.backend_it_helpdesk.dtos.CategoryOutputDto;
import nl.novi.backend_it_helpdesk.models.Category;

public class CategoryMapper {


    public static CategoryOutputDto transferToDto(Category category) {

        CategoryOutputDto dto = new CategoryOutputDto();

        dto.setId(category.getId());
        dto.setCategoryName(category.getCategoryName());
        dto.setSubCategoryName(category.getSubCategoryName());

        if(category.getTickets() != null){
            dto.setTickets(category.getTickets());
        }

        return dto;

    }


}
