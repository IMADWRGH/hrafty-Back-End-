package com.hrafty.web_app.services.ServicesImpl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.hrafty.web_app.Repository.ImageRepository;
import com.hrafty.web_app.dto.ImageDTO;
import com.hrafty.web_app.entities.Image;
import com.hrafty.web_app.mapper.ImageMapper;
import com.hrafty.web_app.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;



@Service
public class ImageImpl implements ImageService {

    @Value("${azure.storage.sas-token}")
    private String sasToken;
    private final BlobClientBuilder blobClientBuilder;
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public ImageImpl(BlobClientBuilder blobClientBuilder, ImageRepository imageRepository, ImageMapper imageMapper) {
        this.blobClientBuilder = blobClientBuilder;
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    public String uploadFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        assert filename != null;
        BlobClient blobClient = blobClientBuilder
                .blobName(filename)
                .buildClient();
        BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(file.getContentType());
        try {
            blobClient.upload(file.getInputStream(), file.getSize(), true);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
        blobClient.setHttpHeaders(headers);
        return blobClient.getBlobUrl();
    }

    public List<ImageDTO> uploadFiles(List<MultipartFile> files) {
        List<ImageDTO> imageDTOs = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileUrl = uploadFile(file);
            Image image = new Image();
            image.setFilename(file.getOriginalFilename());
            image.setUrl(fileUrl);
            ImageDTO imageDTO = imageMapper.toDTO(image);
            imageDTOs.add(imageDTO);
        }
        return imageDTOs;
    }

    public String updateFile(String blobFileUrl, MultipartFile file) {
        String blobName = extractBlobNameFromUrl(blobFileUrl);
        BlobClient blobClient = blobClientBuilder
                .blobName(blobName)
                .buildClient();
        BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(file.getContentType());
        try {
            blobClient.upload(file.getInputStream(), file.getSize(), true);
        } catch (Exception e) {
            throw new RuntimeException("file updated failed");
        }
        blobClient.setHttpHeaders(headers);
        return blobClient.getBlobUrl();
    }

    @Override
    public List<String> updateFile(List<String> blobFilesUrl, List<MultipartFile> files) {
        List<String> imagesUrl = new ArrayList<>();
        for (String fileUrl : blobFilesUrl){
            deleteFile(fileUrl);
        }
        for (MultipartFile file : files){
            String uploadedFile = uploadFile(file);
            imagesUrl.add(uploadedFile);
        }
        return imagesUrl;
    }

    public void deleteFile(String blobFileUrl) {
        String blobName = extractBlobNameFromUrl(blobFileUrl);
        BlobClient blobClient = blobClientBuilder
                .blobName(blobName)
                .buildClient();
        blobClient.delete();
    }

    @Override
    public void deleteFile(List<String> blobFilesUrl) {
        for (String fileName : blobFilesUrl){
            deleteFile(fileName);
        }
    }

    private String extractBlobNameFromUrl(String blobFileUrl) {
        return blobFileUrl.substring(blobFileUrl.lastIndexOf('/') + 1);
    }


    @Override
    public ImageDTO create(ImageDTO dto) {
        Image imageEntity = imageMapper.toEntity(dto);
        Image savedImage = imageRepository.save(imageEntity);
        return imageMapper.toDTO(savedImage);
    }

    @Override
    public ImageDTO getById(Long id) {
        Image image=imageRepository.findById(id).orElseThrow();
        return imageMapper.toDTO(image);
    }

    @Override
    public List<ImageDTO> getAll() {
        List<Image> images=imageRepository.findAll();
        List<ImageDTO> imageDTOS=new ArrayList<>();
        for (Image image:images){
            imageDTOS.add(imageMapper.toDTO(image));
        }
        return imageDTOS;
    }
    @Override
    public List<ImageDTO> getAllImageByProduct(Long id) {
        List<Image> images = imageRepository.findByProductId(id);
        return getImageDTOS(images);
    }
    @Override
    public List<ImageDTO> getAllImageByProducts() {
        List<Image> images = imageRepository.findAll();
        return getImageDTOS(images);
    }

    private List<ImageDTO> getImageDTOS(List<Image> images) {
        List<ImageDTO> imageDTOs = new ArrayList<>();
        for (Image image : images) {
            ImageDTO dto = imageMapper.toDTO(image);
            String urlWithToken = getImageUrlWithToken(dto.url());
            imageDTOs.add(new ImageDTO(dto.id(), dto.filename(), urlWithToken, dto.productId(),null));
        }
        return imageDTOs;
    }


    @Override
    public List<ImageDTO> getAllImageByService(Long id) {
        List<Image> images = imageRepository.findByServiceId(id);
        return getImages(images);
    }
    @Override
    public List<ImageDTO> getAllImageByServices() {
        List<Image> images = imageRepository.findAll();
        return getImages(images);
    }

    private List<ImageDTO> getImages(List<Image> images) {
        List<ImageDTO> imageDTOs = new ArrayList<>();
        for (Image image : images) {
            ImageDTO dto = imageMapper.toDTO(image);
            String urlWithToken = getImageUrlWithToken(dto.url());
            imageDTOs.add(new ImageDTO(dto.id(), dto.filename(), urlWithToken, null, dto.serviceId()));
        }
        return imageDTOs;
    }



    public String getImageUrlWithToken(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return null;
        }
        return imageUrl + (imageUrl.contains("?") ? "&" : "?") + sasToken;
    }

}
