package nl.novi.backend_it_helpdesk.services;

import jakarta.validation.Valid;
import nl.novi.backend_it_helpdesk.dtos.DetailInputDto;
import nl.novi.backend_it_helpdesk.dtos.DetailOutputDto;
import nl.novi.backend_it_helpdesk.exceptions.RecordNotFoundException;
import nl.novi.backend_it_helpdesk.mappers.DetailMapper;
import nl.novi.backend_it_helpdesk.models.Detail;
import nl.novi.backend_it_helpdesk.repositories.DetailRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DetailService {

    final private DetailRepository detailRepository;

    public DetailService(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    public DetailOutputDto getDetailById(String id) {

        Optional<Detail> detail = detailRepository.findById(id);

        if (detail.isPresent()) {
            DetailOutputDto dto = DetailMapper.transferToDto(detail.get());
            return dto;
        } else {
            throw new RecordNotFoundException("Het detailnummer:" + id + " is onbekend");
        }

    }

    public List<DetailOutputDto> getAllDetails() {

        List<Detail> details = detailRepository.findAll();
        List<DetailOutputDto> dtos = new ArrayList<>();

        for (Detail detail : details) {
            dtos.add(DetailMapper.transferToDto(detail));
        }

        return dtos;

    }

    public DetailOutputDto addDetail(DetailInputDto dto) {

        Detail dl = DetailMapper.transferToDetail(dto);
        detailRepository.save(dl);

        return DetailMapper.transferToDto(dl);

    }

    public void deleteDetail(String id) {
        detailRepository.deleteById(id);
    }

    public DetailOutputDto updateDetail(String id, @Valid DetailInputDto updateDetail) {

        Detail dl = detailRepository.findById(id).get();
        Detail dl1 = DetailMapper.transferToDetail(updateDetail);

        if (detailRepository.findById(id).isPresent()) {

            dl1.setId(dl.getId());

            if (dl1.getType() == null) {
                dl1.setType(dl.getType());
            }
            if (dl1.getDescription() == null) {
                dl1.setDescription(dl.getDescription());
            }
            if (dl1.getTitle() == null) {
                dl1.setTitle(dl.getTitle());
            }

            detailRepository.save(dl1);
            return DetailMapper.transferToDto(dl1);
        } else {
            throw new RecordNotFoundException("Het detailnummer: " + id + " is onbekend");
        }

    }

}

