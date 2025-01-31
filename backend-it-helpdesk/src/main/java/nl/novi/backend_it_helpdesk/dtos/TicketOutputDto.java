package nl.novi.backend_it_helpdesk.dtos;

import nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum;
import nl.novi.backend_it_helpdesk.models.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketOutputDto {

    private Long id;
    private Date createdAt;
    private User createdBy;
    private Date closedAt;
    private PriorityTicketEnum priority;
    private CategoryOutputDto category;
    private DetailOutputDto detail;
    private FixOutputDto fix;
    List<Screenshot> screenshots = new ArrayList<>();


    public TicketOutputDto() {}

    public TicketOutputDto(Long id, Date createdAt, Date closedAt, PriorityTicketEnum priority, CategoryOutputDto category, DetailOutputDto detail, FixOutputDto fix, List<Screenshot> screenshots, User createdBy) {
        this.id = id;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
        this.priority = priority;
        this.category = category;
        this.detail = detail;
        this.fix = fix;
        this.screenshots = screenshots;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
