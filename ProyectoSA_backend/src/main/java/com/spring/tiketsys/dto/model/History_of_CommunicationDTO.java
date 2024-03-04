package com.spring.tiketsys.dto.model;

import java.util.Date;

public class History_of_CommunicationDTO {
    private int idHistory;
    private int ticketNumber;
    private Date dateTimeContacted;
    private String sent;
    private String received;
    private String description;

    public History_of_CommunicationDTO() {
    }

    public History_of_CommunicationDTO(int idHistory, int ticketNumber, Date dateTimeContacted, String sent, String received, String description) {
        this.idHistory = idHistory;
        this.ticketNumber = ticketNumber;
        this.dateTimeContacted = dateTimeContacted;
        this.sent = sent;
        this.received = received;
        this.description = description;
    }

    public int getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(int idHistory) {
        this.idHistory = idHistory;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Date getDateTimeContacted() {
        return dateTimeContacted;
    }

    public void setDateTimeContacted(Date dateTimeContacted) {
        this.dateTimeContacted = dateTimeContacted;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
