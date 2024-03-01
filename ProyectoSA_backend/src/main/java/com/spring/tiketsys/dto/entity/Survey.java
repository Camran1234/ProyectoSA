package com.spring.tiketsys.dto.entity;

import jakarta.persistence.*;
import lombok.NonNull;

@Entity
@Table(name="Survey")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name="idSurvey")
    private int idSurvey;
    @NonNull
    @OneToOne
    @JoinColumn(name = "ticketNumber", referencedColumnName = "idTicketNumber")
    private Ticket ticketNumber;
    @NonNull
    private int satisfaction;
    @NonNull
    private int timeService;
    @NonNull
    private int qualityService;

    public Survey() {
    }
    public Survey(@NonNull int idSurvey, @NonNull Ticket ticketNumber, @NonNull int satisfaction, @NonNull int timeService, @NonNull int qualityService) {
        this.idSurvey = idSurvey;
        this.ticketNumber = ticketNumber;
        this.satisfaction = satisfaction;
        this.timeService = timeService;
        this.qualityService = qualityService;
    }

    public int getIdSurvey() {
        return idSurvey;
    }

    public void setIdSurvey(int idSurvey) {
        this.idSurvey = idSurvey;
    }

    public Ticket getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(Ticket ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public int getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(int satisfaction) {
        this.satisfaction = satisfaction;
    }

    public int getTimeService() {
        return timeService;
    }

    public void setTimeService(int timeService) {
        this.timeService = timeService;
    }

    public int getQualityService() {
        return qualityService;
    }

    public void setQualityService(int qualityService) {
        this.qualityService = qualityService;
    }
}
