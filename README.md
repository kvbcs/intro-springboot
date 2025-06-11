# intro-springboot

## Architecture

Ce projet respecte une architecture MVC générée automatiquement par SpringBoot, avec des dossiers Model Controller et Repository dans un dossier src/main/product_api

## Choix techniques

-   Application : Spring Boot

-   Spring Web : CRUD et Tomcat container

-   Spring Data JPA : Persistance des données avec Hibernate

-   Base de données : H2 Database

-   Spring Boot DevTools : Rechargement automatique à chaque changement

## Règles Métiers

-   Model : Instanciation des variables private avec getters et setters pour les réutiliser.

-   Controller : Création des routes et des divers fonctionnalités.

## Jeu de requêtes CURL

![](./requêtes%20curl.PNG)

## Jeu de tests

Avec la commande suivante, j'ai pu tester mon ProductControllerTest :

-   mvn test  
    ou
-   mvn -Dtest=ProductControllerTest test

![](./jeu%20de%20tests.PNG)
