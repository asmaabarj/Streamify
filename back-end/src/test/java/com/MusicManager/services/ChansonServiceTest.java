package com.MusicManager.services;

import com.MusicManager.dtos.ChansonDTO;
import com.MusicManager.exceptions.ChansonException;
import com.MusicManager.mappers.ChansonMapper;
import com.MusicManager.model.Album;
import com.MusicManager.model.Chanson;
import com.MusicManager.repositories.ChansonRepository;
import com.MusicManager.services.impl.ChansonServiceImpl;
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
public class ChansonServiceTest {

    @Mock
    private ChansonRepository chansonRepository;

    @Mock
    private ChansonMapper chansonMapper;

    @InjectMocks
    private ChansonServiceImpl chansonService;

    private Chanson chanson;
    private ChansonDTO chansonDTO;
    private Album album;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        album = new Album();
        album.setId("1");

        chanson = new Chanson();
        chanson.setId("1");
        chanson.setTitre("Test Chanson");
        chanson.setDuree(180);
        chanson.setTrackNumber(1);
        chanson.setAlbum(album);

        chansonDTO = new ChansonDTO();
        chansonDTO.setId("1");
        chansonDTO.setTitre("Test Chanson");
        chansonDTO.setDuree(180);
        chansonDTO.setTrackNumber(1);
        chansonDTO.setAlbumId("1");
    }

    @Test
    void addChanson_Success() {
        when(chansonRepository.existsByTitre(any())).thenReturn(false);
        when(chansonMapper.chansonDTOToChanson(any())).thenReturn(chanson);
        when(chansonRepository.save(any())).thenReturn(chanson);
        when(chansonMapper.chansonToChansonDTO(any())).thenReturn(chansonDTO);

        ChansonDTO result = chansonService.addChanson(chansonDTO);

        assertNotNull(result);
        assertEquals(chansonDTO.getTitre(), result.getTitre());
        verify(chansonRepository).save(any());
    }

    @Test
    void addChanson_DuplicateTitle_ThrowsException() {
        when(chansonRepository.existsByTitre(any())).thenReturn(true);

        assertThrows(ChansonException.class, () -> chansonService.addChanson(chansonDTO));
        verify(chansonRepository, never()).save(any());
    }

    @Test
    void findChansonById_Success() {
        when(chansonRepository.findById("1")).thenReturn(Optional.of(chanson));
        when(chansonMapper.chansonToChansonDTO(chanson)).thenReturn(chansonDTO);

        ChansonDTO result = chansonService.finChansonById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
    }

    @Test
    void findChansonById_NotFound_ThrowsException() {
        when(chansonRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(ChansonException.class, () -> chansonService.finChansonById("1"));
    }

    @Test
    void searchChansonsByTitre_Success() {
        Page<Chanson> chansonPage = new PageImpl<>(Arrays.asList(chanson));
        when(chansonRepository.findByTitreContainingIgnoreCase(any(), any())).thenReturn(chansonPage);
        when(chansonMapper.chansonToChansonDTO(any())).thenReturn(chansonDTO);

        Page<ChansonDTO> result = chansonService.searchChansonsByTitre("Test", pageable);

        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
    }

    @Test
    void findChansonsByAlbum_Success() {
        Page<Chanson> chansonPage = new PageImpl<>(Arrays.asList(chanson));
        when(chansonRepository.findByAlbumId(any(), any())).thenReturn(chansonPage);
        when(chansonMapper.chansonToChansonDTO(any())).thenReturn(chansonDTO);

        Page<ChansonDTO> result = chansonService.findChansonsByAlbum("1", pageable);

        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
    }

} 