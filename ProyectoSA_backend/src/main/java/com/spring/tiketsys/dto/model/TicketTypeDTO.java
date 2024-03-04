package com.spring.tiketsys.dto.model;

public class TicketTypeDTO {
    private int idTicketProblem;
    private String type;

    public TicketTypeDTO() {
    }

    public TicketTypeDTO(int idTicketProblem, String type) {
        this.idTicketProblem = idTicketProblem;
        this.type = type;
    }

    public int getIdTicketProblem() {
        return idTicketProblem;
    }

    public void setIdTicketProblem(int idTicketProblem) {
        this.idTicketProblem = idTicketProblem;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
