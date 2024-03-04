package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {

}
