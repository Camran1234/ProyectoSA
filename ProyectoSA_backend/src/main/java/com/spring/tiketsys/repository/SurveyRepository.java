package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.Survey;
import com.spring.tiketsys.dto.entity.User;
import com.spring.tiketsys.dto.model.SurveyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Integer> {

    @Query(nativeQuery = true, value =
            "SELECT * FROM Survey")
    List<SurveyDTO> getSurveys();
}
