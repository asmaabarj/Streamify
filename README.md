# 🎵 MusicManager

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.18-brightgreen.svg)
![MongoDB](https://img.shields.io/badge/MongoDB-Latest-green.svg)
![JWT](https://img.shields.io/badge/JWT-Authentication-blue.svg)
![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)

## 📝 Description

MusicManager est une API REST sécurisée pour la gestion d'un catalogue musical, permettant la gestion des albums et des chansons avec différents niveaux d'accès (USER/ADMIN). Développée avec Spring Boot et intégrant les pratiques DevOps modernes.

## 🏗️ Architecture

### Entités Principales
- 💿 **Album** : titre, artiste, année
- 🎵 **Chanson** : titre, durée, numéro de piste
- 👤 **User** : login, password, active, roles

### Relations
- Un album peut avoir plusieurs chansons
- Une chanson appartient à un seul album

## 🚀 Fonctionnalités

### 💿 Gestion des Albums (`/api/user/albums/**`, `/api/admin/albums/**`)
#### Utilisateurs (USER, ADMIN)
- Lister les albums (pagination)
- Rechercher par titre (pagination + tri)
- Rechercher par artiste
- Filtrer par année (pagination + tri)

#### Administrateurs uniquement
- Ajouter un album
- Modifier un album
- Supprimer un album

### 🎵 Gestion des Chansons (`/api/user/songs/**`, `/api/admin/songs/**`)
#### Utilisateurs (USER, ADMIN)
- Lister les chansons (pagination)
- Rechercher par titre (pagination + tri)
- Lister les chansons d'un album (pagination + tri)

#### Administrateurs uniquement
- Ajouter une chanson
- Modifier une chanson
- Supprimer une chanson

### 👥 Gestion des Utilisateurs
- `/api/auth/login` : Authentification
- `/api/auth/register` : Création de compte
- `/api/admin/users` : Liste des utilisateurs (ADMIN)
- `/api/admin/users/{id}/roles` : Gestion des rôles (ADMIN)

## 🔒 Sécurité

- Spring Security avec JWT (JSON Web Token)
- Authentification stateless
- BCryptPasswordEncoder pour le cryptage des mots de passe
- Contrôle d'accès basé sur les rôles (ADMIN/USER)

## 🛠️ Technologies

- **Backend** : Spring Boot 2.7.18
- **Base de données** : MongoDB
- **Sécurité** : Spring Security, JWT
- **Tests** : JUnit, Mockito
- **Documentation** : Swagger
- **CI/CD** : Jenkins
- **Conteneurisation** : Docker
- **Registry** : DockerHub

## 📦 Prérequis

- Java 11+
- Maven 3.6+
- MongoDB
- Docker
- Git

## 🚀 Installation

```bash
# Cloner le repository
git clone https://github.com/asmaabarj/MusicManager.git

# Accéder au dossier
cd MusicManager

# Installer les dépendances
mvn clean install

# Lancer l'application
mvn spring-boot:run
```

## 🐳 Docker

```bash
# Construire l'image
docker build -t music-manager .

# Lancer le conteneur
docker run -p 8080:8080 music-manager
```

## 🧪 Tests

```bash
# Exécuter les tests
mvn test

# Exécuter les tests d'intégration
mvn verify
```

## 🔧 Configuration

L'application utilise différents profils :
- `dev` : Configuration de développement
- `prod` : Configuration de production

Pour lancer avec un profil spécifique :
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## 📅 Planification sur Jira avec gitFlow
https://asmaabarj5.atlassian.net/jira/software/projects/MUS/boards/364/backlog
