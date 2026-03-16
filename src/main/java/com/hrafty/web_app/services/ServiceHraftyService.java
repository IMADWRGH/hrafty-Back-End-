package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.request.ServiceRequestDTO;
import com.hrafty.web_app.dto.response.ServiceResponseDTO;
import com.hrafty.web_app.dto.common.PageResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ServiceHraftyService {

    ServiceResponseDTO create(ServiceRequestDTO serviceRequestDTO, List<MultipartFile> files);

    List<ServiceResponseDTO> getAllServices();

    PageResponseDTO<ServiceResponseDTO> getAllServices(Pageable pageable);

    List<ServiceResponseDTO> getServicesBySellerId(Long sellerId);

    PageResponseDTO<ServiceResponseDTO> getServicesBySellerId(Long sellerId, Pageable pageable);

    ServiceResponseDTO updateService(Long id, ServiceRequestDTO serviceRequestDTO);

    ServiceResponseDTO changeStatus(Long id, Boolean status);

    ServiceResponseDTO getService(Long id);

    void deleteService(Long id);

    List<ServiceResponseDTO> searchServices(String name, String type);

    List<ServiceResponseDTO> getServicesByCategory(String category);

    PageResponseDTO<ServiceResponseDTO> getServicesByCategory(String category, Pageable pageable);

    List<ServiceResponseDTO> getServicesByCityAndCategory(String city, String category);

    List<ServiceResponseDTO> getServicesByCity(String city);

    List<String> getAllCategories();


    List<ServiceResponseDTO> getActiveServices();

    List<ServiceResponseDTO> getServicesByPriceRange(double minPrice, double maxPrice);

    List<ServiceResponseDTO> getServicesByPanelId(Long panelId);

    ServiceResponseDTO addImagesToService(Long serviceId, List<MultipartFile> files);

    boolean existsById(Long id);

    long countServices();

    long countServicesBySellerId(Long sellerId);
}