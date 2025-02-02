package nl.novi.backend_it_helpdesk.dtos;

import nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum;
import nl.novi.backend_it_helpdesk.models.*;

import java.util.ArrayList;
import java.util.List;

public class TicketInputDto {

    private PriorityTicketEnum priority;
    private CategoryInputDto category;
    private DetailInputDto detail;
    private FixInputDto fix;
    List<Screenshot> screenshots = new ArrayList<>();

    public TicketInputDto(){}

    public TicketInputDto(PriorityTicketEnum priority, CategoryInputDto category, DetailInputDto detail, FixInputDto fix, User createdBy, List<Screenshot> screenshots) {
        this.priority = priority;
        this.category = category;
        this.detail = detail;
        this.fix = fix;
        this.screenshots = screenshots;
    }

    public PriorityTicketEnum getPriority() {
        return priority;
    }

    public void setPriority(PriorityTicketEnum priority) {
        this.priority = priority;
    }

    public CategoryInputDto getCategory() {
        return category;
    }

    public void setCategory(CategoryInputDto category) {
        this.category = category;
    }

    public DetailInputDto getDetail() {
        return detail;
    }

    public void setDetail(DetailInputDto detail) {
        this.detail = detail;
    }

    public FixInputDto getFix() {
        return fix;
    }

    public void setFix(FixInputDto fix) {
        this.fix = fix;
    }


    public List<Screenshot> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<Screenshot> screenshots) {
        this.screenshots = screenshots;
    }
}
