package com.hrafty.web_app.controller;


import com.hrafty.web_app.services.PanelService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {
    private final PanelService panel;

    public CustomerController(PanelService panel) {
        this.panel = panel;
    }

//    @PostMapping(path = "/add")
//    public ResponseEntity<PanelDTO> createService(@RequestBody PanelDTO panelDTO){
//        PanelDTO panel1= panel.createPanel(panelDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(panel1);
//    }

//    @GetMapping(path ="/getAll/{id}")
//    public ResponseEntity<List<ServiceDTO>> getAllServices(@PathVariable("id") Long id) {
//        List<ServiceDTO> services = service.getAllServices(id);
//        return new ResponseEntity<>(services, HttpStatus.OK);
//    }
//
//    @PutMapping(path = "/update/{id}")
//    public ResponseEntity<ServiceDTO> updateService(@PathVariable("id") Long id,@RequestBody ServiceDTO serviceDTO){
//        service.updateService(id,serviceDTO);
//        return  ResponseEntity.status(HttpStatus.OK).body(serviceDTO);
//    }
//    @DeleteMapping(path = "/delete/{id}")
//    public ResponseEntity<String> deleteService(@PathVariable("id") Long id){
//        service.deleteService(id);
//        return new ResponseEntity<>("Serive successfully deleted (: ",HttpStatus.OK);
//    }
}
