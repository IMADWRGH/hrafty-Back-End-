package com.hrafty.web_app.Repository;

import com.hrafty.web_app.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllBySellerId(Long id);
   @Query("SELECT p.category FROM  Product p")
   Set<String> findAllCategories();

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.images WHERE p.seller.id = :id")
    List<Product> findAllBySellerIdWithImages(@Param("id") Long id);
    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.images ")
    List<Product> findAllWithImages();
    List<Product> findAllByCategory(String category);
}
