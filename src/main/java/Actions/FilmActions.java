package Actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilmActions {
    public static final String LoadFilmAction = "loadFilm";
    public static final String LoadTopAction = "loadTop";
    public static final String GetTopAction = "getTop";
    public static final String GetFilmAction = "getFilm";
    public static String Action = "action";

    public static void filmNotFound(HttpServletResponse response) throws IOException {
        response.getWriter().println("Invalid request");
    }

    public static String getAction(HttpServletRequest request ) {
        return request.getParameter("action");
    }
}

