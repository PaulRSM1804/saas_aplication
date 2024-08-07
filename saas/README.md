
# Proyecto backend ll parcial

Primero se debe crear una base de datos en postgres con el nombre de "suscripcion"
## Generacion de las consultas de los endpoints

#### Post app_users

```http
  POST http://localhost:8080/api/users/register

```

#### Get all app_users

```http
  GET http://localhost:8080/api/users

```

#### Get app_users by id

```http
  GET http://localhost:8080/api/users/{id}
```

#### Post courses

```http
  POST http://localhost:8080/api/courses/create

```

#### Get all app_users

```http
  GET http://localhost:8080/api/courses/

```

#### Get courses by id

```http
  GET http://localhost:8080/api/courses/creator/{creatorId}
```

#### Post subscriptions

```http
  POST http://localhost:8080POST /api/subscriptions/create?consumerId=2&courseId=1

```

#### Get all subscriptions

```http
  GET http://localhost:8080/api/subscriptions
```

#### Get subscriptions by id

```http
  GET http://localhost:8080/api/subscriptions/consumer/{consumerId}

