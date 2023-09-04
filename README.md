# P6-Full-Stack-reseau-dev

Ce projet est une application complète (Full Stack) pour le réseau de développeurs. Il est construit avec Angular pour le front-end et Spring Boot pour le back-end.

## Front

Ce projet a été généré avec [Angular CLI](https://github.com/angular/angular-cli) version 14.1.3.

### Prérequis

- Assurez-vous d'avoir Node.js installé sur votre machine.
- Exécutez `npm install` pour installer les dépendances nécessaires avant de démarrer l'application.

### Serveur de développement

Exécutez `ng serve` pour démarrer un serveur de développement. Naviguez vers `http://localhost:4200/`. L'application se rechargera automatiquement si vous modifiez l'un des fichiers source.

### Compilation

Exécutez `ng build` pour compiler le projet. Les artefacts de construction seront stockés dans le répertoire `dist/`.

## Back-end (API)

Le back-end est construit avec Spring Boot version 2.7.3 et Java 11.

### Prérequis

- Java 11
- Maven

### Démarrer l'application

Exécutez la commande suivante pour démarrer l'application Spring Boot :

\```
mvn spring-boot:run
\```

### Dépendances principales

- Spring Boot Starter Security
- Spring Security Config
- Java JWT pour Auth0
- Lombok pour simplifier le code Java avec des annotations
- API de validation Java
- Spring Boot Starter Data JPA pour la persistance
- Spring Boot Starter Web pour la construction de l'API RESTful
- MySQL Connector Java pour la connexion à une base de données MySQL
- H2 Database pour les tests
- Springfox pour la documentation Swagger de l'API

### Documentation de l'API

Une fois que le back-end est démarré, vous pouvez accéder à la documentation Swagger de l'API en naviguant vers :

\```
http://localhost:8080/v2/api-docs
\```

### Génération de la Javadoc

Pour générer la Javadoc pour votre projet, exécutez la commande suivante :

\```
mvn javadoc:javadoc
\```

La Javadoc sera générée dans le répertoire `target/site/apidocs`.

### Configuration

Il est recommandé de remplacer les variables d'environnement suivantes par vos propres valeurs :

\```
spring.datasource.url=jdbc:mysql://localhost:3306/mdd?allowPublicKeyRetrieval=true
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}
spring.jpa.hibernate.ddl-auto=create-drop
jwt.secret=${JWT_SECRET}
\```
