package com.spring.tiketsys.dto.entity;

import com.spring.tiketsys.dto.entity.compundkeys.TicketElementId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

@Entity
@IdClass(TicketElementId.class)
@Table(name="TicketElement")
@Data
public class TicketElement {
    @Id
    @NonNull
    @Column (name = "idTicketElement")
    private int idTicketElement;
    @Id
    @NonNull
    @Column(name="ticketNumber")
    private int ticketNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticketNumber", referencedColumnName = "ticketNumber", insertable = false, updatable = false)
    private Ticket ticket;

    @Column(name="url", columnDefinition = "TEXT")
    private String url;

    public TicketElement() {
    }

    public TicketElement(int idTicketElement, @NonNull int ticketNumber, String url) {
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

    public Ticket getTicketNumberObject() {
        return ticket;
    }

    public void setTicketNumberObject(Ticket ticketNumberObject) {
        this.ticket = ticketNumberObject;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
