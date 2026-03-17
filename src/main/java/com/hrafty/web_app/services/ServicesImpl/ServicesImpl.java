//package com.hrafty.web_app.services.ServicesImpl;
//
//import com.hrafty.web_app.Repository.SellerRepository;
//import com.hrafty.web_app.Repository.ServiceRepository;
//import com.hrafty.web_app.dto.common.ImageDTO;
//import com.hrafty.web_app.dto.ServiceDTO;
//import com.hrafty.web_app.dto.common.PageResponseDTO;
//import com.hrafty.web_app.dto.request.ServiceRequestDTO;
//import com.hrafty.web_app.dto.response.ServiceResponseDTO;
//import com.hrafty.web_app.entities.Image;
//import com.hrafty.web_app.entities.Seller;
//import com.hrafty.web_app.entities.Service;
//import com.hrafty.web_app.exception.InvalidRequest;
//import com.hrafty.web_app.exception.ServiceNotFoundException;
//import com.hrafty.web_app.mapper.ImageMapper;
//import com.hrafty.web_app.mapper.ServiceMapper;
//import com.hrafty.web_app.services.ImageService;
//import com.hrafty.web_app.services.ServiceHraftyService;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.data.domain.Pageable;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@org.springframework.stereotype.Service
//public class ServicesImpl implements ServiceHraftyService {
//    private final ServiceMapper serviceMapper;
//    private final ServiceRepository serviceRepository;
//
//    private final SellerRepository sellerRepository;
//    private final ImageService imageService;
//    private final ImageMapper imageMapper;
//
//    public ServicesImpl(ServiceMapper serviceMapper, ServiceRepository serviceRepository, SellerRepository sellerRepository, ImageService imageService, ImageMapper imageMapper) {
//        this.serviceMapper = serviceMapper;
//        this.serviceRepository = serviceRepository;
//        this.sellerRepository = sellerRepository;
//        this.imageService = imageService;
//        this.imageMapper = imageMapper;
//    }
//
//    @Override
//    public ServiceDTO create(ServiceDTO serviceDTO, List<MultipartFile> files) {
//        Seller seller = sellerRepository.findById(serviceDTO.sellerId())
//                .orElseThrow(() -> new EntityNotFoundException("Seller not found"));
//        Service service = serviceMapper.toEntity(serviceDTO);
//        service.setSeller(seller);
//        Service savedService = serviceRepository.save(service);
//        List<ImageDTO> imageDTOs = imageService.uploadFiles(files);
//        for (ImageDTO imageDTO : imageDTOs) {
//            Image image = imageMapper.toEntity(imageDTO);
//            image.setService(savedService);
//            savedService.addImage(image);
//        }
//         savedService = serviceRepository.save(service);
//        return serviceMapper.toDTO(savedService);
//    }
//
//    @Override
//    public ServiceResponseDTO create(ServiceRequestDTO serviceRequestDTO, List<MultipartFile> files) {
//        return null;
//    }
//
//    @Override
//    public List<ServiceDTO> getAllServices() {
//        List<Service> services =serviceRepository.findAllByWithImages();
//        return services.stream()
//                .map(service -> {
//                    ServiceDTO dto = serviceMapper.toDTO(service);
//                    List<ImageDTO> imagesWithToken = imageService.getAllImageByServices();
//                    return new ServiceDTO(
//                            dto.id(),
//                            dto.name(),
//                            dto.description(),
//                            imagesWithToken,
//                            dto.price(),
//                            dto.category(),
//                            dto.status(),
//                            dto.sellerId(),
//                            dto.reviewsDTOS(),
//                            dto.panelId()
//                    );
//                })
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public PageResponseDTO<ServiceResponseDTO> getAllServices(Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public List<ServiceResponseDTO> getServicesBySellerId(Long sellerId) {
//        return List.of();
//    }
//
//    @Override
//    public PageResponseDTO<ServiceResponseDTO> getServicesBySellerId(Long sellerId, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public ServiceResponseDTO updateService(Long id, ServiceRequestDTO serviceRequestDTO) {
//        return null;
//    }
//
//    @Override
//    public ServiceResponseDTO changeStatus(Long id, Boolean status) {
//        return null;
//    }
//
//    @Override
//    public List<ServiceDTO> getAllServices(Long sellerId) {
//        List<Service> services = serviceRepository.findAllBySellerIdWithImages(sellerId);
//        return services.stream()
//                .map(service -> {
//                    ServiceDTO dto = serviceMapper.toDTO(service);
//                    List<ImageDTO> imagesWithToken = imageService.getAllImageByService(service.getId());
//                    return new ServiceDTO(
//                           dto.id(),
//                            dto.name(),
//                            dto.description(),
//                            imagesWithToken,
//                            dto.price(),
//                            dto.category(),
//                            dto.status(),
//                            dto.sellerId(),
//                            dto.reviewsDTOS(),
//                            dto.panelId()
//                    );
//                })
//                .collect(Collectors.toList());
//    }
//    @Override
//    public ServiceDTO getService(Long id) {
//        Service service = serviceRepository.findById(id)
//                .orElseThrow(()->new ServiceNotFoundException("Service not found"));
//            ServiceDTO dto = serviceMapper.toDTO(service);
//            List<ImageDTO> imagesWithToken = imageService.getAllImageByService(service.getId());
//            return new ServiceDTO(
//                    dto.id(),
//                    dto.name(),
//                    dto.description(),
//                    imagesWithToken,
//                    dto.price(),
//                    dto.category(),
//                    dto.status(),
//                    dto.sellerId(),
//                    dto.reviewsDTOS(),
//                    dto.panelId()
//            );
//    }
//
//
//    @Override
//    public void updateService(Long id, ServiceDTO serviceDTO) {
//        Service service = serviceRepository.findById(id)
//                .orElseThrow(() -> new InvalidRequest("service not found"));
//        if (service != null) {
//            Service updatedService = serviceMapper.toEntity(serviceDTO);
//            updatedService.setId(service.getId());
//            updatedService = serviceRepository.save(updatedService);
//             serviceMapper.toDTO(updatedService);
//        }
//    }
//
//    @Override
//    public ServiceDTO changeStatus(Boolean status, Long id) {
//        Service service = serviceRepository.findById(id)
//                .orElseThrow(() -> new InvalidRequest("Service not found"));
//        service.setStatus(status);
//        serviceRepository.save(service);
//        return serviceMapper.toDTO(service);
//    }
//
//    @Override
//    public void deleteService(Long id) {
//        Service service=serviceRepository.findById(id)
//                .orElseThrow(()->new InvalidRequest(("Service not found")));
//
//        serviceRepository.deleteById(id);
//    }
//
//    @Override
//    public List<ServiceResponseDTO> searchServices(String name, String type) {
//        return List.of();
//    }
//
//    @Override
//    public List<ServiceResponseDTO> getServicesByCategory(String category) {
//        return List.of();
//    }
//
//    @Override
//    public PageResponseDTO<ServiceResponseDTO> getServicesByCategory(String category, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public List<ServiceResponseDTO> getServicesByCityAndCategory(String city, String category) {
//        return List.of();
//    }
//
//    @Override
//    public List<ServiceResponseDTO> getServicesByCity(String city) {
//        return List.of();
//    }
//
//    @Override
//    public List<ServiceDTO> getAllServices(String name, String type) {
//        return null;
//    }
//
//
//
//    @Override
//    public List<ServiceDTO> getAllServicesCategory(String category) {
//        List<Service> services=serviceRepository.findAllByCategory(category);
//        return services.stream()
//                .map(service -> {
//                    ServiceDTO dto = serviceMapper.toDTO(service);
//                    List<ImageDTO> imagesWithToken = imageService.getAllImageByService(service.getId());
//                    return new ServiceDTO(
//                            dto.id(),
//                            dto.name(),
//                            dto.description(),
//                            imagesWithToken,
//                            dto.price(),
//                            dto.category(),
//                            dto.status(),
//                            dto.sellerId(),
//                            dto.reviewsDTOS(),
//                            dto.panelId()
//                    );
//                })
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<ServiceDTO> getAllServicesByCityAndCategory(String city, String category) {
//       List<Service> services =serviceRepository.findAllByCityAndCategory(city,category);
//        return services.stream()
//                .map(service -> {
//                    ServiceDTO dto = serviceMapper.toDTO(service);
//                    List<ImageDTO> imagesWithToken = imageService.getAllImageByService(service.getId());
//                    return new ServiceDTO(
//                            dto.id(),
//                            dto.name(),
//                            dto.description(),
//                            imagesWithToken,
//                            dto.price(),
//                            dto.category(),
//                            dto.status(),
//                            dto.sellerId(),
//                            dto.reviewsDTOS(),
//                            dto.panelId()
//                    );
//                })
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<ServiceDTO> getAllServicesCity(String city) {
//        List<Service> services =serviceRepository.findAllByCity(city);
//        return services.stream()
//                .map(service -> {
//                    ServiceDTO dto = serviceMapper.toDTO(service);
//                    List<ImageDTO> imagesWithToken = imageService.getAllImageByService(service.getId());
//                    return new ServiceDTO(
//                            dto.id(),
//                            dto.name(),
//                            dto.description(),
//                            imagesWithToken,
//                            dto.price(),
//                            dto.category(),
//                            dto.status(),
//                            dto.sellerId(),
//                            dto.reviewsDTOS(),
//                            dto.panelId()
//                    );
//                })
//                .collect(Collectors.toList());
//    }
//
//
//    @Override
//    public List<String> getAllCategories() {
//        return new ArrayList<>(serviceRepository.findAllCategories());
//
//    }
//
//    @Override
//    public List<ServiceResponseDTO> getActiveServices() {
//        return List.of();
//    }
//
//    @Override
//    public List<ServiceResponseDTO> getServicesByPriceRange(double minPrice, double maxPrice) {
//        return List.of();
//    }
//
//    @Override
//    public List<ServiceResponseDTO> getServicesByPanelId(Long panelId) {
//        return List.of();
//    }
//
//    @Override
//    public ServiceResponseDTO addImagesToService(Long serviceId, List<MultipartFile> files) {
//        return null;
//    }
//
//    @Override
//    public boolean existsById(Long id) {
//        return false;
//    }
//
//    @Override
//    public long countServices() {
//        return 0;
//    }
//
//    @Override
//    public long countServicesBySellerId(Long sellerId) {
//        return 0;
//    }
//
//}
