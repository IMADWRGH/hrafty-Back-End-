package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.ServiceDTO;

import java.util.List;

public interface Service {
    public ServiceDTO create(ServiceDTO serviceDTO);
    public List<ServiceDTO> getAllServices();
    public List<ServiceDTO> getAllServices(Long id);
    public void updateService(Long id , ServiceDTO serviceDTO);
    public ServiceDTO changeStatus(Boolean status, Long id);
    public void  deleteService(Long id);
}
