package nl.novi.backend_it_helpdesk.services;

import jakarta.validation.Valid;
import nl.novi.backend_it_helpdesk.dtos.CategoryInputDto;
import nl.novi.backend_it_helpdesk.dtos.CategoryOutputDto;
import nl.novi.backend_it_helpdesk.exceptions.RecordNotFoundException;
import nl.novi.backend_it_helpdesk.mappers.CategoryMapper;
import nl.novi.backend_it_helpdesk.models.Category;
import nl.novi.backend_it_helpdesk.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static nl.novi.backend_it_helpdesk.mappers.CategoryMapper.*;
import static nl.novi.backend_it_helpdesk.mappers.TicketMapper.transferToDto;

@Service
public class CategoryService {

    final private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryOutputDto getCategoryById(Long id){

        if(categoryRepository.findById(id).isPresent()){
            Category category = categoryRepository.findById(id).get();

            return CategoryMapper.transferToDto(category);
        }

        else {
            throw new RecordNotFoundException("Het categorienummer:" + id + " is onbekend");
        }



    }

    public List<CategoryOutputDto> getAllCategories() {

        List<Category> categoryList = categoryRepository.findAll();

        return transferCategoryListToDtoList(categoryList);
    }

    public CategoryOutputDto addCategory(CategoryInputDto category) {

        Category cat = transferToCategory(category);
        categoryRepository.save(cat);

        return CategoryMapper.transferToDto(cat);
    }

    public void deleteTicket(Long id) {

        categoryRepository.deleteById(id);
    }

    public CategoryOutputDto updateCategory(Long id, CategoryInputDto updateCategory) {

        if(categoryRepository.findById(id).isPresent()){

            Category category = categoryRepository.findById(id).get();

            Category cg1 = transferToCategory(updateCategory);
            cg1.setId(category.getId());

            categoryRepository.save(cg1);

            return CategoryMapper.transferToDto(cg1);

        }
        else {
            throw new RecordNotFoundException("Het categorienummer:" + id + " is onbekend");
        }
    }
}
