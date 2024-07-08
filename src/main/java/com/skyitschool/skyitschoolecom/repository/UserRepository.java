package com.skyitschool.skyitschoolecom.repository;

import com.skyitschool.skyitschoolecom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
