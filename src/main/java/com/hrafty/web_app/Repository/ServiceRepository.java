package com.hrafty.web_app.Repository;

import com.hrafty.web_app.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service,Long> {

    List<Service> findAllBySellerId(Long id);
}
