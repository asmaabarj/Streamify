package com.MusicManager.controllers.admins;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.MusicManager.dtos.AlbumDTO;
import com.MusicManager.services.interfaces.AlbumService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/albums")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminAlbumController {
    private final AlbumService albumService;

    @GetMapping
    public ResponseEntity<Page<AlbumDTO>> getAllAlbums(
            @PageableDefault(size = 10, sort = "titre", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(albumService.listAlbums(pageable));
    }

    @GetMapping("/search/titre/{titre}")
    public ResponseEntity<Page<AlbumDTO>> searchByTitre(
            @PathVariable String titre,@PageableDefault(size = 10, sort = "titre", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(albumService.searchAlbumsBytitre(titre, pageable));
    }

    @GetMapping("/search/artiste/{artiste}")
    public ResponseEntity<Page<AlbumDTO>> searchByArtiste(
            @PathVariable String artiste,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(albumService.searchAlbumsByArtiste(artiste, pageable));
    }

    @GetMapping("/filter/annee/{annee}")
    public ResponseEntity<Page<AlbumDTO>> searchByAnnee(
            @PathVariable Integer annee,
            @PageableDefault(size = 10, sort = "titre", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(albumService.filterAlbumsByAnnee(annee, pageable));
    }

    @PostMapping
    public ResponseEntity<AlbumDTO> createAlbum(@Valid @RequestBody AlbumDTO albumDTO) {
        return ResponseEntity.ok(albumService.addAlbum(albumDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumDTO> updateAlbum(
            @PathVariable String id,
            @Valid @RequestBody AlbumDTO albumDTO) {
        return ResponseEntity.ok(albumService.updateAlbum(id, albumDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable String id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> recupererParId(@PathVariable String id) {
        return ResponseEntity.ok(albumService.findAlbumById(id));
    }
}