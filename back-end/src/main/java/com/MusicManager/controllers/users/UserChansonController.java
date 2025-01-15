package com.MusicManager.controllers.users;

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

@RestController
@RequestMapping("/api/user/songs")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('USER')")
public class UserChansonController {
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

    @GetMapping("/{id}")
    public ResponseEntity<ChansonDTO> recupererParId(@PathVariable String id) {
        return ResponseEntity.ok(chansonService.finChansonById(id));
    }
}
