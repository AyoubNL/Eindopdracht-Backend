package nl.novi.backend_it_helpdesk.services;

import nl.novi.backend_it_helpdesk.dtos.DetailOutputDto;
import nl.novi.backend_it_helpdesk.models.Detail;
import nl.novi.backend_it_helpdesk.repositories.DetailRepository;
import org.springframework.stereotype.Service;

@Service
public class DetailService {

    final private DetailRepository detailRepository;

    public DetailService(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

//    public DetailOutputDto getDetailById(Long id) {
//
//        if(detailRepository.findById(id).isPresent()) {
//            Detail detail = detailRepository.findById(id).get();
//        }
//
//
//    }
//

}
