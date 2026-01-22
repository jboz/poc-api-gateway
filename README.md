# poc-api-gateway

![gateway](api-gateway.excalidraw.png)

## 1. Lancement du premier backend

`mvn -pl backend-simple compile quarkus:dev`

## 2. Démarrage des services

`docker-compose up -d`

## 3. Sécurisation avec Kong

Kong est accessible à cette adresse : http://localhost:8002  
Exécuter les commandes de `kong.http` pour le configurer via son API.  

Dans ces commandes, on retrouve :  
   1. création d'un service
   2. création d'une route vers un backend
   3. appel du backend via Kong, sans sécurisation
   4. ajout du plugin key-auth sur la route
   5. création d'un consommateur key-auth
   6. génération d'un clé pour ce consommateur
   7. avec cette config sur la route, le backend n'est plus accessible sans la clé
   8. appel du backend avec la clé
   9. ajout d'une autre bric de sécurité avec un plugin rate-limit

## 4. Sécuration avec Keycloak et OIDC

Configuration préalable de keycloak :  
   1. Log into Keycloak Admin Console http://localhost:8090 (admin/admin)
   2. Create a new realm named secure-api by clicking the “Keycloak” dropdown
   3. Create a new application client (click the Client menu):  
      Client ID: secure-api-client  
      Client Protocol: openid-connect  
      Check Client authentication  
      Check Standard Flow, Standard Token Exchange and Direct acccess grants  
      Root URL: http://192.168.1.42:8090  
      Set Valid Redirect URIs to '\*'  
      Copy the Client Secret (Credentials tab)  
   4. Create a Role (e.g., user)
   5. Create a User:  
         Username: testuser  
         Password: test123  
         Assign the user role to this user  

Executer les commandes de `keycloack.http` pour configurer kong via api.  
Des appels REST pour tester la configuration.

## Extract user info from the JWT

https://quarkus.io/guides/security-oidc-bearer-token-authentication-tutorial

Add `quarkus-oidc` extension and the `quarkus.oidc.auth-server-url` and `quarkus.oidc.client-id` properties.  
After that the `UsersResource` class can be used to extract user info from the JWT:

```java
@Inject
SecurityIdentity securityIdentity;

@Inject
JsonWebToken jsonWebToken;

@Inject
@Claim(standard = Claims.email)
String email;
```

## 5. Propagate the access token to the downstream service

Execute `quarkus extension add rest-client-oidc-token-propagation` and Annotate RestClient with `@AccessToken`.  

`mvn -pl backend-other compile quarkus:dev`  
