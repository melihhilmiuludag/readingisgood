# Introduction

The documentation for the ReadingIsGood Service API. 

Project development by **Melih Hilmi Uludağ.**

**e-Mail Address:** melihhilmiuludag@gmail.com

**Project Name:** `readingisgood`

**Special package naming:** `mhu.core.*`   :) 

### Using Technology
* JDK 17: amazoncorretto:17.0.8-alpine
* Spring Boot 3.0.5
* Apache maven 3.8.1
* Spring Security
* Lombok
* Docker, docker-compose
* Postgres DB
* Mongo DB
* Mockito

### Using Design Patterns
* DDD (Domain Driven Design)
* Builder Pattern


### Operation of the System

Firstly, the customer registers to the system and log in to the system. Once authenticated, the customer will receive a JWT token that will be used to authenticate all subsequent requests.

You can read the documentation provided by **Swagger** (`/swagger-ui.html`).

Application language default is english. But you can change it.

**Your Request in Header:**

Accept-Language: tr-TR

(default en-EN)

# Best Practice While Coding
* Using SOLID principle.
* Using DDD
* Attention was paid to clean code.
* Using domain model.
* Developed global exception handling.
* Developed logging.
* Generating jwt token with spring security OAuth2.
* Integrated docker compose.
* Validations in dto and entity.
* Open API Specification.

# Authenticate
In order to use the API, customers must authenticate themselves with valid credentials.

## Register User
Register a new user, use the following endpoint:

**Endpoint: `/api/auth/register`**

**http.Method: `POST`**

**Headers:**

* **Content-Type: `application/json`**
* **Accept-Language:** `tr-TR`

**Request body:**

```json
{
  "username": "melihhilmiuludag",
  "email": "melihhilmiuludag@gmail.com",
  "password": "12345"
}
```

**Response:**

```json
{
  "message": "xyz@gmail.com mail adres başarılı bir şekilde kaydedildi."
}
```

After successfully,you can generate a JWT token.

## Login in System

Generate a new JWT token, use the following:

**Endpoint: `/api/auth/login`**

**http.Method: `POST`**

**Headers:**

* **Content-Type: `application/json`**

**Request body:**

```json
{
  "email": "melihhilmiuludag@gmail.com",
  "password": "12345"
}
```

**Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtZWxpaGhpbG1pdWx1ZGFnQGdtYWlsLmNvbSIsImlhdCI6MTY5NjM1MDExOSwiZXhwIjoxNjk2MzU3MzE5fQ.18tyCaTIuxZom8t1OyBBxzAmqH33i4MGaUQBYbuTHiI"
}

```

## Dockerize with docker-compose

Generating a jar file inside the project folder in the directory `/target/readingisgood-1.0.jar`

To build the project using with docker-compose use the following command:

```console
docker compose build
```

And then run for use the following command:

```console
docker compose up readingisgood
```

This command starts the container and maps **port 8080** of the container to port 8080 of the host machine, allowing you to access the application point: **`localhost:8080`**.

If you want to close the project, kill the containers:

```console
docker compose down
```

  
