package com.spring.tiketsys.dto.model;

public class SurveyDTO {

    private int idSurvey;
    private int ticketNumber;
    private int satisfaction;
    private int timeService;
    private int qualityService;

    public SurveyDTO(int idSurvey, int ticketNumber, int satisfaction, int timeService, int qualityService) {
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

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
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
