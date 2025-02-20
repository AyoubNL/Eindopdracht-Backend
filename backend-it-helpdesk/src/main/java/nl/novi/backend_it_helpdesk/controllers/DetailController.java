package nl.novi.backend_it_helpdesk.controllers;

import nl.novi.backend_it_helpdesk.dtos.DetailOutputDto;
import nl.novi.backend_it_helpdesk.services.DetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/details")
public class DetailController {

    final private DetailService detailService;

    public DetailController(DetailService detailService) {
        this.detailService = detailService;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<DetailOutputDto> getDetail(@PathVariable Long id) {
//
//        DetailOutputDto detail = detailService.getDetailById(id);
//
//        return ResponseEntity.ok().body(detail);
//
//    }




}
