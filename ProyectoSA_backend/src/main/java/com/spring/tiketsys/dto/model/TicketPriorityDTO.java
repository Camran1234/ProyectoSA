package com.spring.tiketsys.dto.model;

public class TicketPriorityDTO {
    private int idTicketPriority;
    private String priority;

    public TicketPriorityDTO(int idTicketPriority, String priority) {
        this.idTicketPriority = idTicketPriority;
        this.priority = priority;
    }

    public TicketPriorityDTO() {
    }

    public int getIdTicketPriority() {
        return idTicketPriority;
    }

    public void setIdTicketPriority(int idTicketPriority) {
        this.idTicketPriority = idTicketPriority;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
