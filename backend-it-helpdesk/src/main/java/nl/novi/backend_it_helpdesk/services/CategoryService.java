package nl.novi.backend_it_helpdesk.services;

import nl.novi.backend_it_helpdesk.dtos.CategoryInputDto;
import nl.novi.backend_it_helpdesk.dtos.CategoryOutputDto;
import nl.novi.backend_it_helpdesk.exceptions.NotAuthorizedUserException;
import nl.novi.backend_it_helpdesk.exceptions.RecordNotFoundException;
import nl.novi.backend_it_helpdesk.mappers.CategoryMapper;
import nl.novi.backend_it_helpdesk.models.Category;
import nl.novi.backend_it_helpdesk.models.Ticket;
import nl.novi.backend_it_helpdesk.repositories.CategoryRepository;
import nl.novi.backend_it_helpdesk.repositories.TicketRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static nl.novi.backend_it_helpdesk.mappers.CategoryMapper.*;

@Service
public class CategoryService {

    final private CategoryRepository categoryRepository;
    final private TicketRepository ticketRepository;

    public CategoryService(CategoryRepository categoryRepository, TicketRepository ticketRepository) {
        this.categoryRepository = categoryRepository;
        this.ticketRepository = ticketRepository;

    }

    public CategoryOutputDto getCategoryById(String id) {

        if (categoryRepository.findById(id.toUpperCase()).isPresent()) {
            Category category = categoryRepository.findById(id.toUpperCase()).get();
            return CategoryMapper.transferToDto(category);
        } else {
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

    public void deleteTicket(String id) {

        if (categoryRepository.existsById(id.toUpperCase())) {

            categoryRepository.deleteById(id.toUpperCase());
        } else {
            throw new EmptyResultDataAccessException("Onbekende entiteit", 1);
        }

    }

    public CategoryOutputDto updateCategory(String id, CategoryInputDto updateCategory) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();

        if (categoryRepository.findById(id.toUpperCase()).isPresent()) {

            Ticket tk = ticketRepository.findById(id.toUpperCase()).get();

            if (currentUser.equals(tk.getUser())){

                Category cg = categoryRepository.findById(id.toUpperCase()).get();
                Category cg1 = transferToCategory(updateCategory);
                cg1.setId(cg.getId());

                categoryRepository.save(cg1);

                return CategoryMapper.transferToDto(cg1);
            }
            else{
                throw new NotAuthorizedUserException("De gebruiker " + currentUser+ " is niet gemachtigd, om aanpassingen te doen aan " +id);}

        } else {
            throw new RecordNotFoundException("Het categorienummer:" + id + " is onbekend");
        }
    }
}
