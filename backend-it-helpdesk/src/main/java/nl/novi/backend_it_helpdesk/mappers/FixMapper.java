package nl.novi.backend_it_helpdesk.mappers;

import nl.novi.backend_it_helpdesk.dtos.FixInputDto;
import nl.novi.backend_it_helpdesk.dtos.FixOutputDto;
import nl.novi.backend_it_helpdesk.models.Fix;

public class FixMapper {

    public static FixOutputDto transferToDto(Fix fix){
        FixOutputDto dto = new FixOutputDto();

        dto.setId(fix.getId());
        dto.setSolution(fix.getSolution());
        dto.setFeedback(fix.getFeedback());
        dto.setStatus(fix.getStatus());

        return dto;

    }

    public static Fix transferToFix(FixInputDto dto){

        Fix fix = new Fix();

        fix.setSolution(dto.getSolution());
        fix.setFeedback(dto.getFeedback());
        fix.setStatus(dto.getStatus());

        return fix;




    }


}
