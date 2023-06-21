package Service.Impl;

import Daos.Impl.Top250DaoImpl;
import Entities.Top250;
import Load.Top250Loader;
import Service.Top250Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(top250);
        }
    }

    public void loadTop(HttpServletResponse response) throws IOException, SQLException {
        List<Top250> top250 = Top250Loader.loadTop();

        top250Dao.deleteTop250();
        top250Dao.addTop250(top250);

        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println("Top250 loaded successfully:");
        for (Top250 film : top250) {
            writer.println(film);
        }
    }
}
