package nl.novi.backend_it_helpdesk.mappers;

import nl.novi.backend_it_helpdesk.dtos.DetailInputDto;
import nl.novi.backend_it_helpdesk.dtos.DetailOutputDto;
import nl.novi.backend_it_helpdesk.models.Detail;

public class DetailMapper {

    public static DetailOutputDto transferToDto(Detail detail){
        DetailOutputDto dto = new DetailOutputDto();

        dto.setId(detail.getId());
        dto.setTitle(detail.getTitle());
        dto.setDescription(detail.getDescription());
        dto.setPriority(detail.getPriority());
        dto.setType(detail.getType());

        return dto;

    }

    public static Detail transferToDetail(DetailInputDto dto){

        Detail detail = new Detail();

        detail.setDescription(dto.getDescription());
        detail.setTitle(dto.getTitle());
        detail.setPriority(dto.getPriority());
        detail.setType(dto.getType());

        return detail;

    }


}
