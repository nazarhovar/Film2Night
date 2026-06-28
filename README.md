# Film2Night Loader

Microservice that fetches films from the [Kinopoisk API](https://kinopoiskapiunofficial.tech/) and stores them in a shared PostgreSQL database.

Part of the Film2Night platform (paired with the Main API service on port 8181).

## Prerequisites

- Java 17+
- PostgreSQL with database `film2night`

## Setup

1. **Configure secrets**

   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```

   Fill in your database password and Kinopoisk API key.

2. **Run**

   ```bash
   ./mvnw.cmd spring-boot:run
   ```

   Starts on port 8182.

## What it does

- Loads films by ID from Kinopoisk API with retry logic (3 attempts, exponential backoff)
- Loads TOP-250 films via scheduler (disabled by default — enable `top250.scheduler.enabled=true` in config)
- Updates existing films on re-sync (does not duplicate)
- Stores: poster, rating, year, length, trailer URL, and more

## Endpoints (internal, not meant for direct browser use)

| Endpoint | Description |
|----------|-------------|
| `POST /api/loadFilm?id={kinopoiskId}` | Load a single film |
| `POST /api/loadTop250` | Load TOP-250 films |

## Secrets

`application.properties` is in `.gitignore`. Never commit it. Use the `.example` file as a template.

## License

MIT
