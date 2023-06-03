package Service.Impl;

import ActionsEnum.Actions;
import Daos.Impl.Top250DaoImpl;
import Entities.Top250;
import Load.Top250Loader;
import Service.Top250Servlet;
import Util.ActionsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

@WebServlet(name = "Top250Servlet")
public class Top250ServletImpl extends HttpServlet implements Top250Servlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void getTop(HttpServletResponse response) throws IOException, SQLException {
        Top250DaoImpl top250Dao = new Top250DaoImpl();
        Top250 top250 = (Top250) top250Dao.getTop250();
        response.setContentType("application/json");
        response.getWriter().println(top250);
    }

    public void loadTop(HttpServletResponse response) throws IOException {
        try {
            Top250 top250 = (Top250) Top250Loader.loadTop();
            Top250DaoImpl top250Dao = new Top250DaoImpl();
            top250Dao.addTop250(top250);
            response.getWriter().println("Top films loaded successfully");
        } catch (Exception e) {
            response.getWriter().println("Error loading top films");
        }
    }
}
