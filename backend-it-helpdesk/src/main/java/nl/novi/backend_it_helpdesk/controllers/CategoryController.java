package nl.novi.backend_it_helpdesk.controllers;

import jakarta.validation.Valid;
import nl.novi.backend_it_helpdesk.dtos.CategoryInputDto;
import nl.novi.backend_it_helpdesk.dtos.CategoryOutputDto;
import nl.novi.backend_it_helpdesk.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryOutputDto> getCategory(@PathVariable("id") String id) {

        CategoryOutputDto category = categoryService.getCategoryById(id);

        return ResponseEntity.ok().body(category);

    }

    @GetMapping
    public ResponseEntity<List<CategoryOutputDto>> getAllCategories() {

        List<CategoryOutputDto> dtos = categoryService.getAllCategories();

        return ResponseEntity.ok().body(dtos);

    }

    @PostMapping()
    public ResponseEntity<Object> addCategory(@Valid @RequestBody CategoryInputDto category) {

        try {
            CategoryOutputDto dto = categoryService.addCategory(category);

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/" + dto.getId())
                    .buildAndExpand(dto.getId()).toUri();

            return ResponseEntity.created(uri).body(dto);
        } catch (Exception e) {

            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable String id){

        categoryService.deleteTicket(id);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable String id, @Valid @RequestBody CategoryInputDto updateCategory){

        CategoryOutputDto outputDto = categoryService.updateCategory(id, updateCategory);

        return ResponseEntity.ok().body(outputDto);



    }

}
