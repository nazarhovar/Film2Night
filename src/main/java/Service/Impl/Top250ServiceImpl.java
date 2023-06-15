package Service.Impl;

import Daos.Impl.Top250DaoImpl;
import Entities.Top250;
import Load.Top250Loader;
import Service.Top250Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Top250ServiceImpl implements Top250Service {
    private final Top250DaoImpl top250Dao;

    public Top250ServiceImpl() {
        top250Dao = new Top250DaoImpl();
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

    public void loadTop(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
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
