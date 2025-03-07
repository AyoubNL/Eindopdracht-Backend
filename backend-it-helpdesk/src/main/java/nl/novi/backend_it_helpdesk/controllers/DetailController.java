package nl.novi.backend_it_helpdesk.controllers;

import jakarta.validation.Valid;
import nl.novi.backend_it_helpdesk.dtos.DetailInputDto;
import nl.novi.backend_it_helpdesk.dtos.DetailOutputDto;
import nl.novi.backend_it_helpdesk.repositories.DetailRepository;
import nl.novi.backend_it_helpdesk.services.DetailService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/details")
public class DetailController {

    final private DetailService detailService;
    private final DetailRepository detailRepository;

    public DetailController(DetailService detailService, DetailRepository detailRepository) {
        this.detailService = detailService;
        this.detailRepository = detailRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailOutputDto> getDetail(@PathVariable Long id) {

        if (!detailRepository.existsById(id)) {

            return ResponseEntity.notFound().build();

        } else {

            DetailOutputDto detail = detailService.getDetailById(id);

            return ResponseEntity.ok().body(detail);

        }

    }

    @GetMapping
    public ResponseEntity<List<DetailOutputDto>> getAllDetails() {

        List<DetailOutputDto> dtos = detailService.getAllDetails();

        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping
    public ResponseEntity<Object> addDetail(@Valid @RequestBody DetailInputDto detail) {

        try {

            DetailOutputDto dto = detailService.addDetail(detail);

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
    public ResponseEntity<Object> deleteDetail(@PathVariable Long id) {

        detailService.deleteDetail(id);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<DetailOutputDto> updateDetail(@PathVariable Long id, @Valid @RequestBody DetailInputDto updateDetail) {

        DetailOutputDto outputDto = detailService.updateDetail(id, updateDetail);

        return ResponseEntity.ok().body(outputDto);

    }


}
