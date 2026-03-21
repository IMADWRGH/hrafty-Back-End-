package com.hrafty.web_app.Repository;

import com.hrafty.web_app.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> findByProductId(Long id);
    List<Image> findByServiceId(Long id);
}
