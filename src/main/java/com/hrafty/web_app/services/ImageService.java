package com.hrafty.web_app.services;


import com.hrafty.web_app.Auth.ResponseMessage;
import com.hrafty.web_app.dto.ImageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface ImageService {
    ImageDTO create(ImageDTO entity);
    ImageDTO getById(Long id);
    List<ImageDTO> getAll();
    void deleteImg(String fileName)  ;
    ImageDTO update(Long id, ImageDTO entity);
    ResponseMessage uploadImage(MultipartFile file, Long id,String entity) throws IOException;
    ResponseEntity<byte[]> downloadImage(String fileName) throws IOException;
    void downloadAllImages(ByteArrayOutputStream outputStream) ;
    byte[] getImageByFileName(String fileName) throws IOException;
    byte[] getImageByUrl(String imageUrl) throws IOException;

}
