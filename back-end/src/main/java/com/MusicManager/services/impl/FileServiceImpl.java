package com.MusicManager.services.impl;

import com.MusicManager.exceptions.ChansonException;
import com.MusicManager.services.interfaces.FileService;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations gridFsOperations;

    @Override
    public String storeFile(MultipartFile file) {
        try {
            ObjectId fileId = gridFsTemplate.store(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType()
            );
            return fileId.toString();
        } catch (IOException e) {
            throw new ChansonException("Erreur lors du stockage du fichier: " + e.getMessage());
        }
    }

    @Override
    public void deleteFile(String fileId) {
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(fileId)));
    }

    @Override
    public byte[] getFile(String fileId) {
        try {
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
            if (gridFSFile == null) {
                throw new ChansonException("Fichier non trouvé");
            }
            return IOUtils.toByteArray(gridFsOperations.getResource(gridFSFile).getInputStream());
        } catch (IOException e) {
            throw new ChansonException("Erreur lors de la récupération du fichier: " + e.getMessage());
        }
    }
} 