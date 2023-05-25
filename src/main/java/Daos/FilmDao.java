package Daos;

import Entities.Film;

public interface FilmDao {
    void addFilm(Film film);
    Film getFilmById(int id);
}
