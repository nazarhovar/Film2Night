package Controller;

import ActionsEnum.Actions;
import Service.Impl.Top250ServiceImpl;
import Util.ActionsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

@WebServlet(name = "Top250Servlet" , urlPatterns = {"/Top250Servlet"})
public class Top250ServletImpl extends HttpServlet {

    private Top250ServiceImpl top250ServiceImpl;

    @Override
    public void init() throws ServletException {
        super.init();
        top250ServiceImpl = new Top250ServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = ActionsUtil.getAction(request);
        if (action == null) {
            response.getWriter().println("Action is null");
            return;
        }
        switch (Actions.valueOf(action)) {
            case getTop:
                try {
                    top250ServiceImpl.getTop(response);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case loadTop:
                try {
                    top250ServiceImpl.loadTop(response);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
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
