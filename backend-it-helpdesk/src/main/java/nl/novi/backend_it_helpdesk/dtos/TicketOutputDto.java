package nl.novi.backend_it_helpdesk.dtos;

import nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum;
import nl.novi.backend_it_helpdesk.models.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketOutputDto {

    private Long id;
    private LocalDateTime createdAt;
    private User createdBy;
    private Date closedAt;
    private PriorityTicketEnum priority;
    private CategoryOutputDto categoryDto;
    private DetailOutputDto detailDto;
    private FixOutputDto fixDto;
    List<Screenshot> screenshots = new ArrayList<>();

    public TicketOutputDto() {}

    public TicketOutputDto(Long id, LocalDateTime createdAt, User createdBy, Date closedAt, PriorityTicketEnum priority, CategoryOutputDto categoryDto, DetailOutputDto detailDto, FixOutputDto fixDto, List<Screenshot> screenshots) {
        this.id = id;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.closedAt = closedAt;
        this.priority = priority;
        this.categoryDto = categoryDto;
        this.detailDto = detailDto;
        this.fixDto = fixDto;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
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

    public CategoryOutputDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryOutputDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public DetailOutputDto getDetailDto() {
        return detailDto;
    }

    public void setDetailDto(DetailOutputDto detailDto) {
        this.detailDto = detailDto;
    }

    public FixOutputDto getFixDto() {
        return fixDto;
    }

    public void setFixDto(FixOutputDto fixDto) {
        this.fixDto = fixDto;
    }

    public List<Screenshot> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<Screenshot> screenshots) {
        this.screenshots = screenshots;
    }
}
