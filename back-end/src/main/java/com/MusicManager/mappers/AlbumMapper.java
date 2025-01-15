package com.MusicManager.mappers;

import com.MusicManager.dtos.AlbumDTO;
import com.MusicManager.model.Album;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface AlbumMapper {
    AlbumDTO albumToAlbumDTO(Album album);
    Album AlbumDTOToAlbum(AlbumDTO albumDTO);
}
