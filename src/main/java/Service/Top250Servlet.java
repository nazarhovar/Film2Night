package Service;

import Actions.Actions;
import Daos.Top250Dao;
import Entities.Top250;
import Load.Top250Loader;
import Util.ActionsUtil;

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
        ActionsUtil.getAction(request);
        if (Actions.LOAD_TOP_ACTION.equals("action"))
            loadTop(response);
        else
            ActionsUtil.filmNotFound(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActionsUtil.getAction(request);
        if (Actions.GET_TOP_ACTION.equals("action")) try {
            getTop(response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        else
            ActionsUtil.filmNotFound(response);
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
