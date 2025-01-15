package com.MusicManager.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.MusicManager.dtos.AlbumDTO;

public interface AlbumService {

    Page<AlbumDTO> listAlbums(Pageable pageable);
    AlbumDTO findAlbumById(String id);
    Page<AlbumDTO> searchAlbumsBytitre(String titre, Pageable pageable);
    Page<AlbumDTO> searchAlbumsByArtiste(String artiste, Pageable pageable);
    Page<AlbumDTO> filterAlbumsByAnnee(Integer annee, Pageable pageable);
    AlbumDTO addAlbum(AlbumDTO albumDTO);
    AlbumDTO updateAlbum(String id, AlbumDTO albumDTO);
    void deleteAlbum(String id);
}
