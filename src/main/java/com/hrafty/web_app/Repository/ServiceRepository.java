package com.hrafty.web_app.Repository;

import com.hrafty.web_app.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ServiceRepository extends JpaRepository<Service,Long> {

    List<Service> findAllBySellerId(Long id);


    List<Service> findAllByCategory(String category);

    @Query("SELECT s.category FROM Service s")
    Set<String> findAllCategories();

    @Query("SELECT s FROM Service s JOIN s.seller se JOIN se.address a WHERE a.name_city = :city")
    List<Service> findAllByCity(@Param("city") String city);

    @Query("SELECT s FROM Service s JOIN s.seller se JOIN se.address a WHERE a.name_city=:city AND s.category=:category")
    List<Service> findAllByCityAndCategory(@Param("city") String city, @Param("category") String category);

    @Query("SELECT DISTINCT s FROM Service s LEFT JOIN FETCH s.images WHERE s.seller.id = :id")
    List<Service> findAllBySellerIdWithImages(@Param("id") Long id);

    @Query("SELECT DISTINCT s FROM Service s LEFT JOIN FETCH s.images")
    List<Service> findAllByWithImages();

}

