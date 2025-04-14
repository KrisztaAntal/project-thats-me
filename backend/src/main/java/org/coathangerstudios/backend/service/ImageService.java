package org.coathangerstudios.backend.service;

import org.coathangerstudios.backend.exception.DatabaseSaveException;
import org.coathangerstudios.backend.exception.FileReadException;
import org.coathangerstudios.backend.exception.NoSuchImageException;
import org.coathangerstudios.backend.exception.UnUploadedFileException;
import org.coathangerstudios.backend.model.entity.Image;
import org.coathangerstudios.backend.model.entity.ImageType;
import org.coathangerstudios.backend.model.entity.Member;
import org.coathangerstudios.backend.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.UnsupportedMediaTypeException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    private static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "image/png", "application/pdf");


    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image saveImage(MultipartFile file, Member member, ImageType imageType) throws DatabaseSaveException {
        if (file.isEmpty()) {
            throw new UnUploadedFileException("Missing image, please upload an image.");
        }
        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new UnsupportedMediaTypeException("Invalid file type. Only JPG, PNG, and PDF are allowed.");
        }
        try {
            Image image = new Image(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    new HashSet<>(List.of(ImageType.GENERAL)),
                    LocalDate.now(),
                    file.getBytes(),
                    member);
            image.addImageType(imageType);
            imageRepository.save(image);
            return imageRepository.findByImagePublicId(image.getImagePublicId()).orElseThrow(NoSuchElementException::new);
        } catch (IOException e) {
            logger.error("Error reading file: {}", e.getMessage(), e);
            throw new FileReadException("Error reading the uploaded file. Please try again.");
        } catch (DataAccessException e) {
            logger.error("Could not find saved image in repository: {}", e.getMessage(), e);
            throw new DatabaseSaveException("Database error. Please try again later.");
        } catch (Exception e) {
            logger.error("Unexpected error while saving image: {}", e.getMessage(), e);
            throw new DatabaseSaveException("Unexpected error occurred while saving image");
        }
    }

    @Transactional
    public Image updateAvatarImage(MultipartFile file, Member member) {
        removeAvatarTagFromCurrentAvatar(member);
        return saveImage(file, member, ImageType.AVATAR);
    }

    @Transactional
    protected void removeAvatarTagFromCurrentAvatar(Member member) {
        Image currentAvatar = imageRepository.findByMemberAndImageTypesContaining(member, ImageType.AVATAR).orElseThrow(() -> new NoSuchElementException("Could not find current avatar"));
        currentAvatar.removeImageType(ImageType.AVATAR);
    }

    @Transactional
    public Image selectImageOfMember(Member member, ImageType imageType){
        return imageRepository.findByMemberAndImageTypesContaining(member, imageType).orElseThrow(NoSuchImageException::new);
    }
}
