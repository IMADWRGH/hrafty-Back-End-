package com.hrafty.web_app.Repository;

import com.hrafty.web_app.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
