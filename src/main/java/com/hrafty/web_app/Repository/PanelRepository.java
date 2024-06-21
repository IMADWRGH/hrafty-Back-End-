package com.hrafty.web_app.Repository;

import com.hrafty.web_app.entities.Panel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanelRepository extends JpaRepository<Panel,Long> {
}
