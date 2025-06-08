package nl.novi.backend_it_helpdesk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.backend_it_helpdesk.dtos.DetailInputDto;
import nl.novi.backend_it_helpdesk.dtos.DetailOutputDto;
import nl.novi.backend_it_helpdesk.exceptions.NotAuthorizedUserException;
import nl.novi.backend_it_helpdesk.exceptions.RecordNotFoundException;
import nl.novi.backend_it_helpdesk.mappers.DetailMapper;
import nl.novi.backend_it_helpdesk.models.Detail;
import nl.novi.backend_it_helpdesk.models.Ticket;
import nl.novi.backend_it_helpdesk.repositories.DetailRepository;
import nl.novi.backend_it_helpdesk.repositories.TicketRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DetailService {

    final private DetailRepository detailRepository;
    final private TicketRepository ticketRepository;

    public DetailService(DetailRepository detailRepository, TicketRepository ticketRepository) {
        this.detailRepository = detailRepository;
        this.ticketRepository = ticketRepository;
    }

    private static final String apiUrl = "https://api.mymemory.translated.net/get?q=BESCHRIJVING&langpair=nl|en";

    public DetailOutputDto getDetailById(String id) {

        Optional<Detail> detail = detailRepository.findById(id.toUpperCase());

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

        if(detailRepository.existsById(id.toUpperCase())) {

            detailRepository.deleteById(id.toUpperCase());
        }
        else{
            throw new EmptyResultDataAccessException("Onbekende entiteit", 1);
        }

    }

    public DetailOutputDto updateDetail(String id, DetailInputDto updateDetail) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();

        Detail dl = detailRepository.findById(id.toUpperCase()).get();
        Detail dl1 = DetailMapper.transferToDetail(updateDetail);

        if (detailRepository.findById(id.toUpperCase()).isPresent()) {

            Ticket tk = ticketRepository.findById(id.toUpperCase()).get();

            if (currentUser.equals(tk.getUser())){

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

            }
            else{
                throw new NotAuthorizedUserException("De gebruiker " + currentUser+ " is niet gemachtigd, om aanpassingen te doen aan " +id);}

        } else {
            throw new RecordNotFoundException("Het detailnummer: " + id + " is onbekend");
        }

    }

    public DetailOutputDto translateDetail(String id) throws JsonProcessingException {

        Optional<Detail> detail = detailRepository.findById(id.toUpperCase());

        if (detail.isPresent()) {

            String beschrijving = detail.get().getDescription();

            try {
                String finalAPI = apiUrl.replace("BESCHRIJVING", beschrijving);
                RestTemplate restTemplate = new RestTemplate();
                String trans = restTemplate.getForObject(finalAPI, String.class);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonRoot = mapper.readTree(trans);

                String transValue = jsonRoot.get("responseData").get("translatedText").toString().replace("\"", "");

                detail.get().setDescription(transValue);
            }
            catch (RestClientException e) {
              throw new RestClientException("De vertaling is mislukt" + e.getMessage());
            }
            detailRepository.save(detail.get());

            return DetailMapper.transferToDto(detail.get());
        }
        else {
            throw new RecordNotFoundException("Het detailnummer: " + id + " is onbekend");
        }



    }
}

