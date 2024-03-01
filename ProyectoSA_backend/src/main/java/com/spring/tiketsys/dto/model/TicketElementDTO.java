package com.spring.tiketsys.dto.model;

public class TicketElementDTO {
    private int idTicketElement;
    private int ticketNumber;
    private String url;

    public TicketElementDTO() {
    }

    public TicketElementDTO(int idTicketElement, int ticketNumber, String url) {
        this.idTicketElement = idTicketElement;
        this.ticketNumber = ticketNumber;
        this.url = url;
    }

    public int getIdTicketElement() {
        return idTicketElement;
    }

    public void setIdTicketElement(int idTicketElement) {
        this.idTicketElement = idTicketElement;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
