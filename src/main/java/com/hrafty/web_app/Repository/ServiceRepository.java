package com.hrafty.web_app.Repository;

import com.hrafty.web_app.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Repository
public interface ServiceRepository extends JpaRepository<Service,Long> {

    List<Service> findAllBySellerId(Long id);
    List<Service> findAllByNameAndCategory(String name,String category);

    List<Service> findAllByCategory(String category);

    @Query("SELECT s.category FROM Service s")
    Set<String> findAllCategories();
}
