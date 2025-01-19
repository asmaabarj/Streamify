package com.MusicManager.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChansonDTO {
    private String id;
    
    @NotBlank(message = "Le titre est obligatoire")
    private String titre;
    
    @Size(max = 200, message = "La description ne peut pas dépasser 200 caractères")
    private String description;
    
    private String categorie;
    
    @NotNull(message = "La date d'ajout est obligatoire")
    private Date dateAjout;
    
    @NotNull(message = "La durée est obligatoire")
    @Min(value = 1, message = "La durée doit être positive")
    private Integer duree;
    
    @NotNull(message = "Le numéro de piste est obligatoire")
    @Min(value = 1, message = "Le numéro de piste doit être positif")
    private Integer trackNumber;
    
    private String audioFileId;
    
    private String coverFileId;
    
    @NotBlank(message = "L'ID de l'album est obligatoire")
    private String albumId;
}
