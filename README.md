# Proyecto Spring Web Flux Matricula

Proyecto API REST para el control de registros de matrículas, se desarrolla en Spring WebFlux y MongoDB, despliegue con Docker Compose.

## Características
- API reactiva para operaciones CRUD de entidades enrollment, student, courses y usuario.
- Seguridad JWT y rol (admin).
- Inicialización automática de datos de usuario y roles.
- Conexión a MongoDB en contenedor.

## Requisitos previos
- [Docker y Docker Compose](https://docs.docker.com/get-docker/) instalados.

## Pasos para desplegar el proyecto

### 1. Clonar repositorio
```bash
git clone <url-del-repo>
cd spring-matricula
```

### 2. Compilar el proyecto (opcional si solo usas Docker Compose)
```bash
mvn clean package -DskipTests
```

### 3. Levantar los servicios
```bash
docker compose up -d --build
```
Se crea y levanta dos contenedores:
- `mongodb`: BDD MongoDB en puerto 27017
- `matricula_app`: Backend Spring en puerto 8080

## Endpoints disponibles

> **Nota:** Cambia `<jwt-token>` por el token real recibido en el login.

### Autenticación

#### POST `/login`
- **Descripción:** Obtiene un token JWT válido.
- **URL completa:** `http://localhost:8080/login`
- **Body ejemplo:**
  ```json
  {
    "username": "admin",
    "password": "admin123"
  }
  ```
- **Curl:**
  ```bash
  curl -X POST http://localhost:8080/login \
    -H "Content-Type: application/json" \
    -d '{"username":"admin","password":"admin123"}'
  ```
- **Respuesta exitosa:**
  ```json
  {
    "token": "<jwt-token>"
  }
  ```

---

### Courses - Cursos

#### GET `/v1/courses`
- **Descripción:** Lista todos los cursos
- **URL completa:** `http://localhost:8080/v1/courses`
- **Curl:**
  ```bash
  curl -H "Authorization: Bearer <jwt-token>" http://localhost:8080/v1/courses
  ```

#### GET `/v1/courses/{id}`
- **Descripción:** Obtiene curso por ID
- **URL completa:** `http://localhost:8080/v1/courses/ID_CURSO`
- **Curl:**
  ```bash
  curl -H "Authorization: Bearer <jwt-token>" http://localhost:8080/v1/courses/ID_CURSO
  ```

#### POST `/v1/courses`
- **Descripción:** Crea nuevo curso
- **URL completa:** `http://localhost:8080/v1/courses`
  - **Body ejemplo:**
    ```json
    {
      "name": "WebFlux",
      "initial": "WF",
      "enabled": true
    }
    ```
- **Curl:**
  ```bash
  curl -X POST http://localhost:8080/api/v1/courses \
    -H "Authorization: Bearer <jwt-token>" \
    -H "Content-Type: application/json" \
    -d '{
      "name": "WebFlux",
      "initial": "WF",
      "enabled": true
    }'
  ```

#### PUT `/v1/courses/{id}`
- **Descripción:** Actualiza curso
- **URL completa:** `http://localhost:8080/v1/courses/ID_CURSO`
- **Body ejemplo:**
  ```json
  {
   "name": "Curso Spring WebFlux",
   "initial": "SWF"
  }
  ```
- **Curl:**
  ```bash
  curl -X PUT http://localhost:8080/api/v1/courses/ID_CURSO \
    -H "Authorization: Bearer <jwt-token>" \
    -H "Content-Type: application/json" \
    -d '{"name": "Curso Spring WebFlux","initial": "SWF"}'
  ```

#### DELETE `/v1/courses/{id}`
- **Descripción:** Elimina curso
- **URL completa:** `http://localhost:8080/v1/courses/ID_CURSO`
- **Curl:**
  ```bash
  curl -X DELETE http://localhost:8080/v1/courses/ID_CURSO \
    -H "Authorization: Bearer <jwt-token>"
  ```
---

### Students - Estudiantes

#### GET `/v1/students`
- **Descripción:** Lista todos los estudiantes
- **URL completa:** `http://localhost:8080/v1/students`
- **Curl:**
  ```bash
  curl -H "Authorization: Bearer <jwt-token>" http://localhost:8080/v1/students
  ```

#### GET `/v1/students/{id}`
- **Descripción:** Obtiene estudiante por ID
- **URL completa:** `http://localhost:8080/v1/students/ID_ESTUDIANTE`
- **Curl:**
  ```bash
  curl -H "Authorization: Bearer <jwt-token>" http://localhost:8080/v1/students/ID_ESTUDIANTE
  ```

#### GET `/v1/students/edad/asc`
- **Descripción:** Lista estudiantes ordenados por edad ascendente
- **URL completa:** `http://localhost:8080/v1/students/edad/asc`
- **Curl:**
  ```bash
  curl -H "Authorization: Bearer <jwt-token>" http://localhost:8080/api/v2/estudiantes/ordenados/asc
  ```

#### GET `/v1/students/edad/desc`
- **Descripción:** Lista estudiantes ordenados por edad descendente
- **URL completa:** `http://localhost:8080/v1/students/edad/desc`
- **Curl:**
  ```bash
  curl -H "Authorization: Bearer <jwt-token>" http://localhost:8080/api/v2/estudiantes/ordenados/desc
  ```

#### POST `/v1/students`
- **Descripción:** Crea nuevo estudiante
- **URL completa:** `http://localhost:8080/v1/students`
  - **Body ejemplo:**
    ```json
    { "firstName":"Pedro", "lastName":"Chávez", "dni":"01010109", "age":40 }
    ```
- **Curl:**
  ```bash
  curl -X POST http://localhost:8080/v1/students \
    -H "Authorization: Bearer <jwt-token>" \
    -H "Content-Type: application/json" \
    -d '{ "firstName":"Pedro", "lastName":"Chávez", "dni":"01010109", "age":40 }'
  ```

#### PUT `/v1/students/{id}`
- **Descripción:** Actualiza estudiante
- **URL completa:** `http://localhost:8080/v1/students/ID_ESTUDIANTE`
- **Body ejemplo:**
  ```json
  {
    "id": "ID_ESTUDIANTE",
    "firstName": "Sofía Paola",
    "lastName": "Alvarez Moya",
    "dni": "12345678",
    "age": 50
  }
  ```
- **Curl:**
  ```bash
  curl -X PUT http://localhost:8080/v1/students/ID_ESTUDIANTE \
    -H "Authorization: Bearer <jwt-token>" \
    -H "Content-Type: application/json" \
    -d '{
    "id": "ID_ESTUDIANTE",
    "firstName": "Sofía Paola",
    "lastName": "Alvarez Moya",
    "dni": "12345678",
    "age": 50
    }'
  ```

#### DELETE `/v1/students/{id}`
- **Descripción:** Elimina estudiante
- **URL completa:** `http://localhost:8080/v1/students/ID_ESTUDIANTE`
- **Curl:**
  ```bash
  curl -X DELETE http://localhost:8080/v1/students/ID_ESTUDIANTE \
    -H "Authorization: Bearer <jwt-token>"
  ```

---

### Enrollments - Matriculas

#### GET `/v1/enrollments`
- **Descripción:** Lista todas las matrículas
- **URL completa:** `http://localhost:8080/v1/enrollments`
- **Curl:**
  ```bash
  curl -H "Authorization: Bearer <jwt-token>" http://localhost:8080/v1/enrollments
  ```

#### GET `/v1/enrollments/{id}`
- **Descripción:** Obtiene una matrícula por ID
- **URL completa:** `http://localhost:8080/v1/enrollments/ID_MATRICULA`
- **Curl:**
  ```bash
  curl -H "Authorization: Bearer <jwt-token>" http://localhost:8080/v1/enrollments/ID_MATRICULA
  ```

#### POST `/v1/enrollments`
- **Descripción:** Crea una nueva matrícula
- **URL completa:** `http://localhost:8080/v1/enrollments`
- **Body ejemplo:**
  ```json
  {
    "dateEnrollment": "2025-11-05T14:00:00",
    "student": { "id": "690c1286d698a73996d4f388" },
    "courses": [
      { "id": "690c193ed698a73996d4f38c" },
      { "id": "690c1975d698a73996d4f390" },
      { "id": "690c1958d698a73996d4f38e" }
    ]
  }
  ```
- **Curl:**
  ```bash
  curl -X POST http://localhost:8080/v1/enrollments \
    -H "Authorization: Bearer <jwt-token>" \
    -H "Content-Type: application/json" \
    -d '{
      "dateEnrollment": "2025-11-05T14:00:00",
      "student": { "id": "690c1286d698a73996d4f388" },
      "courses": [
        { "id": "690c193ed698a73996d4f38c" },
        { "id": "690c1975d698a73996d4f390" },
        { "id": "690c1958d698a73996d4f38e" }
      ]
    }'
  ```

#### PUT `/v1/enrollments/{id}`
- **Descripción:** Actualiza una matrícula existente
- **URL completa:** `http://localhost:8080/v1/enrollments/ID_MATRICULA`
- **Body ejemplo:**
  ```json
  {
  "student": {
      "id": "689e4252e4d37407a79cd9ee"
  },
  "courses": [
      {
          "id": "689e8467fd811545a3c4c79c"
      }
  ],
  "dateEnrollment": "2025-10-06"
  }
  ```
- **Curl:**
  ```bash
  curl -X PUT http://localhost:8080/v1/enrollments/ID_MATRICULA \
    -H "Authorization: Bearer <jwt-token>" \
    -H "Content-Type: application/json" \
    -d '{
  "student": {
      "id": "689e4252e4d37407a79cd9ee"
  },
  "courses": [
      {
          "id": "689e8467fd811545a3c4c79c"
      }
  ],
  "dateEnrollment": "2025-10-06"
    }'
  ```

#### DELETE `/v1/enrollments/{id}`
- **Descripción:** Elimina una matrícula
- **URL completa:** `http://localhost:8080/v1/enrollments/ID_MATRICULA`
- **Curl:**
  ```bash
  curl -X DELETE http://localhost:8080/v1/enrollments/ID_MATRICULA \
    -H "Authorization: Bearer <jwt-token>"
  ```
---

> **Nota:** Todos los endpoints (excepto `/login`) necesitan JWT en el header `Authorization`, el Token se obtiene con el endpoint de login.

## Datos iniciales
Al iniciar, se crean el usuario por defecto:
- **admin / admin123** (roles: ADMIN, USER)

## Troubleshooting
- Si el backend no arranca, revisa los logs con:
  ```bash
  docker logs matricula_app
  ```
- Si MongoDB no responde, revisa los logs con:
  ```bash
  docker logs mongodb
  ```
- Si cambias el código Java, recuerda recompilar y reconstruir la imagen:
  ```bash
  mvn clean package -DskipTests
  docker compose up -d --build
  ```

## Licencia
Proyecto Final Spring Webflux - Luis Soria.
