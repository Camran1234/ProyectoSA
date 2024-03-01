package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.History_of_Communication;
import com.spring.tiketsys.dto.entity.compundkeys.History_of_CommunicationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface History_of_CommunicationRepository extends JpaRepository<History_of_Communication, History_of_CommunicationId> {
}
