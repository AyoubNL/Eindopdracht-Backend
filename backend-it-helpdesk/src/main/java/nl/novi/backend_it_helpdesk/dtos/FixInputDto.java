package nl.novi.backend_it_helpdesk.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nl.novi.backend_it_helpdesk.enums.StatusTicketEnum;

public class FixInputDto {

    @NotNull(message = "Solution is required")
    @Size(min=1, max=500, message = "Category too long (max 500 characters)")
    private String solution;
    private String feedback;
    private StatusTicketEnum status;

    public FixInputDto() {}

    public FixInputDto(String solution, String feedback, StatusTicketEnum status) {
        this.solution = solution;
        this.feedback = feedback;
        this.status = status;
    }

    public @NotNull(message = "Solution is required") @Size(min = 1, max = 500, message = "Category too long (max 500 characters)") String getSolution() {
        return solution;
    }

    public void setSolution(@NotNull(message = "Solution is required") @Size(min = 1, max = 500, message = "Category too long (max 500 characters)") String solution) {
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
