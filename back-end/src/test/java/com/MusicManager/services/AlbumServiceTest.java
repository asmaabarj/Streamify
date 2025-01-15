package com.MusicManager.services;

import com.MusicManager.dtos.AlbumDTO;
import com.MusicManager.exceptions.AlbumException;
import com.MusicManager.mappers.AlbumMapper;
import com.MusicManager.model.Album;
import com.MusicManager.repositories.AlbumRepository;
import com.MusicManager.services.impl.AlbumServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private AlbumMapper albumMapper;

    @InjectMocks
    private AlbumServiceImpl albumService;

    private Album album;
    private AlbumDTO albumDTO;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        album = new Album();
        album.setId("1");
        album.setTitre("Test Album");
        album.setArtiste("Test Artiste");
        album.setAnnee(2024);

        albumDTO = new AlbumDTO();
        albumDTO.setId("1");
        albumDTO.setTitre("Test Album");
        albumDTO.setArtiste("Test Artiste");
        albumDTO.setAnnee(2024);
    }

    @Test
    void addAlbum_Success() {
        when(albumRepository.existsByTitre(any())).thenReturn(false);
        when(albumMapper.AlbumDTOToAlbum(any())).thenReturn(album);
        when(albumRepository.save(any())).thenReturn(album);
        when(albumMapper.albumToAlbumDTO(any())).thenReturn(albumDTO);

        AlbumDTO result = albumService.addAlbum(albumDTO);

        assertNotNull(result);
        assertEquals(albumDTO.getTitre(), result.getTitre());
        verify(albumRepository).save(any());
    }

    @Test
    void addAlbum_DuplicateTitle_ThrowsException() {
        when(albumRepository.existsByTitre(any())).thenReturn(true);

        assertThrows(AlbumException.class, () -> albumService.addAlbum(albumDTO));
        verify(albumRepository, never()).save(any());
    }

    @Test
    void findAlbumById_Success() {
        when(albumRepository.findById("1")).thenReturn(Optional.of(album));
        when(albumMapper.albumToAlbumDTO(album)).thenReturn(albumDTO);

        AlbumDTO result = albumService.findAlbumById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
    }

    @Test
    void findAlbumById_NotFound_ThrowsException() {
        when(albumRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(AlbumException.class, () -> albumService.findAlbumById("1"));
    }

    @Test
    void searchAlbumsByTitre_Success() {
        Page<Album> albumPage = new PageImpl<>(Arrays.asList(album));
        when(albumRepository.findByTitreContainingIgnoreCase(any(), any())).thenReturn(albumPage);
        when(albumMapper.albumToAlbumDTO(any())).thenReturn(albumDTO);

        Page<AlbumDTO> result = albumService.searchAlbumsBytitre("Test", pageable);

        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
    }

    @Test
    void deleteAlbum_Success() {
        when(albumRepository.findById("1")).thenReturn(Optional.of(album));
        doNothing().when(albumRepository).delete(album);

        assertDoesNotThrow(() -> albumService.deleteAlbum("1"));
        verify(albumRepository).delete(album);
    }

    @Test
    void deleteAlbum_NotFound_ThrowsException() {
        when(albumRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(AlbumException.class, () -> albumService.deleteAlbum("1"));
        verify(albumRepository, never()).delete(any());
    }
} 