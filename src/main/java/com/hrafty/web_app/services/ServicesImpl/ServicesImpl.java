package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.Repository.SellerRepository;
import com.hrafty.web_app.Repository.ServiceRepository;
import com.hrafty.web_app.dto.ImageDTO;
import com.hrafty.web_app.dto.ServiceDTO;
import com.hrafty.web_app.entities.Image;
import com.hrafty.web_app.entities.Seller;
import com.hrafty.web_app.entities.Service;
import com.hrafty.web_app.exception.InvalidRequest;
import com.hrafty.web_app.exception.ServiceNotFoundException;
import com.hrafty.web_app.mapper.ImageMapper;
import com.hrafty.web_app.mapper.ServiceMapper;
import com.hrafty.web_app.services.ImageService;
import com.hrafty.web_app.services.ServiceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@org.springframework.stereotype.Service
public class ServicesImpl implements ServiceService {
    private final ServiceMapper serviceMapper;
    private final ServiceRepository serviceRepository;

    private final SellerRepository sellerRepository;
    private final ImageService imageService;
    private final ImageMapper imageMapper;

    public ServicesImpl(ServiceMapper serviceMapper, ServiceRepository serviceRepository, SellerRepository sellerRepository, ImageService imageService, ImageMapper imageMapper) {
        this.serviceMapper = serviceMapper;
        this.serviceRepository = serviceRepository;
        this.sellerRepository = sellerRepository;
        this.imageService = imageService;
        this.imageMapper = imageMapper;
    }

    @Override
    public ServiceDTO create(ServiceDTO serviceDTO, List<MultipartFile> files) {
        Seller seller = sellerRepository.findById(serviceDTO.sellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found"));
        Service service = serviceMapper.toEntity(serviceDTO);
        service.setSeller(seller);
        Service savedService = serviceRepository.save(service);
        List<ImageDTO> imageDTOs = imageService.uploadFiles(files);
        for (ImageDTO imageDTO : imageDTOs) {
            Image image = imageMapper.toEntity(imageDTO);
            image.setService(savedService);
            savedService.addImage(image);
        }
         savedService = serviceRepository.save(service);
        return serviceMapper.toDTO(savedService);
    }

    @Override
    public List<ServiceDTO> getAllServices() {
        List<Service> services =serviceRepository.findAllByWithImages();
        return services.stream()
                .map(service -> {
                    ServiceDTO dto = serviceMapper.toDTO(service);
                    List<ImageDTO> imagesWithToken = imageService.getAllImageByServices();
                    return new ServiceDTO(
                            dto.id(),
                            dto.name(),
                            dto.description(),
                            imagesWithToken,
                            dto.price(),
                            dto.category(),
                            dto.status(),
                            dto.sellerId(),
                            dto.reviewsDTOS(),
                            dto.panelId()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceDTO> getAllServices(Long sellerId) {
        List<Service> services = serviceRepository.findAllBySellerIdWithImages(sellerId);
        return services.stream()
                .map(service -> {
                    ServiceDTO dto = serviceMapper.toDTO(service);
                    List<ImageDTO> imagesWithToken = imageService.getAllImageByService(service.getId());
                    return new ServiceDTO(
                           dto.id(),
                            dto.name(),
                            dto.description(),
                            imagesWithToken,
                            dto.price(),
                            dto.category(),
                            dto.status(),
                            dto.sellerId(),
                            dto.reviewsDTOS(),
                            dto.panelId()
                    );
                })
                .collect(Collectors.toList());
    }
    @Override
    public ServiceDTO getService(Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(()->new ServiceNotFoundException("Service not found"));
            ServiceDTO dto = serviceMapper.toDTO(service);
            List<ImageDTO> imagesWithToken = imageService.getAllImageByService(service.getId());
            return new ServiceDTO(
                    dto.id(),
                    dto.name(),
                    dto.description(),
                    imagesWithToken,
                    dto.price(),
                    dto.category(),
                    dto.status(),
                    dto.sellerId(),
                    dto.reviewsDTOS(),
                    dto.panelId()
            );
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
        Service service=serviceRepository.findById(id)
                .orElseThrow(()->new InvalidRequest(("Service not found")));

        serviceRepository.deleteById(id);
    }

    @Override
    public List<ServiceDTO> getAllServices(String name, String type) {
        return null;
    }



    @Override
    public List<ServiceDTO> getAllServicesCategory(String category) {
        List<Service> services=serviceRepository.findAllByCategory(category);
        return services.stream()
                .map(service -> {
                    ServiceDTO dto = serviceMapper.toDTO(service);
                    List<ImageDTO> imagesWithToken = imageService.getAllImageByService(service.getId());
                    return new ServiceDTO(
                            dto.id(),
                            dto.name(),
                            dto.description(),
                            imagesWithToken,
                            dto.price(),
                            dto.category(),
                            dto.status(),
                            dto.sellerId(),
                            dto.reviewsDTOS(),
                            dto.panelId()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceDTO> getAllServicesByCityAndCategory(String city, String category) {
       List<Service> services =serviceRepository.findAllByCityAndCategory(city,category);
        return services.stream()
                .map(service -> {
                    ServiceDTO dto = serviceMapper.toDTO(service);
                    List<ImageDTO> imagesWithToken = imageService.getAllImageByService(service.getId());
                    return new ServiceDTO(
                            dto.id(),
                            dto.name(),
                            dto.description(),
                            imagesWithToken,
                            dto.price(),
                            dto.category(),
                            dto.status(),
                            dto.sellerId(),
                            dto.reviewsDTOS(),
                            dto.panelId()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceDTO> getAllServicesCity(String city) {
        List<Service> services =serviceRepository.findAllByCity(city);
        return services.stream()
                .map(service -> {
                    ServiceDTO dto = serviceMapper.toDTO(service);
                    List<ImageDTO> imagesWithToken = imageService.getAllImageByService(service.getId());
                    return new ServiceDTO(
                            dto.id(),
                            dto.name(),
                            dto.description(),
                            imagesWithToken,
                            dto.price(),
                            dto.category(),
                            dto.status(),
                            dto.sellerId(),
                            dto.reviewsDTOS(),
                            dto.panelId()
                    );
                })
                .collect(Collectors.toList());
    }


    @Override
    public List<String> getAllCategories() {
        return new ArrayList<>(serviceRepository.findAllCategories());

    }

}
