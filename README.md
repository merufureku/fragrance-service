# Fragrance Service

The main catalog service - stores all perfume data including names, brands, descriptions, images, and fragrance notes (top, middle, base).

## Tech Stack

- Spring Boot 3.x
- Spring Security
- PostgreSQL
- Gradle
- Docker

## Endpoints

### Public
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/public/fragrances` | List all fragrances (with filters) |
| GET | `/public/fragrances/{id}` | Get fragrance details |
| GET | `/public/fragrances/{id}/notes` | Get fragrance notes |

### Admin Only
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/fragrances` | Add new fragrance |
| PUT | `/fragrances/{id}` | Update fragrance |
| DELETE | `/fragrances/{id}` | Delete fragrance |
| POST | `/fragrances/{id}/notes` | Add note to fragrance |
| DELETE | `/fragrances/{id}/notes/{noteId}` | Remove note |

### Internal (service-to-service)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/internal/fragrances/batch` | Get multiple fragrances by IDs |
| POST | `/internal/fragrances/notes/batch` | Get notes for multiple fragrances |

## Filtering & Search

The list endpoint supports filtering:

```
GET /public/fragrances?name=oud&brand=Tom Ford&gender=Male&type=EDP&page=0&size=20
```

Available filters:
- `name` - partial match
- `brand` - exact match
- `gender` - Male, Female, Unisex
- `type` - EDP, EDT, Parfum, etc.
- `country` - country of origin

## Data Model

**Fragrance**
- id, name, brand, description, imageUrl, gender, type, country

**Note**
- id, name, type (Top/Middle/Base)

**FragranceNotes** (join table)
- fragrance_id, note_id

## Brands in the DB

Focused on niche/luxury brands:
- Parfums de Marly
- Xerjoff
- Tom Ford
- Creed
- Initio
- Maison Francis Kurkdjian
- Le Labo
- Byredo
- Amouage
- And more...

## Environment Variables

```
DB_URL=jdbc:postgresql://localhost:5432/your_db
DB_USERNAME=your_username
DB_PASSWORD=your_password
ACCESS_SECRET=base64_encoded_key
INTERNAL_FRAGRANCE_SECRET=internal_service_key
```

## Running Locally

```bash
./gradlew bootRun
```

Swagger UI: `http://localhost:8082/api/fragrance-service/swagger-ui`

## Docker

```bash
docker build -t fragrance-service .
docker run -p 8082:8082 --env-file .env fragrance-service
```

## Project Structure

```
src/main/java/.../fragrance_service/
├── config/          # Security, CORS, JWT filter
├── controller/      # Public, Admin, Internal endpoints
├── dao/             # Fragrance, Note, FragranceNotes entities
├── dto/             # Request/response DTOs
├── helper/          # Specification builder for filtering
├── services/        # Business logic with factory pattern
└── utilities/       # Token utilities
```

## Internal API

The Recommendation Service calls the internal endpoints to get fragrance data in batch. These endpoints use a separate JWT signed with `INTERNAL_FRAGRANCE_SECRET`.

## Related Services

- [Auth Service](../auth-service) - Authentication
- [Collection Service](../collection-service) - User collections
- [Review Service](../review-service) - Reviews and ratings
- [Recommendation Service](../recommendation-service) - Uses this service for note data
