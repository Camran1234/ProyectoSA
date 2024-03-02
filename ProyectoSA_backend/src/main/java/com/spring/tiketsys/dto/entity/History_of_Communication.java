package com.spring.tiketsys.dto.entity;

import com.spring.tiketsys.dto.ParserEntity;
import com.spring.tiketsys.dto.entity.compundkeys.History_of_CommunicationId;
import com.spring.tiketsys.dto.model.History_of_CommunicationDTO;
import jakarta.persistence.*;
import lombok.NonNull;

import java.util.Date;

@Entity
@IdClass(History_of_CommunicationId.class)
@Table(name="History_of_Communication")
public class History_of_Communication implements ParserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHistory")
    private int idHistory;
    @Id
    @NonNull
    private int ticketNumber;
    @NonNull
    @Column(name = "dateTimeContacted", columnDefinition = "DATETIME")
    private Date dateTimeContacted;
    @Column(name="sent", columnDefinition = "TEXT")
    private String sent;
    @Column(name="received", columnDefinition = "TEXT")
    private String received;
    @NonNull
    @Column(name="description", columnDefinition = "TEXT")
    private String description;

    public History_of_Communication() {
    }

    public History_of_Communication(@NonNull int ticketNumber, @NonNull Date dateTimeContacted, String sent, String received, @NonNull String description) {
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

    @Override
    public History_of_CommunicationDTO parseToDTO() {
        //int idHistory, int ticketNumber, Date dateTimeContacted, String sent, String received, String description
        return new History_of_CommunicationDTO(
                this.getIdHistory(),
                this.getTicketNumber(),
                this.getDateTimeContacted(),
                this.getSent(),
                this.getRecieved(),
                this.getDescription()
        );
    }

    @Override
    public History_of_Communication parseToEntity() {
        return this;
    }

    @Override
    public String toCSV() {
        StringBuffer string = new StringBuffer();
        string.append(getIdHistory());
        string.append(getTicketNumber());
        string.append(getDateTimeContacted());
        string.append(getSent());
        string.append(getRecieved());
        string.append(getDescription());
        return string.toString();
    }
}
