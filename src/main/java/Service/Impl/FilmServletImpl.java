package Service.Impl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

import ActionsEnum.Actions;
import Load.FilmLoader;
import Daos.Impl.FilmDaoImpl;
import Entities.Film;
import Service.FilmServlet;
import Util.ActionsUtil;

@WebServlet(name = "FilmServlet" , urlPatterns = {"/FilmServlet"})
public class FilmServletImpl extends HttpServlet implements FilmServlet {

    private final FilmDaoImpl filmDao;

    public FilmServletImpl() {
        filmDao = new FilmDaoImpl();
    }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = ActionsUtil.getAction(request);
            if (action == null) {
                response.getWriter().println("Action is null");
                return;
            }
            switch (Actions.valueOf(action)) {
                case getFilm:
                    getFilm(request, response);
                    break;
                case loadFilm:
                    loadFilm(request,response);
                    break;
                default:
                    ActionsUtil.filmNotFound(response);
            }
    }

    public void getFilm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Film film = filmDao.getFilmById(ActionsUtil.getFilmId(request));
        if (film == null)
            response.getWriter().println("Film not found");
          else {
            response.setContentType("application/json");
            response.getWriter().println(film);
        }
    }

    public void loadFilm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            FilmLoader filmLoader = new FilmLoader();
            Film film = filmLoader.loadFilm(ActionsUtil.getFilmId(request));

            filmDao.addFilm(film);

            response.getWriter().println("Film loaded successfully: " + film.getNameOriginal());
        } catch (Exception e) {
            response.getWriter().println("Error loading film");
        }
    }
}
