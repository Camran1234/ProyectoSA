package com.spring.tiketsys.dto.entity;

import com.spring.tiketsys.dto.entity.compundkeys.History_of_CommunicationId;
import jakarta.persistence.*;
import lombok.NonNull;

import java.util.Date;

@Entity
@IdClass(History_of_CommunicationId.class)
@Table(name="History_of_Communication")
public class History_of_Communication {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHistory")
    private int idHistory;
    @Id
    @NonNull
    private int ticketNumber;
    @NonNull
    @Column(name = "dateTimeContacted", columnDefinition = "DATETIME")
    private Date dateTimeContacted;
    @NonNull
    @Column(name="sent", columnDefinition = "TEXT")
    private String sent;
    @NonNull
    @Column(name="received", columnDefinition = "TEXT")
    private String received;
    @NonNull
    @Column(name="description", columnDefinition = "TEXT")
    private String description;

    public History_of_Communication() {
    }

    public History_of_Communication(@NonNull int idHistory, @NonNull int ticketNumber, @NonNull Date dateTimeContacted, @NonNull String sent, @NonNull String received, @NonNull String description) {
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

    public String getRecieved() {
        return received;
    }

    public void setRecieved(String received) {
        this.received = received;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
