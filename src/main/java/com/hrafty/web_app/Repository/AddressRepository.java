package com.hrafty.web_app.Repository;

import com.hrafty.web_app.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    @Query("SELECT a.name_city FROM Address a")
    Set<String> findAllDistinctCityNames();
}
