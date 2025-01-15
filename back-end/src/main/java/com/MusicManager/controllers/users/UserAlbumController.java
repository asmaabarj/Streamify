package com.MusicManager.controllers.users;

import com.MusicManager.dtos.AlbumDTO;
import com.MusicManager.services.interfaces.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/albums")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('USER')")
public class UserAlbumController {
    private final AlbumService albumService;
    @GetMapping
    public ResponseEntity<Page<AlbumDTO>> getAllAlbums(
            @PageableDefault(size = 10, sort = "titre", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(albumService.listAlbums(pageable));
    }

    @GetMapping("/search/titre/{titre}")
    public ResponseEntity<Page<AlbumDTO>> searchByTitre(
            @PathVariable String titre, @PageableDefault(size = 10, sort = "titre", direction = Sort.Direction.ASC) Pageable pageable) {
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

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> recupererParId(@PathVariable String id) {
        return ResponseEntity.ok(albumService.findAlbumById(id));
    }


}
