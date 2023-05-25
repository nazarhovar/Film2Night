package Service.Impl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

import Actions.Actions;
import Load.FilmLoader;
import Daos.Impl.FilmDaoImpl;
import Entities.Film;
import Service.FilmServlet;
import Util.ActionsUtil;

@WebServlet(name = "FilmServlet")
public class FilmServletImpl extends HttpServlet implements FilmServlet {

    public FilmServletImpl() throws SQLException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActionsUtil.getAction(request);
        if (Actions.LOAD_FILM_ACTION.equals("action"))
            loadFilm(response);
        else
            ActionsUtil.filmNotFound(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActionsUtil.getAction(request);
        if (Actions.GET_FILM_ACTION.equals("action"))
            getFilm(request, response);
        else
            ActionsUtil.filmNotFound(response);
    }

    public void getFilm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int filmId = Integer.parseInt(request.getParameter("id"));
        Film film = FilmDaoImpl.getFilmById(filmId);
        if (film == null)
            response.getWriter().println("Film not found");
          else {
            response.setContentType("application/json");
            response.getWriter().println(film.toJSON());
        }
    }

    public void loadFilm(HttpServletResponse response) throws IOException {
        try {
            Film film = (Film) FilmLoader.loadFilm();
            FilmDaoImpl.addFilm(film);
            response.getWriter().println("Film loaded successfully");
        } catch (Exception e) {
            response.getWriter().println("Error loading film");
        }
    }
}
