package com.MusicManager.services.impl;

import com.MusicManager.dtos.ChansonDTO;
import com.MusicManager.exceptions.ChansonException;
import com.MusicManager.exceptions.ChansonException;
import com.MusicManager.mappers.ChansonMapper;
import com.MusicManager.model.Chanson;
import com.MusicManager.repositories.ChansonRepository;
import com.MusicManager.services.interfaces.ChansonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChansonServiceImpl implements ChansonService {

    private final ChansonRepository chansonRepository;
    private final ChansonMapper chansonMapper;

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
}
