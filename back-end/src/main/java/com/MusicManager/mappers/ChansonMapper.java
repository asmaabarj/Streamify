package com.MusicManager.mappers;

import com.MusicManager.dtos.ChansonDTO;
import com.MusicManager.model.Chanson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface ChansonMapper {

    @Mapping(source = "album.id", target = "albumId")
    ChansonDTO chansonToChansonDTO(Chanson chanson);

    @Mapping(source = "albumId", target = "album.id")
    Chanson chansonDTOToChanson(ChansonDTO chansonDTO);
}
