package daos;

import entities.Film;

public interface FilmDao {
     void addFilm(Film film);
     Film getFilmById(int id);
}
