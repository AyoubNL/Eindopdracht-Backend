package nl.novi.backend_it_helpdesk.services;

import jakarta.validation.Valid;
import nl.novi.backend_it_helpdesk.dtos.FixInputDto;
import nl.novi.backend_it_helpdesk.dtos.FixOutputDto;
import nl.novi.backend_it_helpdesk.exceptions.RecordNotFoundException;
import nl.novi.backend_it_helpdesk.mappers.FixMapper;
import nl.novi.backend_it_helpdesk.models.Fix;
import nl.novi.backend_it_helpdesk.repositories.FixRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FixService {

    final private FixRepository fixRepository;

    public FixService(FixRepository fixRepository) {
        this.fixRepository = fixRepository;
    }

    public FixOutputDto getFixById(Long id) {

        Optional<Fix> fix = fixRepository.findById(id);

        if (fix.isPresent()) {
            FixOutputDto dto = FixMapper.transferToDto(fix.get());
            return dto;
        } else {
            throw new RecordNotFoundException("Het detailnummer:" + id + " is onbekend");

        }
    }

    public List<FixOutputDto> getAllFixes() {

        List<Fix> fixes = fixRepository.findAll();
        List<FixOutputDto> dtos = new ArrayList<>();

        for(Fix fix : fixes){
            dtos.add(FixMapper.transferToDto(fix));
        }

        return dtos;

    }

    public FixOutputDto addFix(@Valid FixInputDto fix) {

    Fix fx = FixMapper.transferToFix(fix);

    fixRepository.save(fx);

    }
}