package nl.novi.backend_it_helpdesk.dtos;

import nl.novi.backend_it_helpdesk.enums.StatusTicketEnum;

public class FixOutputDto {

    private Long id;
    private String solution;
    private String feedback;
    private StatusTicketEnum status;

    public FixOutputDto(){}

    public FixOutputDto(Long id, String solution, String feedback, StatusTicketEnum status) {
        this.id = id;
        this.solution = solution;
        this.feedback = feedback;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public StatusTicketEnum getStatus() {
        return status;
    }

    public void setStatus(StatusTicketEnum status) {
        this.status = status;
    }
}
