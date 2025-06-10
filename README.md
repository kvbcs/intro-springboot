# intro-springboot

## Architecture

Ce projet respecte une architecture MVC, avec des dossiers Model Controller et Repository dans un dossier src/main/product_api

## Choix techniques

Application : Spring Boot
Spring Web : CRUD et Tomcat container
Spring Data JPA : Persistance des données avec Hibernate
Base de données : H2 Database
Spring Boot DevTools : Rechargement automatique à chaque changement


## Règles Métiers

Model : Instanciation des variables private avec getters et setters pour les réutiliser.
Controller : Instanciation des routes et fonctions