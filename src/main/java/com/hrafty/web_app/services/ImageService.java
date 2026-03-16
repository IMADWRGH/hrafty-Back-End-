package com.hrafty.web_app.services;


import com.hrafty.web_app.dto.common.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    String uploadFile(MultipartFile file);

    void deleteFile(List<String> blobFilesUrl);

    List<ImageDTO> uploadFiles(List<MultipartFile> files);

    List<String> updateFile(List<String> blobFilesUrl, List<MultipartFile> files);

    ImageDTO create(ImageDTO dto);

    ImageDTO getById(Long id);

    List<ImageDTO> getAll();

    String getImageUrlWithToken(String imageUrl);

    List<ImageDTO> getAllImagesByProduct(Long productId);

    List<ImageDTO> getAllImagesByService(Long serviceId);

    List<ImageDTO> getAllImagesByProducts();

    List<ImageDTO> getAllImagesByServices();


    void deleteImage(Long id);

    void deleteImagesByProduct(Long productId);

    void deleteImagesByService(Long serviceId);

    ImageDTO setPrimaryImage(Long imageId);

    List<ImageDTO> reorderImages(List<Long> imageIds);

    boolean existsById(Long id);
}
