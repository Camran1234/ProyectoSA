package com.spring.tiketsys.service;

import com.spring.tiketsys.dto.entity.Survey;
import com.spring.tiketsys.dto.model.SurveyDTO;
import com.spring.tiketsys.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SurveyService {
    @Autowired
    private SurveyRepository surveyRepository;

    public void saveSurvey(Survey survey){
        surveyRepository.saveAndFlush(survey);
    }

    public List<Map<String,Object>> getSurveys(){
        return surveyRepository.getSurveys();
    }

    public String getCsv() {
        List<Survey> list = surveyRepository.findAll();
        StringBuffer string = new StringBuffer();
        for(Survey survey:list){
            string.append(survey.toCSV());
        }
        return string.toString();
    }
}
