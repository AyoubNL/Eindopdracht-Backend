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

    public FixOutputDto getFixById(String id) {

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

        for (Fix fix : fixes) {
            dtos.add(FixMapper.transferToDto(fix));
        }

        return dtos;

    }

    public FixOutputDto addFix(@Valid FixInputDto fix) {

        Fix fx = FixMapper.transferToFix(fix);
        fixRepository.save(fx);

        return FixMapper.transferToDto(fx);
    }

    public void deleteDetail(String id) {

        fixRepository.deleteById(id);

    }

    public FixOutputDto updateFix(String id, @Valid FixInputDto updateFix) {

        Fix fx = fixRepository.findById(id).get();
        Fix fx1 = FixMapper.transferToFix(updateFix);

        if (fixRepository.findById(id).isPresent()) {

            fx1.setId(fx.getId());

            if (fx1.getSolution() == null) {
                fx1.setSolution(fx.getSolution());
            }
            if (fx1.getFeedback() == null) {
                fx1.setFeedback(fx.getFeedback());
            }

            if (fx1.getStatus() == null) {
                fx1.setStatus(fx.getStatus());
            }

            fixRepository.save(fx1);
            return FixMapper.transferToDto(fx1);
        }
        throw new RecordNotFoundException("Het fixnummer:" + id + " is onbekend");


    }
}