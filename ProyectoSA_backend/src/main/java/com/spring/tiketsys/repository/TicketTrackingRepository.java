package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.TicketTracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketTrackingRepository extends JpaRepository<TicketTracking, Integer> {
}
