package com.MusicManager.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.MusicManager.dtos.ChansonDTO;
import com.MusicManager.exceptions.ChansonException;
import com.MusicManager.mappers.ChansonMapper;
import com.MusicManager.model.Chanson;
import com.MusicManager.repositories.ChansonRepository;
import com.MusicManager.services.interfaces.ChansonService;
import com.MusicManager.services.interfaces.FileService;
import com.MusicManager.utils.AudioFileValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChansonServiceImpl implements ChansonService {

    private final ChansonRepository chansonRepository;
    private final ChansonMapper chansonMapper;
    private final FileService fileService;

    @Override
    public Page<ChansonDTO> listChansons(Pageable pageable) {
        return chansonRepository.findAll(pageable)
                .map(chansonMapper::chansonToChansonDTO);
    }

    @Override
    public  ChansonDTO finChansonById(String id){
        log.info("Fetching Album with ID: {}", id);
        Chanson chanson = chansonRepository.findById(id)
                .orElseThrow(() -> new ChansonException("chanson introuvable avec l'ID: " + id));
        return chansonMapper.chansonToChansonDTO(chanson);    }


    @Override
    public Page<ChansonDTO> searchChansonsByTitre(String titre, Pageable pageable) {
        return chansonRepository.findByTitreContainingIgnoreCase(titre, pageable)
                .map(chansonMapper::chansonToChansonDTO);
    }

    @Override
    public Page<ChansonDTO> findChansonsByAlbum(String albumId, Pageable pageable) {
        return chansonRepository.findByAlbumId(albumId, pageable)
                .map(chansonMapper::chansonToChansonDTO);
    }

    @Override
    public ChansonDTO addChanson(ChansonDTO chansonDTO) {
        if (chansonRepository.existsByTitre(chansonDTO.getTitre())) {
            throw new ChansonException("Une chanson avec le titre '" + chansonDTO.getTitre() + "' existe déjà");
        }
        Chanson chanson = chansonMapper.chansonDTOToChanson(chansonDTO);
        return chansonMapper.chansonToChansonDTO(chansonRepository.save(chanson));
    }

    @Override
    public ChansonDTO updateChanson(String id, ChansonDTO chansonDTO) {
        if (!chansonRepository.existsById(id)) {
            throw new ChansonException("Chanson non trouvée avec l'ID: " + id);
        }
        Chanson chanson = chansonMapper.chansonDTOToChanson(chansonDTO);
        chanson.setId(id);
        Chanson updatedChanson = chansonRepository.save(chanson);
        return chansonMapper.chansonToChansonDTO(updatedChanson);
    }

    @Override
    public void deleteChanson(String id) {
        if (!chansonRepository.existsById(id)) {
            throw new ChansonException("Chanson non trouvée avec l'ID: " + id);
        }
        chansonRepository.deleteById(id);
    }

    @Override
    public String uploadAudioFile(String chansonId, MultipartFile file) {
        AudioFileValidator.validateAudioFile(file);
        Chanson chanson = chansonRepository.findById(chansonId)
            .orElseThrow(() -> new ChansonException("Chanson non trouvée avec l'ID: " + chansonId));
            
        // Supprimer l'ancien fichier si existe
        if (chanson.getAudioFileId() != null) {
            fileService.deleteFile(chanson.getAudioFileId());
        }
        
        String fileId = fileService.storeFile(file);
        chanson.setAudioFileId(fileId);
        chansonRepository.save(chanson);
        return fileId;
    }

    @Override
    public String uploadCoverFile(String chansonId, MultipartFile file) {
        Chanson chanson = chansonRepository.findById(chansonId)
            .orElseThrow(() -> new ChansonException("Chanson non trouvée avec l'ID: " + chansonId));
            
        if (chanson.getCoverFileId() != null) {
            fileService.deleteFile(chanson.getCoverFileId());
        }
        
        String fileId = fileService.storeFile(file);
        chanson.setCoverFileId(fileId);
        chansonRepository.save(chanson);
        return fileId;
    }

    @Override
    public byte[] getAudioFile(String chansonId) {
        Chanson chanson = chansonRepository.findById(chansonId)
            .orElseThrow(() -> new ChansonException("Chanson non trouvée avec l'ID: " + chansonId));
        if (chanson.getAudioFileId() == null) {
            throw new ChansonException("Aucun fichier audio associé à cette chanson");
        }
        return fileService.getFile(chanson.getAudioFileId());
    }

    @Override
    public byte[] getCoverFile(String chansonId) {
        Chanson chanson = chansonRepository.findById(chansonId)
            .orElseThrow(() -> new ChansonException("Chanson non trouvée avec l'ID: " + chansonId));
        if (chanson.getCoverFileId() == null) {
            throw new ChansonException("Aucune pochette associée à cette chanson");
        }
        return fileService.getFile(chanson.getCoverFileId());
    }
}
