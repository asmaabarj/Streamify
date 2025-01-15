package com.MusicManager.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MusicManager.dtos.AlbumDTO;
import com.MusicManager.exceptions.AlbumException;
import com.MusicManager.mappers.AlbumMapper;
import com.MusicManager.model.Album;
import com.MusicManager.repositories.AlbumRepository;
import com.MusicManager.services.interfaces.AlbumService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AlbumServiceImpl implements AlbumService {

    private static final Logger logger = LoggerFactory.getLogger(AlbumServiceImpl.class);

    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;

    @Override
    public Page<AlbumDTO> listAlbums(Pageable pageable) {
        log.info("Listing albums with pagination.");
        return albumRepository.findAll(pageable)
                .map(albumMapper::albumToAlbumDTO);
    }

    @Override
    public AlbumDTO findAlbumById(String id) {
        log.info("Fetching Album with ID: {}", id);
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new AlbumException("Album introuvable avec l'ID: " + id));
        return albumMapper.albumToAlbumDTO(album);
    }

    @Override
    public Page<AlbumDTO> searchAlbumsBytitre(String titre, Pageable pageable) {
        return albumRepository.findByTitreContainingIgnoreCase(titre,pageable).map(albumMapper::albumToAlbumDTO);
    }

    @Override
    public Page<AlbumDTO> searchAlbumsByArtiste(String artiste, Pageable pageable) {
        log.info("Recherche des albums par artiste: {}", artiste);
        Page<Album> albums = albumRepository.findByArtisteContainingIgnoreCase(artiste, pageable);
        return albums.map(albumMapper::albumToAlbumDTO);
    }

    @Override
    public Page<AlbumDTO> filterAlbumsByAnnee(Integer annee, Pageable pageable) {
        log.info("Filtrage des albums par année: {}", annee);
        Page<Album> albums = albumRepository.findByAnnee(annee, pageable);
        return albums.map(albumMapper::albumToAlbumDTO);
    }

    @Override
    public AlbumDTO addAlbum(AlbumDTO albumDTO) {
        logger.info("Tentative d'ajout d'un album: {}", albumDTO);
        log.info("Adding a new Album: {}", albumDTO.getTitre());
        if (albumRepository.existsByTitre(albumDTO.getTitre())) {
            throw new AlbumException("Un album avec ce nom existe déjà.");
        }
        Album album = albumMapper.AlbumDTOToAlbum(albumDTO);
        album = albumRepository.save(album);
        return albumMapper.albumToAlbumDTO(album);

    }

    @Override
    public AlbumDTO updateAlbum(String id, AlbumDTO albumDTO) {
        log.info("Updating Album with ID: {}", id);
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new AlbumException("Album introuvable avec l'ID: " + id));

        if (albumRepository.existsByTitre(albumDTO.getTitre()) && !album.getTitre().equals(albumDTO.getTitre())) {
            throw new AlbumException("Un Album avec ce nom existe déjà : " + albumDTO.getTitre());

        }
        album.setTitre(albumDTO.getTitre());
        album.setAnnee(albumDTO.getAnnee());
        album.setArtiste(albumDTO.getArtiste());

        album = albumRepository.save(album);
        return albumMapper.albumToAlbumDTO(album);
    }

    @Override
    public void deleteAlbum(String id) {
        log.info("Deleting Album with ID: {}", id);
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new AlbumException("Album introuvable avec l'ID: " + id));
        albumRepository.delete(album);
    }
}
