package com.spring.tiketsys.dto.entity;

import com.spring.tiketsys.dto.entity.compundkeys.TicketElementId;
import jakarta.persistence.*;
import lombok.NonNull;

@Entity
@IdClass(TicketElementId.class)
@Table(name="TicketElement")
public class TicketElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "idTicketElement")
    private int idTicketElement;
    @Id
    @NonNull
    @Column(name="ticketNumber")
    private int ticketNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @NonNull
    @JoinColumn(name = "ticketNumber", referencedColumnName = "ticketNumber", insertable = false, updatable = false)
    private Ticket ticketNumberObject;

    @Column(name="url", columnDefinition = "TEXT")
    private String url;

    public TicketElement() {
    }

    public TicketElement(@NonNull int idTicketElement, @NonNull int ticketNumber, @NonNull Ticket ticketNumberObject, String url) {
        this.idTicketElement = idTicketElement;
        this.ticketNumber = ticketNumber;
        this.ticketNumberObject = ticketNumberObject;
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
        return ticketNumberObject;
    }

    public void setTicketNumberObject(Ticket ticketNumberObject) {
        this.ticketNumberObject = ticketNumberObject;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
