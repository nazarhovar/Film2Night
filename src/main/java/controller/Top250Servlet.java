package controller;

import actionsEnum.Actions;
import service.Impl.Top250ServiceImpl;
import util.ActionsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

@WebServlet(name = "Top250Servlet" , urlPatterns = {"/Top250Servlet"})
public class Top250Servlet extends HttpServlet {

    private Top250ServiceImpl top250ServiceImpl;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            top250ServiceImpl = new Top250ServiceImpl();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error initializing top250 servlet: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = ActionsUtil.getAction(request);
        if (action == null) {
            response.getWriter().println("Action is null");
            return;
        }

        switch (Actions.valueOf(action)) {
            case GET250:
                try {
                    top250ServiceImpl.getTop(response);
                } catch (SQLException throwables) {
                    response.getWriter().println("Error retrieving Top250: " + throwables.getMessage());
                }
                break;
            case LOAD250:
                try {
                    top250ServiceImpl.loadTop(response);
                } catch (SQLException throwables) {
                    response.getWriter().println("Error loading Top250: " + throwables.getMessage());
                }
                break;
            case SCHEDULER:
                top250ServiceImpl.scheduleFilmLoading();
                response.getWriter().println("Scheduler loading films");
                break;
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ActionsUtil.filmNotFound(response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        top250ServiceImpl = null;
    }
}
