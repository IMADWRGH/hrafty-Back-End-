package com.hrafty.web_app.Repository;

import com.hrafty.web_app.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
