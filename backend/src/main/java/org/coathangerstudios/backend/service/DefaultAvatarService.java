package org.coathangerstudios.backend.service;

import org.coathangerstudios.backend.exception.DatabaseSaveException;
import org.coathangerstudios.backend.model.entity.AvatarColor;
import org.coathangerstudios.backend.model.entity.DefaultAvatar;
import org.coathangerstudios.backend.repository.DefaultAvatarRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DefaultAvatarService {
    private final DefaultAvatarRepository defaultAvatarRepository;

    public DefaultAvatarService(DefaultAvatarRepository defaultAvatarRepository) {
        this.defaultAvatarRepository = defaultAvatarRepository;
    }

    public DefaultAvatar saveDefaultAvatar(String character) {
            String colorCode = getRandomColorForAvatar();
            DefaultAvatar defaultAvatarOfMember = new DefaultAvatar(character, colorCode);
        try {
            defaultAvatarRepository.save(defaultAvatarOfMember);
        } catch (Exception e) {
            throw new DatabaseSaveException("Failed to save default avatar into the database");
        }
        return defaultAvatarRepository.findDefaultAvatarByDefaultAvatarId(defaultAvatarOfMember.getDefaultAvatarId()).orElseThrow(() -> new IllegalStateException("Member was not found after saving. This should not happen."));
    }

    private String getRandomColorForAvatar() {
        AvatarColor[] colors = AvatarColor.values();
        return colors[new Random().nextInt(colors.length)].getHashCode();
    }
}
