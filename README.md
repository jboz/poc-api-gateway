# poc-quarkus-auth

`quarkus dev`

`docker-compose up -d`  
Maybe the first time, the kong migration and service has to be restart

## Kong

http://localhost:8002

Executer les commandes de kong.http

## Keycloack

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

Executer les commandes de keycloack.http

Voir le contenu du token: https://www.jwt.io/
