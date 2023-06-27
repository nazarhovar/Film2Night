package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

import actionsEnum.Actions;
import service.Impl.FilmServiceImpl;
import util.ActionsUtil;

@WebServlet(name = "FilmServlet" , urlPatterns = {"/FilmServlet"})
public class FilmServlet extends HttpServlet {

    private FilmServiceImpl filmServiceImpl;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            filmServiceImpl = new FilmServiceImpl();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error initializing film servlet: " + e.getMessage());
        }
    }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = ActionsUtil.getAction(request);
            if (action == null) {
                response.getWriter().println("Action is null");
                return;
            }

            switch (Actions.valueOf(action)) {
                case GETFILM:
                    filmServiceImpl.getFilm(request, response);
                    break;
                case LOADFILM:
                    filmServiceImpl.loadFilm(request,response);
                    break;
                case TWODAYS:
                    filmServiceImpl.getTwoDaysFilms(response);
                    break;
                default:
                    ActionsUtil.filmNotFound(response);
            }
    }

    @Override
    public void destroy() {
        super.destroy();
        filmServiceImpl = null;
        }
}
