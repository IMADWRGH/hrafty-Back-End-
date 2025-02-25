package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.ServiceDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ServiceService {
     ServiceDTO create(ServiceDTO serviceDTO, List<MultipartFile> files);
     List<ServiceDTO> getAllServices();
    List<ServiceDTO> getAllServices(Long id);
     void updateService(Long id , ServiceDTO serviceDTO);
     ServiceDTO changeStatus(Boolean status, Long id);
     ServiceDTO getService(Long id);
   void  deleteService(Long id);

    List<ServiceDTO> getAllServices(String name,String type);
    List<ServiceDTO> getAllServicesCategory(String category);
    List<ServiceDTO> getAllServicesByCityAndCategory(String city,String category);
    List<ServiceDTO> getAllServicesCity(String city);

    List<String> getAllCategories();





}
