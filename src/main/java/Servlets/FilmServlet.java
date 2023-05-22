package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

import Actions.FilmActions;
import Load.FilmLoader;
import Daos.FilmDao;
import Entities.Film;

@WebServlet(name = "FilmId")
public class FilmServlet extends HttpServlet {

    public FilmServlet() throws SQLException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FilmActions.getAction(request);
        if (FilmActions.LoadFilmAction.equals(FilmActions.Action))
            loadFilm(request, response);
        else
            FilmActions.filmNotFound(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FilmActions.getAction(request);
        if (FilmActions.GetFilmAction.equals(FilmActions.Action))
            getFilm(request, response);
        else
            FilmActions.filmNotFound(response);
    }

    private void getFilm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        FilmActions.id = FilmActions.getFilmId(request);
        Film film = FilmDao.getFilmById(FilmActions.id);
        if (film == null)
            response.getWriter().println("Film not found");
          else {
            response.setContentType("application/json");
            response.getWriter().println(film.toJSON());
        }
    }

    private void loadFilm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        FilmActions.id = FilmActions.getFilmId(request);
        try {
            Film film = (Film) FilmLoader.loadFilm(FilmActions.id);
            FilmDao.addFilm(film);
            response.getWriter().println("Film loaded successfully");
        } catch (Exception e) {
            response.getWriter().println("Error loading film");
        }
    }
}
