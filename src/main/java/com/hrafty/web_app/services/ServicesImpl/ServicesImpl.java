package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.Repository.SellerRepository;
import com.hrafty.web_app.Repository.ServiceRepository;
import com.hrafty.web_app.dto.ServiceDTO;
import com.hrafty.web_app.entities.Seller;
import com.hrafty.web_app.entities.Service;
import com.hrafty.web_app.exception.InvalidRequest;
import com.hrafty.web_app.exception.ServiceNotFoundException;
import com.hrafty.web_app.mapper.ServiceMapper;
import com.hrafty.web_app.services.ServiceService;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@org.springframework.stereotype.Service
public class ServicesImpl implements ServiceService {
    private final ServiceMapper serviceMapper;
    private final ServiceRepository serviceRepository;

    private final SellerRepository sellerRepository;

    public ServicesImpl(ServiceMapper serviceMapper, ServiceRepository serviceRepository, SellerRepository sellerRepository) {
        this.serviceMapper = serviceMapper;
        this.serviceRepository = serviceRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public ServiceDTO create(ServiceDTO serviceDTO) {
        Seller seller = sellerRepository.findById(serviceDTO.sellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found"));
        Service service = serviceMapper.toEntity(serviceDTO);
        service.setSeller(seller);
        Service savedService = serviceRepository.save(service);
        return serviceMapper.toDTO(savedService);
    }

    @Override
    public List<ServiceDTO> getAllServices() {
        List<Service> services =serviceRepository.findAll();
        List<ServiceDTO> serviceDTOS=new ArrayList<>();
        for(Service service:services){
            if (service.isStatus()) {
                serviceDTOS.add(serviceMapper.toDTO(service));
            }
        }
        return serviceDTOS;
    }

    @Override
    public List<ServiceDTO> getAllServices(Long id) {
        List<Service> services = serviceRepository.findAllBySellerId(id);
        List<ServiceDTO> serviceDTOS = new ArrayList<>();
        for (Service service : services) {
            serviceDTOS.add(serviceMapper.toDTO(service));
        }
        return serviceDTOS;
    }
    @Override
    public ServiceDTO getService(Long id) {
        Optional<Service> serviceOptional = serviceRepository.findById(id);
        if (serviceOptional.isPresent()) {
            return serviceMapper.toDTO(serviceOptional.get());
        }
        throw new ServiceNotFoundException("Service not found for id: ",new Throwable());
    }

    @Override
    public void updateService(Long id, ServiceDTO serviceDTO) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new InvalidRequest("service not found"));
        if (service != null) {
            Service updatedService = serviceMapper.toEntity(serviceDTO);
            updatedService.setId(service.getId());
            updatedService = serviceRepository.save(updatedService);
             serviceMapper.toDTO(updatedService);
        }
    }

    @Override
    public ServiceDTO changeStatus(Boolean status, Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new InvalidRequest("Service not found"));
        service.setStatus(status);
        serviceRepository.save(service);
        return serviceMapper.toDTO(service);
    }

    @Override
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    @Override
    public List<ServiceDTO> getAllServices(String name, String type) {
        return null;
    }

//    @Override
//    public List<ServiceDTO> listServicesByNamesAndCategory(String city, String category) {
//        List<Service> serviceList=serviceRepository.findAllByNameAndCategory(city, category);
//        List<ServiceDTO> serviceDTOS = new ArrayList<>();
//        for (Service service : serviceList) {
//            serviceDTOS.add(serviceMapper.toDTO(service));
//        }
//        return serviceDTOS;
//    }

    @Override
    public List<ServiceDTO> getAllServices(String category) {
        List<Service> serviceList=serviceRepository.findAllByCategory(category);
        List<ServiceDTO> serviceDTOS = new ArrayList<>();
        for (Service service : serviceList) {
            serviceDTOS.add(serviceMapper.toDTO(service));
        }
        return serviceDTOS;
    }

    @Override
    public List<ServiceDTO> getAllServicesByCityAndCategory(String city, String category) {
        return null;
    }

    @Override
    public List<ServiceDTO> getAllServicesCity(String category) {
        return null;
    }


    @Override
    public List<String> getAllCatrgories() {
        return new ArrayList<>(serviceRepository.findAllCategories());

    }

}
