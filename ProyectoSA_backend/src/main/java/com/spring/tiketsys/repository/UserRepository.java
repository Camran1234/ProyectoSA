package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
