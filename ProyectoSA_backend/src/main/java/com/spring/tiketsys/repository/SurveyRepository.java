package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Integer> {
}
