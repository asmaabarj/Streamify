package com.MusicManager.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.MusicManager.model.Chanson;

@Repository
public interface ChansonRepository extends MongoRepository<Chanson, String> {
    Page<Chanson> findByTitreContainingIgnoreCase(String titre, Pageable pageable);
    Page<Chanson> findByAlbumId(String albumId, Pageable pageable);
    boolean existsByTitre(String titre);
}