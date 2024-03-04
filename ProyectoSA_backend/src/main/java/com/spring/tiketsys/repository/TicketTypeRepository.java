package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketTypeRepository extends JpaRepository<TicketType, Integer> {
}
