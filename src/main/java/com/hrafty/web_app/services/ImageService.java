package com.hrafty.web_app.services;


import com.hrafty.web_app.dto.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ImageService {
    String uploadFile(MultipartFile file);
     void deleteFile(List<String> blobFilesUrl);
    List<ImageDTO> uploadFiles(List<MultipartFile> files);

   List<String> updateFile(List<String> blobFilesUrl, List<MultipartFile> files);
    ImageDTO create(ImageDTO dto);
    ImageDTO getById(Long id);
    List<ImageDTO> getAll();
    String getImageUrlWithToken(String imageUrl);
    List<ImageDTO> getAllImageByProduct(Long id);
    List<ImageDTO> getAllImageByService(Long id);
    List<ImageDTO> getAllImageByProducts();
    List<ImageDTO> getAllImageByServices();
}
