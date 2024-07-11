package com.hrafty.web_app.controller;

import com.hrafty.web_app.dto.PanelDTO;
import com.hrafty.web_app.services.PanelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/panel")
public class PanelController {
    private final PanelService panel;

    public PanelController(PanelService panel) {
        this.panel = panel;
    }

    @PostMapping(path = "/add")
    ResponseEntity<PanelDTO> createPanel(@RequestBody PanelDTO panelDTO){
        PanelDTO panelDTO1= panel.create(panelDTO);
        System.out.println(panelDTO1);
        return ResponseEntity.status(HttpStatus.CREATED).body(panelDTO1);
    }

    @GetMapping(path="/getAll/{id}")
    ResponseEntity<List<PanelDTO>> getAllPanels(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(panel.getPanelsByCustomer(id));
    }

    @DeleteMapping(path = "/delete/{id}")
    ResponseEntity<String> deletePanal(@PathVariable("id") Long id){
        panel.deletePanel(id);
        return new ResponseEntity<>("Panel successfully deleted",HttpStatus.OK);
    }


}
