package org.coathangerstudios.backend.service;

import org.coathangerstudios.backend.exception.DatabaseSaveException;
import org.coathangerstudios.backend.model.entity.Image;
import org.coathangerstudios.backend.model.payload.SuccessfulUploadResponse;
import org.coathangerstudios.backend.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public SuccessfulUploadResponse saveImage(MultipartFile file) throws IOException {
        Image image = new Image(file.getOriginalFilename(), file.getContentType(), file.getBytes());
        try {
            imageRepository.save(image);
        }catch (Exception e){
            throw new DatabaseSaveException("Could not save image");
        }
        Image savedImage = imageRepository.findByImagePublicId(image.getImagePublicId()).orElseThrow(NoSuchElementException::new);
        return new SuccessfulUploadResponse("Successful image upload", savedImage.getImagePublicId());
    }
}
