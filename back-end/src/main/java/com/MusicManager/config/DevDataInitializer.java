package com.MusicManager.config;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.MusicManager.model.Role;
import com.MusicManager.model.User;
import com.MusicManager.repositories.RoleRepository;
import com.MusicManager.repositories.UserRepository;
import com.MusicManager.model.Album;
import com.MusicManager.model.Chanson;
import com.MusicManager.repositories.AlbumRepository;
import com.MusicManager.repositories.ChansonRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class DevDataInitializer {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;
    private final ChansonRepository chansonRepository;
    private final MongoTemplate mongoTemplate;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        // Nettoyage des collections
        mongoTemplate.getDb().drop();

        // Création des rôles
        Role roleAdmin = new Role();
        roleAdmin.setName("ADMIN");
        roleRepository.save(roleAdmin);

        Role roleUser = new Role();
        roleUser.setName("USER");
        roleRepository.save(roleUser);

        // Création d'un utilisateur admin de test
        User adminUser = new User();
        adminUser.setLogin("admin");
        adminUser.setPassword(passwordEncoder.encode("admin"));
        adminUser.setRoles(Arrays.asList(roleAdmin));
        adminUser.setActive(true);
        userRepository.save(adminUser);

        // Création d'un utilisateur normal de test
        User normalUser = new User();
        normalUser.setLogin("user");
        normalUser.setPassword(passwordEncoder.encode("user"));
        normalUser.setRoles(Arrays.asList(roleUser));
        normalUser.setActive(true);
        userRepository.save(normalUser);

        // Création d'albums et chansons de test
        Album album1 = new Album();
        album1.setTitre("Album Test 1");
        album1.setArtiste("Artiste Test");
        album1.setAnnee(2024);
        albumRepository.save(album1);

        Chanson chanson1 = new Chanson();
        chanson1.setTitre("Chanson Test 1");
        chanson1.setDuree(180);
        chanson1.setTrackNumber(1);
        chanson1.setAlbum(album1);
        chansonRepository.save(chanson1);
    }
} 