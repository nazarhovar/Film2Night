package service.Impl;

import daos.Impl.Top250DaoImpl;
import entities.Top250;
import load.Top250Loader;
import scheduler.Top250Scheduler;
import service.Top250Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Top250ServiceImpl implements Top250Service {
    private final Top250DaoImpl top250Dao;

    public Top250ServiceImpl() throws SQLException, ClassNotFoundException {
        top250Dao = new Top250DaoImpl();
    }

    public void getTop(HttpServletResponse response) throws IOException, SQLException {
        List<Top250> top250 = top250Dao.getTop250();
        if (top250 == null)
            response.getWriter().println("Top not found");
        else {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.println("Top250 loaded successfully:");
            for (Top250 film : top250) {
                writer.println(film);
            }
        }
    }

    public void loadTop(HttpServletResponse response) throws IOException, SQLException {
        List<Top250> top250 = Top250Loader.loadTop();

        top250Dao.deleteTop250();
        top250Dao.addTop250(top250);

        response.setCharacterEncoding("UTF-8");
        response.getWriter().println("Top250 loaded successfully");
    }

    public void scheduleFilmLoading() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Top250Scheduler(), 0, 360, TimeUnit.SECONDS);
    }
}
