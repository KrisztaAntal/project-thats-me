package org.coathangerstudios.backend.serviceTest;

import org.coathangerstudios.backend.exception.DatabaseSaveException;
import org.coathangerstudios.backend.exception.FileReadException;
import org.coathangerstudios.backend.exception.UnUploadedFileException;
import org.coathangerstudios.backend.model.entity.Image;
import org.coathangerstudios.backend.model.entity.ImageType;
import org.coathangerstudios.backend.model.entity.Member;
import org.coathangerstudios.backend.repository.ImageRepository;
import org.coathangerstudios.backend.service.ImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.UnsupportedMediaTypeException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {
    @Mock
    private ImageRepository imageRepositoryMock;
    @Mock
    private Member memberMock;
    @Mock
    private MultipartFile multipartFileMock;

    @InjectMocks
    private ImageService imageService;

    @Test
    public void testSaveImage_Success() throws IOException {
        when(multipartFileMock.getOriginalFilename()).thenReturn("example.jpg");
        when(multipartFileMock.getContentType()).thenReturn("image/jpeg");
        when(multipartFileMock.getBytes()).thenReturn("image data".getBytes());

        ImageType avatarType = ImageType.AVATAR;
        ImageType generalType = ImageType.GENERAL;
        Image savedImage = new Image("example.jpg", "image/jpeg",
                new HashSet<>(List.of(generalType, avatarType)), LocalDate.now(),
                "image data".getBytes(), memberMock);

        when(imageRepositoryMock.save(any(Image.class))).thenReturn(savedImage);
        when(imageRepositoryMock.findByImagePublicId(savedImage.getImagePublicId())).thenReturn(Optional.of(savedImage));

        Image result = imageService.saveImage(multipartFileMock, memberMock, avatarType);

        assertEquals("example.jpg", result.getName());
        assertTrue(result.getImageTypes().contains(ImageType.AVATAR));

        verify(imageRepositoryMock, times(1)).save(any(Image.class));
        verify(imageRepositoryMock, times(1)).findByImagePublicId(savedImage.getImagePublicId());
    }

    @Test
    public void testSaveImage_missingFile() {
        when(multipartFileMock.isEmpty()).thenReturn(true);
        assertThrows(UnUploadedFileException.class, () -> imageService.saveImage(multipartFileMock, memberMock, ImageType.AVATAR));
    }

    @Test
    public void testSaveImage_wrongFileType() {
        when(multipartFileMock.getContentType()).thenReturn("text/csv");
        assertThrows(UnsupportedMediaTypeException.class, () -> imageService.saveImage(multipartFileMock, memberMock, ImageType.AVATAR));
    }

    @Test
    public void testSaveImage_IOException() throws IOException {
        when(multipartFileMock.getOriginalFilename()).thenReturn("example.jpg");
        when(multipartFileMock.getContentType()).thenReturn("image/jpeg");
        when(multipartFileMock.getBytes()).thenThrow(new IOException("Simulated IO Error"));
        Exception thrown = assertThrows(FileReadException.class, () -> imageService.saveImage(multipartFileMock, memberMock, ImageType.AVATAR));
        assertEquals(thrown.getMessage(), "Error reading the uploaded file. Please try again.");
    }

    @Test
    public void testSaveImage_failedDataSave() {
        when(multipartFileMock.getOriginalFilename()).thenReturn("example.jpg");
        when(multipartFileMock.getContentType()).thenReturn("image/jpeg");
        when(imageRepositoryMock.save(any(Image.class))).thenThrow(new DataIntegrityViolationException("Database error"));
        assertThrows(DatabaseSaveException.class, () -> imageService.saveImage(multipartFileMock, memberMock, ImageType.AVATAR));
    }

    @Test
    public void testSaveImage_unexpectedError() {
        when(multipartFileMock.getOriginalFilename()).thenReturn("example.jpg");
        when(multipartFileMock.getContentType()).thenReturn("image/jpeg");
        when(imageRepositoryMock.findByImagePublicId(any(UUID.class))).thenThrow(new RuntimeException("Unexpected error"));
        Exception exception = assertThrows(DatabaseSaveException.class, () -> imageService.saveImage(multipartFileMock, memberMock, ImageType.AVATAR));
        assertEquals(exception.getMessage(), "Unexpected error occurred while saving image");
    }

}
