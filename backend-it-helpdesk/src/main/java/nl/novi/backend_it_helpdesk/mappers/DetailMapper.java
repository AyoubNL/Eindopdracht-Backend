package nl.novi.backend_it_helpdesk.mappers;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import nl.novi.backend_it_helpdesk.dtos.DetailOutputDto;
import nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum;
import nl.novi.backend_it_helpdesk.enums.TypeTicketEnum;
import nl.novi.backend_it_helpdesk.models.Detail;
import nl.novi.backend_it_helpdesk.models.Ticket;

import java.util.ArrayList;
import java.util.List;

public class DetailMapper {

    public static DetailOutputDto transferToDto(Detail detail){
        DetailOutputDto dto = new DetailOutputDto();

        dto.setId(detail.getId());
        dto.setTitle(detail.getTitle());
        dto.setDescription(detail.getDescription());
        dto.setPriority(detail.getPriority());
        dto.setType(detail.getType());

        if(detail.getTickets() != null){
            dto.setTickets(detail.getTickets());
        }




    }


}
