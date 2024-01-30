# Gestion des Matchs Sportifs

Ce projet vise à fournir une solution complète pour la gestion des rencontres sportives, incluant la saisie des buts, des pénalités et la visualisation des détails des participants.

## Architecture du Projet

Le projet est organisé selon une architecture MVC (Modèle-Vue-Contrôleur) et est composé des packages suivants :

- **Model**: Contient les entités métier et les classes de données.
- **DAO**: Gère l'accès aux données et les opérations de persistance.
- **Resource**: Expose les services RESTful pour l'interaction avec le système depuis des clients externes.
- **Views**: Comprend les pages JSP et JSTL utilisées pour l'interface utilisateur, avec l'utilisation de Bootstrap pour un design responsive.

## Fonctionnalités

- Gestion des rencontres sportives.
- Saisie des buts et des pénalités.
- Visualisation des détails des participants.


## Technologies
- Java: Langage de programmation principal utilisé pour le développement de l'application.
Servlets et JSP (JavaServer Pages): Utilisés pour la création des pages web dynamiques et pour gérer les requêtes HTTP.
- JSTL (JavaServer Pages Standard Tag Library): Utilisée pour simplifier l'écriture des pages JSP en fournissant des tags prédéfinis.
- Hibernate: Framework de mapping objet-relationnel (ORM) utilisé pour la gestion de la persistance des données dans la base de données.
- MySQL: Système de gestion de base de données relationnelle utilisé pour stocker les données de l'application.
- RESTful Web Services: Utilisés pour créer des services web qui peuvent être consommés par des clients externes.
- Bootstrap: Framework front-end utilisé pour la conception d'interfaces utilisateur réactives et attrayantes.
- HTML et CSS: Utilisés pour la structure et le style des pages web.
- Maven: Outil de gestion de projet utilisé pour la construction, le reporting et les dépendances du projet Java.
- Tomcat: Serveur web utilisé pour exécuter l'application Java EE.

## Installation

1. Cloner le projet depuis GitHub.
2. Importer le projet dans votre IDE préféré.
3. Configurer la base de données et les paramètres de connexion dans le fichier `hibernate.cfg.xml`.
4. Compiler et exécuter le projet.

## Utilisation

- Accéder à l'interface utilisateur via le navigateur web à l'adresse suivante : [http://localhost:8080/](http://localhost:8080/).
