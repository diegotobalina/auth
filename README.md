# Auth server
Auth server using spring and Google login

## General information
Deployed app
````
https://xbidi-auth.herokuapp.com
````
Code formatter
````
https://github.com/google/google-java-format
````
Dependency manager
````
maven
````
## Important info
Default admin user
````
username: admin
email: admin@admin.com
password: <generated on first startup and showed in the logs>
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
### Default calls security
oauth2
````
POST /access -> authentication not required
POST /login -> authentication not required
DELETE /logout -> authentication not required
POST /tokenInfo -> authentication not required
POST /userInfo -> ( ROLE_ADMIN OR ROLE_USER ) AND READ_USER
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

## Application versioning
Api version
````
application.properties -> api.version=X.X.X.X
{major release}.{major change in a component}.{minor change in a component}.{resolved bugs}
````
Api url versioning
````
/api/v0/{resource}
** vX must match with the major release version
````

## Code Structure
![alt text](https://reflectoring.io/assets/img/posts/spring-hexagonal/hexagonal-architecture.png)
![alt text](https://miro.medium.com/max/1718/1*yR4C1B-YfMh5zqpbHzTyag.png)

## Install and deploy
Install Java ubuntu
````
sudo apt-get install openjdk-14-jdk
````

Deploy app in docker
````
sh deploy.sh
````

Deploy app in heroku
````
heroku login
heroku create
heroku config:set GOOGLE_CLIENT_ID=<client_id>
heroku config:set MONGODB_URI=<mongodb_uri>
git push heroku master
heroku ps:scale web=1
heroku open
heroku logs --tail
````
See heroku logs
````
heroku logs --tail --app xbidi-auth --source app | ccze -o nolookups
````
