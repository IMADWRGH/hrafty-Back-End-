package com.hrafty.web_app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<com.hrafty.web_app.entities.Customer,Long> {
}
