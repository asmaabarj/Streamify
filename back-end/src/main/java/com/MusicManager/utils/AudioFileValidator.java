package com.MusicManager.utils;

import org.springframework.web.multipart.MultipartFile;

import com.MusicManager.exceptions.ChansonException;

public class AudioFileValidator {
    private static final long MAX_FILE_SIZE = 15 * 1024 * 1024; // 15MB
    private static final String[] SUPPORTED_FORMATS = {"audio/mpeg", "audio/wav", "audio/ogg"};

    public static void validateAudioFile(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ChansonException("Le fichier dépasse la taille maximale autorisée de 15MB");
        }

        String contentType = file.getContentType();
        boolean formatSupported = false;
        for (String format : SUPPORTED_FORMATS) {
            if (format.equals(contentType)) {
                formatSupported = true;
                break;
            }
        }

        if (!formatSupported) {
            throw new ChansonException("Format de fichier non supporté. Formats acceptés: MP3, WAV, OGG");
        }
    }
} 