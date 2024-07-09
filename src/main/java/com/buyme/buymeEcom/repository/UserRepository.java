package com.buyme.buymeEcom.repository;

import com.buyme.buymeEcom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
