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
import java.util.List;

@WebServlet(name = "Top250Servlet" , urlPatterns = {"/Top250Servlet"})
public class Top250ServletImpl extends HttpServlet implements Top250Servlet {

    private final Top250DaoImpl top250Dao;

    public Top250ServletImpl() {
        top250Dao = new Top250DaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = ActionsUtil.getAction(request);
        if (action == null) {
            response.getWriter().println("Action is null");
            return;
        }
        switch (Actions.valueOf(action)) {
            case getTop:
                try {
                    getTop(response);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case loadTop:
                try {
                    loadTop(request, response);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ActionsUtil.filmNotFound(response);
        }
    }

    public void getTop(HttpServletResponse response) throws IOException, SQLException {
        List<Top250> top250 = top250Dao.getTop250();
        if (top250 == null)
            response.getWriter().println("Top not found");
        else {
            response.setContentType("application/json");
            response.getWriter().println(top250);
        }
    }

    public void loadTop(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException {
        String pageStr = request.getParameter("page");
        int page = 1;
        if (pageStr != null && !pageStr.isEmpty()) {
            page = Integer.parseInt(pageStr);
        }

        List<Top250> top250 = Top250Loader.loadTop(page);

        top250Dao.addTop250(top250);

        response.getWriter().println("Top250 loaded successfully" + top250);
    }
}
