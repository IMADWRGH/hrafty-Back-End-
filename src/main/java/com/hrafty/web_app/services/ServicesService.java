package com.hrafty.web_app.services;

import com.hrafty.web_app.Repository.SellerRepository;
import com.hrafty.web_app.Repository.ServiceRepository;
import com.hrafty.web_app.dto.ServiceDTO;
import com.hrafty.web_app.entities.Seller;
import com.hrafty.web_app.entities.Service;
import com.hrafty.web_app.exception.InvalidRequest;
import com.hrafty.web_app.exception.PanelNotFoundException;
import com.hrafty.web_app.mapper.ServiceMapper;

import java.util.ArrayList;
import java.util.List;


@org.springframework.stereotype.Service
public class ServicesService {
    private final ServiceMapper serviceMapper;
    private final ServiceRepository serviceRepository;

    private final SellerRepository sellerRepository;

    public ServicesService(ServiceMapper serviceMapper, ServiceRepository serviceRepository, SellerRepository sellerRepository) {
        this.serviceMapper = serviceMapper;
        this.serviceRepository = serviceRepository;
        this.sellerRepository = sellerRepository;
    }



    public ServiceDTO create(ServiceDTO serviceDTO) {
        Seller seller = sellerRepository.findById(serviceDTO.getSellerId())
                .orElseThrow(() -> new InvalidRequest("Seller not found"));
        Service service = serviceMapper.toEntity(serviceDTO);
        service.setSeller(seller);
        Service savedService = serviceRepository.save(service);
        return serviceMapper.toDTO(savedService);
    }


    public List<ServiceDTO> getAllServices(){
        List<Service> services =serviceRepository.findAll();
        List<ServiceDTO> serviceDTOS=new ArrayList<>();
        for(Service service:services){
            serviceDTOS.add(serviceMapper.toDTO(service));
        }
        return serviceDTOS;
    }

    public List<ServiceDTO> getAllServices(Long id) {
        List<Service> services = serviceRepository.findAllBySellerId(id);
        List<ServiceDTO> serviceDTOS = new ArrayList<>();
        for (Service service : services) {
            serviceDTOS.add(serviceMapper.toDTO(service));
        }
        return serviceDTOS;
    }
    public void updateService(Long id , ServiceDTO serviceDTO){
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new InvalidRequest("service not found"));
        if (service != null) {
            Service updatedService = serviceMapper.toEntity(serviceDTO);
            updatedService.setId(service.getId());
            updatedService = serviceRepository.save(updatedService);
             serviceMapper.toDTO(updatedService);
        }
    }

    public ServiceDTO changeStatus(Boolean status, Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new InvalidRequest("Service not found"));
        service.setStatus(status);
        serviceRepository.save(service);
        return serviceMapper.toDTO(service);
    }

    public void  deleteService(Long id){
        serviceRepository.deleteById(id);
    }
}
