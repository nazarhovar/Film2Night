package service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public interface Top250Service {
    void getTop(HttpServletResponse response) throws IOException, SQLException;
    void loadTop(HttpServletResponse response) throws IOException, SQLException;
    void scheduleFilmLoading();
}
