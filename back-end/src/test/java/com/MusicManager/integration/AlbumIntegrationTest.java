package com.MusicManager.integration;

import com.MusicManager.config.MongoTestConfig;
import com.MusicManager.config.TestSecurityConfig;
import com.MusicManager.dtos.AlbumDTO;
import com.MusicManager.model.Album;
import com.MusicManager.repositories.AlbumRepository;
import com.MusicManager.services.interfaces.AlbumService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Import({MongoTestConfig.class, TestSecurityConfig.class})
public class AlbumIntegrationTest {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumRepository albumRepository;

    @BeforeEach
    void setUp() {
        albumRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        albumRepository.deleteAll();
    }

    @Test
    void shouldCreateAndRetrieveAlbum() {
        // Create album
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setTitre("Test Album");
        albumDTO.setArtiste("Test Artist");
        albumDTO.setAnnee(2024);

        AlbumDTO savedAlbum = albumService.addAlbum(albumDTO);

        assertNotNull(savedAlbum.getId());
        assertEquals("Test Album", savedAlbum.getTitre());

        AlbumDTO retrievedAlbum = albumService.findAlbumById(savedAlbum.getId());
        assertEquals(savedAlbum.getId(), retrievedAlbum.getId());
        assertEquals(savedAlbum.getTitre(), retrievedAlbum.getTitre());
    }

    @Test
    void shouldSearchAlbumsByTitre() {
        // Create multiple albums
        createAlbum("Rock Album", "Artist 1", 2024);
        createAlbum("Pop Album", "Artist 2", 2024);
        createAlbum("Jazz Album", "Artist 3", 2024);

        // Search albums
        Page<AlbumDTO> rockAlbums = albumService.searchAlbumsBytitre("Rock", PageRequest.of(0, 10));
        assertEquals(1, rockAlbums.getContent().size());
        assertEquals("Rock Album", rockAlbums.getContent().get(0).getTitre());
    }

    @Test
    void shouldSearchAlbumsByArtiste() {
        createAlbum("Album 1", "Artist 1", 2024);
        createAlbum("Album 2", "Artist 2", 2024);
        createAlbum("Album 3", "Artist 1", 2024);

        // Search albums
        Page<AlbumDTO> artistAlbums = albumService.searchAlbumsByArtiste("Artist 1", PageRequest.of(0, 10));
        assertEquals(2, artistAlbums.getContent().size());
    }

    @Test
    void shouldFilterAlbumsByAnnee() {
        // Create albums with different years
        createAlbum("Album 2023", "Artist 1", 2023);
        createAlbum("Album 2024-1", "Artist 2", 2024);
        createAlbum("Album 2024-2", "Artist 3", 2024);

        Page<AlbumDTO> albums2024 = albumService.filterAlbumsByAnnee(2024, PageRequest.of(0, 10));
        assertEquals(2, albums2024.getContent().size());
    }

    private Album createAlbum(String titre, String artiste, Integer annee) {
        Album album = new Album();
        album.setTitre(titre);
        album.setArtiste(artiste);
        album.setAnnee(annee);
        return albumRepository.save(album);
    }
}