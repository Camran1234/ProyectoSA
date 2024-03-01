package com.spring.tiketsys.dto.entity.compundkeys;

import java.io.Serializable;

public class TicketElementId implements Serializable {
    private int idTicketElement;
    private int ticketNumber;

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
}
