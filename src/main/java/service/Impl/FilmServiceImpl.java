package service.Impl;

import daos.Impl.FilmDaoImpl;
import entities.Film;
import load.FilmLoader;
import service.FilmService;
import util.ActionsUtil;
import util.PosterUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FilmServiceImpl implements FilmService {
    private final FilmDaoImpl filmDao;
    FilmLoader filmLoader = new FilmLoader();
    String outputDirectory = "/app/Posters";

    public FilmServiceImpl() throws SQLException, ClassNotFoundException {
        filmDao = new FilmDaoImpl();
    }

    public void getFilm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Film film = filmDao.getFilmById(ActionsUtil.getFilmId(request));
        if (film == null)
            response.getWriter().println("Film not found");
        else {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(film);
        }
    }

    public void loadFilm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Film film = filmLoader.loadFilm(ActionsUtil.getFilmId(request));

            filmDao.addFilm(film);

            PosterUtil.savePosterLocally(film, outputDirectory);

            response.setCharacterEncoding("UTF-8");
            response.getWriter().println("Film loaded successfully: " + film.getNameOriginal());
        } catch (Exception e) {
            response.getWriter().println("Error loading film");
        }
    }

    public void getTwoDaysFilms(HttpServletResponse response) throws IOException {
        List<Integer> twoDaysFilms = filmDao.getTwoDaysFilms();
        response.getWriter().println(twoDaysFilms);
    }
}
