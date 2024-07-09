package com.buyme.buymeEcom.repository;

import com.buyme.buymeEcom.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Integer> {

   public List<Country> findAllByOrderByNameAsc();
   @Query("SELECT c FROM Country c LEFT JOIN FETCH c.states WHERE c.id = :id")
   Country findByIdWithStates(@Param("id") Integer id);

}
