package nl.novi.backend_it_helpdesk.mappers;

import nl.novi.backend_it_helpdesk.dtos.ScreenshotOutputDto;
import nl.novi.backend_it_helpdesk.dtos.TicketOutputDto;
import nl.novi.backend_it_helpdesk.models.Screenshot;
import nl.novi.backend_it_helpdesk.models.Ticket;

import java.util.ArrayList;
import java.util.List;

public class ScreenshotMapper {

    public static ScreenshotOutputDto transferToDto(Screenshot screenshot) {

        ScreenshotOutputDto dto = new ScreenshotOutputDto();

        dto.setId(screenshot.getId());
        dto.setTitle(screenshot.getTitle());
        dto.setContentType(screenshot.getContentType());
        dto.setUrl(screenshot.getUrl());
        dto.setSize(screenshot.getSize());

        return dto;
    }

    public static List<ScreenshotOutputDto> transferScreenshotListToDtoList(List<Screenshot> screenshots) {

        List<ScreenshotOutputDto> screenshotDtoList = new ArrayList<>();

        for(Screenshot screenshot : screenshots) {
            ScreenshotOutputDto dto = transferToDto(screenshot);

            if(screenshot.getId() != null) {
                dto.setId(screenshot.getId());
            }

            if(screenshot.getTitle() != null) {
                dto.setTitle(screenshot.getTitle());
            }

            if(screenshot.getContentType() != null) {
                dto.setContentType(screenshot.getContentType());
            }

            if(screenshot.getUrl() != null) {
                dto.setUrl(screenshot.getUrl());
            }

            if(screenshot.getSize() != null) {
                dto.setSize(screenshot.getSize());
            }
            screenshotDtoList.add(dto);
            }


        return screenshotDtoList;

        }

    }


