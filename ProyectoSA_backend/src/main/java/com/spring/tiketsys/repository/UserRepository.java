package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.User;
import com.spring.tiketsys.dto.model.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(nativeQuery = true, value =
            "SELECT * FROM User WHERE username= :user LIMIT 1")
    User getUserByUserName(@Param("user") String user);
}
