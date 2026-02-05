package com.hrafty.web_app.Repository;

import com.hrafty.web_app.entities.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {
    Seller findByUserId(Long id);

    Page<Seller> findAll(Pageable pageable);
}
