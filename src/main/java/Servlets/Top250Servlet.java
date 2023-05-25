package Servlets;

import Actions.FilmActions;
import Daos.Top250Dao;
import Entities.Top250;
import Load.Top250Loader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

@WebServlet(name = "Top250Servlet")
public class Top250Servlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FilmActions.getAction(request);
        if (FilmActions.LoadTopAction.equals(FilmActions.Action))
            loadTop(response);
        else
            FilmActions.filmNotFound(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FilmActions.getAction(request);
        if (FilmActions.GetTopAction.equals(FilmActions.Action)) {
            try {
                getTop(response);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else
            FilmActions.filmNotFound(response);
    }

    private void getTop(HttpServletResponse response) throws IOException, SQLException {
        Top250 top250 = (Top250) Top250Dao.getTop250();
        response.setContentType("application/json");
        response.getWriter().println(top250.toJSON());
    }

    private void loadTop(HttpServletResponse response) throws IOException {
        try {
            Top250 top250 = (Top250) Top250Loader.loadTop();
            Top250Dao.addTop250(top250);
            response.getWriter().println("Top films loaded successfully");
        } catch (Exception e) {
            response.getWriter().println("Error loading top films");
        }
    }
}
