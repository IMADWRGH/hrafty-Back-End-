package com.hrafty.web_app.Repository;

import com.hrafty.web_app.entities.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllBySellerId(Long id);
   @Query("SELECT p.category FROM  Product p")
   Set<String> findAllCategories();

    List<Product> findAllByCategory(String category);
}
