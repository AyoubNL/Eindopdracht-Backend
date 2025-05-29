package nl.novi.backend_it_helpdesk.controllers;

import jakarta.validation.Valid;
import nl.novi.backend_it_helpdesk.dtos.*;
import nl.novi.backend_it_helpdesk.services.FixService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/fixes")
public class FixController {

    final private FixService fixService;

    public FixController(FixService fixService) {
        this.fixService = fixService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FixOutputDto> getFix(@PathVariable("id") String id) {

        FixOutputDto fix = fixService.getFixById(id);

        return ResponseEntity.ok().body(fix);

    }

    @GetMapping
    public ResponseEntity<List<FixOutputDto>> getAllFixes() {

        List<FixOutputDto> dtos = fixService.getAllFixes();

        return ResponseEntity.ok().body(dtos);

    }

    @PostMapping()
    public ResponseEntity<Object> addFix(@Valid @RequestBody FixInputDto fix) {

        try {
            FixOutputDto dto = fixService.addFix(fix);

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
    public ResponseEntity<Object> deleteFix(@PathVariable String id) {

        fixService.deleteFix(id);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<FixOutputDto> updateDetail(@PathVariable String id, @Valid @RequestBody FixInputDto updateFix) {

        FixOutputDto outputDto = fixService.updateFix(id, updateFix);

        return ResponseEntity.ok().body(outputDto);

    }



}
