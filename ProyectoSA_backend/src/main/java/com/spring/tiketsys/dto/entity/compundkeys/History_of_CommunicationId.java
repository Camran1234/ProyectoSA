package com.spring.tiketsys.dto.entity.compundkeys;

import jakarta.persistence.Entity;

import java.io.Serializable;

public class History_of_CommunicationId implements Serializable {
    private int idHistory;
    private int ticketNumber;

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
}
