package com.hrafty.web_app.controller;


import com.hrafty.web_app.dto.ImageDTO;
import com.hrafty.web_app.services.ImageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }
//
//    @PostMapping("/upload/{id}")
//    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @PathVariable Long id,@PathVariable String entityType) throws IOException {
//        return new ResponseEntity(imageService.uploadImage(file,id,entityType), HttpStatus.CREATED);
//    }
//
//    @GetMapping("/download/{fileName}")
//    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) throws IOException {
//        return imageService.downloadImage(fileName);
//    }
//
//
//    @PutMapping("/update/{fileName}")
//    public ResponseEntity<ImageDTO> updateImage(@PathVariable Long id, @RequestBody ImageDTO image) {
//        try {
//            ImageDTO updatedImage = imageService.update(id, image);
//            return ResponseEntity.ok(updatedImage);
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }


//    @DeleteMapping("/delete/{fileName}")
//    public ResponseEntity<Void> deleteImage(@PathVariable String fileName) {
//        try {
//            imageService.deleteImg(fileName);
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//
//    @GetMapping("/all")
//    public ResponseEntity<List<ImageDTO>> getAllImages() {
//        List<ImageDTO> images = imageService.getAll();
//        return ResponseEntity.ok(images);
//    }
//
//
//    @GetMapping("/download/all")
//    public ResponseEntity<byte[]> downloadAllImages() throws IOException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        imageService.downloadAllImages(outputStream);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDispositionFormData("attachment", "all-images.zip");
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(outputStream.toByteArray());
//    }
//
//
//    @GetMapping("/getByFileName/{fileName}")
//    public ResponseEntity<byte[]> getImageByFileName(@PathVariable String fileName) throws IOException {
//        byte[] imageData = imageService.getImageByFileName(fileName);
//        return ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_JPEG)
//                .body(imageData);
//    }
//
//    @GetMapping("/getByUrl")
//    public ResponseEntity<byte[]> getImageByUrl(@RequestParam String imageUrl) throws IOException {
//        byte[] imageData = imageService.getImageByUrl(imageUrl);
//        return ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_JPEG)
//                .body(imageData);
//    }
}
