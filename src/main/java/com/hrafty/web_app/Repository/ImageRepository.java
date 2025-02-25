package com.hrafty.web_app.Repository;

import com.hrafty.web_app.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> findByProductId(Long id);
    List<Image> findByServiceId(Long id);
}
