package com.hrafty.web_app.Repository;

import com.hrafty.web_app.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<com.hrafty.web_app.entities.Customer,Long> {

    boolean existsByUserId(Long userId);

    @Query("""
    SELECT c
    FROM Customer c
    LEFT JOIN FETCH c.user
    LEFT JOIN FETCH c.panel
    WHERE c.id = :id
""")
    Optional<Customer> findCustomerWithRelations(@Param("id") Long id);

}
