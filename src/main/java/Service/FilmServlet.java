package Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FilmServlet {
    void getFilm(HttpServletRequest request, HttpServletResponse response) throws IOException;
    void loadFilm(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
