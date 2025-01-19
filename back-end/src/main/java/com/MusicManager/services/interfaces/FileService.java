package com.MusicManager.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String storeFile(MultipartFile file);
    void deleteFile(String fileId);
    byte[] getFile(String fileId);
} 