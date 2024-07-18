package com.hrafty.web_app.services.ServicesImpl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.models.BlobStorageException;
import com.hrafty.web_app.Auth.ResponseMessage;
import com.hrafty.web_app.Repository.ImageRepository;
import com.hrafty.web_app.dto.*;
import com.hrafty.web_app.entities.Image;
import com.hrafty.web_app.exception.ImageNotFoundException;
import com.hrafty.web_app.mapper.ImageMapper;
import com.hrafty.web_app.services.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service
public class ImageImpl implements ImageService {
    private final ImageMapper imageMapper;
    private final BlobClientBuilder blobClientBuilder;
    private final BlobContainerClient blobContainerClient;
    private final ImageRepository imageRepository;
    private final CustomerService customerService;
    private final SellerService sellerService;
    private final ProductService productService;
    private final ServiceService serviceService;

    public ImageImpl(ImageMapper imageMapper, BlobClientBuilder blobClientBuilder, BlobContainerClient blobContainerClient, ImageRepository imageRepository, CustomerService customerService, SellerService sellerService, ProductService productService, ServiceService serviceService) {
        this.imageMapper = imageMapper;
        this.blobClientBuilder = blobClientBuilder;
        this.blobContainerClient = blobContainerClient;
        this.imageRepository = imageRepository;
        this.customerService = customerService;
        this.sellerService = sellerService;
        this.productService = productService;
        this.serviceService = serviceService;
    }


    @Override
    public ImageDTO create(ImageDTO entity) {
        Image image=imageMapper.toEntity(entity);
        Image saveImage=imageRepository.save(image);
        return imageMapper.toDTO(saveImage);
    }

    @Override
    public ImageDTO getById(Long id) {
        Optional<Image> imageOptional=imageRepository.findById(id);
        if (imageOptional.isPresent()){
            return imageMapper.toDTO(imageOptional.get());
        }
        throw new ImageNotFoundException("Image not found for id: ",new Throwable());
    }

    @Override
    public List<ImageDTO> getAll() {
      List<Image> images =imageRepository.findAll();
      List<ImageDTO> imageDTOS=new ArrayList<>();
       for(Image image:images){
           imageDTOS.add(imageMapper.toDTO(image));
       }
       return imageDTOS;
    }

    @Override
    public void deleteImg(String fileName) {
        try {
            BlobClient blobClient = blobClientBuilder.blobName(fileName).buildClient();
            boolean deleted = blobClient.deleteIfExists();
            if (!deleted) {
                throw new ImageNotFoundException("File not found or error deleting file: " , new Throwable());
            }
        } catch (Exception e) {
            throw new ImageNotFoundException("File not found or error deleting file: " , new Throwable());
        }
    }


    @Override
    public ImageDTO update(Long id, ImageDTO entity) {
        Optional<Image> optionalEntity = imageRepository.findById(id);
        if (optionalEntity.isPresent()) {
            Image existingEntity = optionalEntity.get();
            copyProperties(entity, existingEntity);
            Image updatedEntity = imageRepository.save(existingEntity);
            return imageMapper.toDTO(updatedEntity);
        } else {
            throw new ImageNotFoundException("Image not found for id: " + id, new Throwable());
        }
    }

    @Override
    public ResponseMessage uploadImage(MultipartFile file, Long id,String entityType) throws IOException {
        if (file.isEmpty()) {
            return new ResponseMessage("File is empty");
        }
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = getFileExtension(originalFileName);
        String fileName = generateUniqueFileName() + "." + extension;
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
        blobClient.upload(file.getInputStream(), file.getSize(), true);
        BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(file.getContentType());
        blobClient.setHttpHeaders(headers);
        Image image = new Image();
        image.setFilename(fileName);
        image.setUrl(blobClient.getBlobUrl());
        switch (entityType.toLowerCase()) {
            case "seller" -> {
                SellerDTO seller = sellerService.getSeller(id);
                image.setId(seller.id());
                sellerService.updateSeller(seller,id);
            }
            case "customer" -> {
                CustomerDTO customer = customerService.getCustomer(id);
//                image.setId(customer.id());
                customer = new CustomerDTO(
                        customer.id(),
                        image.getId(),
                        customer.sexe(),
                        customer.phone(),
                        customer.userId()
                );
                customerService.updateCustomer(customer);
            }
            case "product" -> {
                ProductDTO product = productService.getProduct(id);
                image = imageRepository.save(image);
                ImageDTO imageDTO = imageMapper.toDTO(image);
                product.images().add(imageDTO);
            }
            case "service" -> {
                ServiceDTO product = serviceService.getService(id);
                image = imageRepository.save(image);
                ImageDTO imageDTO = imageMapper.toDTO(image);
                product.images().add(imageDTO);
            }
            default -> throw new IllegalArgumentException("Invalid entity type: " + entityType);
        }

        return new ResponseMessage("Image uploaded successfully");
    }


    @Override
    public ResponseEntity<byte[]> downloadImage(String fileName) throws IOException {
        BlobClient blobClient = blobClientBuilder.blobName(fileName).buildClient();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blobClient.download(outputStream);
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(blobClient.getProperties().getContentType()));
        headers.setContentDispositionFormData("attachment", encodedFileName);
        return ResponseEntity.ok().headers(headers).body(outputStream.toByteArray());
    }

    @Override
    public void downloadAllImages(ByteArrayOutputStream outputStream) {
        List<ImageDTO> images = getAll();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            for (ImageDTO image : images) {
                BlobClient blobClient = blobClientBuilder.blobName(image.filename()).buildClient();
                try {
                    ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream();
                    blobClient.download(imageOutputStream);
                    ZipEntry zipEntry = new ZipEntry(image.filename());
                    zipEntry.setSize(imageOutputStream.size());
                    zipOutputStream.putNextEntry(zipEntry);
                    zipOutputStream.write(imageOutputStream.toByteArray());
                    zipOutputStream.closeEntry();
                } catch (BlobStorageException e) {
                    if (e.getStatusCode() == 404) {
                        System.out.println("Blob not found for image: " + image.filename());
                    } else {
                        throw e;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getImageByFileName(String fileName) throws IOException {
        BlobClient blobClient = blobClientBuilder.blobName(fileName).buildClient();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blobClient.download(outputStream);
        return outputStream.toByteArray();
    }

    @Override
    public byte[] getImageByUrl(String imageUrl) throws IOException {
        java.net.URL url = new java.net.URL(imageUrl);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (java.io.InputStream stream = url.openStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = stream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return outputStream.toByteArray();
    }


    private String generateUniqueFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return now.format(formatter);
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0) {
            return filename.substring(dotIndex + 1);
        }
        return "";
    }

    private void copyProperties(ImageDTO source, Image target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, "id");
    }
}
