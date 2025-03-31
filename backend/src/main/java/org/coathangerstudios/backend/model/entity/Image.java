package org.coathangerstudios.backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @UuidGenerator
    private UUID imagePublicId;

    private String name;

    private String contentType;

    @ElementCollection(targetClass = ImageType.class)
    @CollectionTable(name = "image_types", joinColumns = @JoinColumn(name = "image_public_id"))
    @Enumerated(EnumType.STRING)
    private Set<ImageType> imageTypes;

    private LocalDate dateOfUpload;

    @Lob
    private byte[] imageData;

    @ManyToOne
    private Member member;

    public Image(String name, String contentType, Set<ImageType> imageTypes, LocalDate dateOfUpload, byte[] imageData, Member member) {
        this.name = name;
        this.contentType = contentType;
        this.imageTypes = new HashSet<>(imageTypes);
        this.dateOfUpload = dateOfUpload;
        this.imageData = imageData;
        this.member = member;
    }

    public Image() {
    }

    public void addImageType(ImageType imageType) {
        imageTypes.add(imageType);
    }

    public void removeImageType(ImageType imageType){
        imageTypes.remove(imageType);
    }
}
