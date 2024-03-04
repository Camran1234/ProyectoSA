package com.spring.tiketsys.dto.entity;

import com.spring.tiketsys.dto.ParserEntity;
import com.spring.tiketsys.dto.model.SurveyDTO;
import jakarta.persistence.*;
import lombok.NonNull;

@Entity
@Table(name="Survey")
public class Survey implements ParserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idSurvey")
    private int idSurvey;
    @NonNull
    @OneToOne
    @JoinColumn(name = "ticketNumber", referencedColumnName = "ticketNumber")
    private Ticket ticketNumber;
    @NonNull
    private int satisfaction;
    @NonNull
    private int timeService;
    @NonNull
    private int qualityService;

    public Survey() {
    }
    public Survey(@NonNull Ticket ticketNumber, @NonNull int satisfaction, @NonNull int timeService, @NonNull int qualityService) {
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


    @Override
    public SurveyDTO parseToDTO() {
        return new SurveyDTO(
                getIdSurvey(),
                getTicketNumber().getTicketNumber(),
                getSatisfaction(),
                getTimeService(),
                getQualityService());
    }

    @Override
    public Survey parseToEntity() {
        return this;
    }

    @Override
    public String toCSV() {
        StringBuffer string = new StringBuffer();
        string.append(getIdSurvey()+",");
        string.append(getTicketNumber().getTicketNumber()+",");
        string.append(getSatisfaction()+",");
        string.append(getTimeService()+",");
        string.append(getQualityService()+"\n");
        return string.toString();
    }
}
