# Auth server
Auth server using spring and Google login

## Actual version
````
0.2.0.0
````

## Requirements
````
Needed mongodb database
docker run -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=mongo -e MONGO_INITDB_ROOT_PASSWORD=password --name mongo -d mongo
````

## Important info

Login / Access / Authorize
````
scopes -> wanted scope for the generated jwt ( if empty will get all from the user )
roles -> wanted roles for the generated jwt ( if empty will get all from the user )
````

Tokens
````
oauth2/authorize and google tokens generated with a google client_id are just accepted by /oauth2/tokenInfo 
and /oauth2/authorize/userInfo endpoints, these tokens are for readonly external apps,
NOT VALID for auth server administration

** all other endpoints will return UNAUTHORIZED

to get a full permission token you will need to follow one of the normal flows
1.- login endpoint, access endpoint
2.- get the token from google using the default google client_id
````

tokenInfo and authorize/userInfo endpoint
````
allowed tokens:

* without client_id:
- Bearer {session_token} ( from login endpoint )
- Bearer {id_token} ( from access endpoint )
- Google {google id_token} ( from google login button )

* with client_id:
- {id_token} ( from authorize endpoint, requires "client_id" param )
- Google {google id_token} ( from google login button using Client.google_client_id, requires "client_id" param )
````

Default admin user
````
Change default values in the application.properties 

username: admin
email: admin@admin.com
password: string
````
Default user-role-scope relations
````
-------------------------------
|          admin              |
-------------------------------
| ROLE_ADMIN |    ROLE_USER   |
|------------|----------------|
| -> READ    | -> READ_USER   |
| -> CREATE  | -> UPDATE_USER |
| -> UPDATE  |-----------------
| -> DELETE  |
--------------
````
Default roles
````
ROLE_ADMIN: admin role to manage the server
ROLE_USER: default role for all users
````
Default scope
````
READ: admin scope for data reading
CREATE: admin scope for object insertion
UPDATE: admin scope for update objects info
DELETE: admin scope for object deleting
READ_USER: user scope for data reading
UPDATE_USER: user scope for update objects info
````
### Users locking
````
When a user is locked will not have permissions to call:

- login use case
- access use case
- user info use case
- google login use case
- authorize use case

** can use another endpoints if have a valid jwt so the "lock" process will be completed when the current "access token" expires ( by default 5 min )
````


### Default calls security
clients
````
GET /clients -> ROLE_ADMIN AND READ
GET /clients/{clientId} -> ROLE_ADMIN AND READ
POST /clients -> ROLE_ADMIN AND CREATE
PUT /clients/{clientId} -> ROLE_ADMIN AND UPDATE
DELETE /clients/{clientId} -> ROLE_ADMIN AND DELETE
````

oauth2
````
POST /access -> authentication not required
POST /login -> authentication not required
POST /authorize -> authentication not required
DELETE /logout -> authentication not required
POST /tokenInfo -> authentication not required
POST /userInfo -> ( ROLE_ADMIN OR ROLE_USER ) AND READ_USER
POST /authorize/userInfo -> authentication not required
````

roles
````
GET /roles -> ROLE_ADMIN AND READ
POST /roles -> ROLE_ADMIN AND CREATE
DELETE /roles/{roleId} -> ROLE_ADMIN AND DELETE
PUT /roles/{roleId}/scopes -> ROLE_ADMIN AND UPDATE
DELETE /roles/{roleId}/scopes -> ROLE_ADMIN AND DELETE
````

scopes
````
GET /scopes -> ROLE_ADMIN AND READ
POST /scopes -> ROLE_ADMIN AND CREATE
DELETE /scopes/{scopeId} -> ROLE_ADMIN AND DELETE
````

users
````
GET /users -> ROLE_ADMIN AND READ
POST /users -> ROLE_ADMIN AND CREATE
PUT /users/{userId}/roles -> ROLE_ADMIN AND UPDATE
PUT /users/password -> ( ROLE_ADMIN OR ROLE_USER ) AND UPDATE_USER
PUT /users/{userId}/password -> ROLE_ADMIN AND UPDATE
PUT /users/{userId}/lock -> ROLE_ADMIN AND UPDATE
PUT /users/{userId}/unlock -> ROLE_ADMIN AND UPDATE
DELETE /users/{userId}/roles -> ROLE_ADMIN AND DELETE
````
**** Do not delete any initial role or scope !!
## App profiles

Current supported profiles
````
- test: used to run the application tests 
- dev: used for development
- pre: used in the pre-production enviroment
- pro: used in the production enviroment
````

## Code Structure
![alt text](https://reflectoring.io/assets/img/posts/spring-hexagonal/hexagonal-architecture.png)
![alt text](https://miro.medium.com/max/1718/1*yR4C1B-YfMh5zqpbHzTyag.png)
