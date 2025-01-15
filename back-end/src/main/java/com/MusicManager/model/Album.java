package com.MusicManager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "albums")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    @Id
    private String id;
    
    @NotBlank(message = "Le titre est obligatoire")
    private String titre;
    
    @NotBlank(message = "L'artiste est obligatoire")
    private String artiste;
    
    @NotNull(message = "L'année est obligatoire")
    private Integer annee;
    
    @DBRef
    private List<Chanson> chansons = new ArrayList<>();
}
