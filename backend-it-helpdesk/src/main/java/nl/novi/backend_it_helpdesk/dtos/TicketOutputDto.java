package nl.novi.backend_it_helpdesk.dtos;

import nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum;
import nl.novi.backend_it_helpdesk.models.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketOutputDto {

    private Long id;
    private UserOutputDto createdBy;
    private LocalDateTime createdAt;
    private PriorityTicketEnum priority;
    private CategoryOutputDto category;
    private DetailOutputDto detail;
    private FixOutputDto fix;
    List<Screenshot> screenshots = new ArrayList<>();
    private Date closedAt;


    public TicketOutputDto() {}

    public TicketOutputDto(Long id, LocalDateTime createdAt, UserOutputDto createdBy, Date closedAt, PriorityTicketEnum priority, CategoryOutputDto category, DetailOutputDto detail, FixOutputDto fix, List<Screenshot> screenshots) {
        this.id = id;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.closedAt = closedAt;
        this.priority = priority;
        this.category = category;
        this.detail = detail;
        this.fix = fix;
        this.screenshots = screenshots;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public UserOutputDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserOutputDto createdBy) {
        this.createdBy = createdBy;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    public PriorityTicketEnum getPriority() {
        return priority;
    }

    public void setPriority(PriorityTicketEnum priority) {
        this.priority = priority;
    }

    public CategoryOutputDto getCategory() {
        return category;
    }

    public void setCategory(CategoryOutputDto category) {
        this.category = category;
    }

    public DetailOutputDto getDetail() {
        return detail;
    }

    public void setDetail(DetailOutputDto detail) {
        this.detail = detail;
    }

    public FixOutputDto getFix() {
        return fix;
    }

    public void setFix(FixOutputDto fix) {
        this.fix = fix;
    }

    public List<Screenshot> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<Screenshot> screenshots) {
        this.screenshots = screenshots;
    }
}
