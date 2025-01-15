package com.MusicManager.controllers.admins;

import com.MusicManager.dtos.AlbumDTO;
import com.MusicManager.dtos.ChansonDTO;
import com.MusicManager.services.interfaces.ChansonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/songs")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminChansonController {
    private final ChansonService chansonService;

    @GetMapping
    public ResponseEntity<Page<ChansonDTO>> getAllSongs(
            @PageableDefault(size = 10, sort = "titre", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(chansonService.listChansons(pageable));
    }

    @GetMapping("/search/titre/{titre}")
    public ResponseEntity<Page<ChansonDTO>> searchByTitre(
            @PathVariable String titre,
            @PageableDefault(size = 10, sort = "titre", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(chansonService.searchChansonsByTitre(titre, pageable));
    }

    @GetMapping("/album/{albumId}")
    public ResponseEntity<Page<ChansonDTO>> getChansonsByAlbum(
            @PathVariable String albumId,
            @PageableDefault(size = 10, sort = "trackNumber", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(chansonService.findChansonsByAlbum(albumId, pageable));
    }

    @PostMapping
    public ResponseEntity<ChansonDTO> createSong(@Valid @RequestBody ChansonDTO chansonDTO) {
        return ResponseEntity.ok(chansonService.addChanson(chansonDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChansonDTO> updateSong(
            @PathVariable String id,
            @Valid @RequestBody ChansonDTO chansonDTO) {
        return ResponseEntity.ok(chansonService.updateChanson(id, chansonDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable String id) {
        chansonService.deleteChanson(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChansonDTO> recupererParId(@PathVariable String id) {
        return ResponseEntity.ok(chansonService.finChansonById(id));
    }

}
