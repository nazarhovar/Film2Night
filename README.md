# Film2Night Loader

Microservice that fetches films from the [Kinopoisk API](https://kinopoiskapiunofficial.tech/) and stores them in a shared PostgreSQL database.

Part of the Film2Night platform (paired with the Main API service).

## What it does

- Loads films by ID from Kinopoisk API 
- Loads TOP-250 films
- Updates existing films 
- Stores: poster, rating, year, length, trailer URL, and more

## Endpoints

| Endpoint | Description |
|----------|-------------|
| `POST /api/loadFilm?id={kinopoiskId}` | Load a single film |
| `POST /api/loadTop250` | Load TOP-250 films |
