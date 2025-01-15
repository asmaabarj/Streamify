package com.MusicManager.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.MusicManager.model.Album;

@Repository
public interface AlbumRepository extends MongoRepository<Album, String> {
    Page<Album> findByTitreContainingIgnoreCase(String titre, Pageable pageable);
    Page<Album> findByArtisteContainingIgnoreCase(String artiste, Pageable pageable);
    Page<Album> findByAnnee(Integer annee, Pageable pageable);
    boolean existsByTitre(String titre);
}
