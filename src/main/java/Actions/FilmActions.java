package Actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilmActions {
    public static final String LoadFilmAction = "loadFilm";
    public static final String GetFilmAction = "getFilm";
    public static String Action = "Action";
    public static int id;

    public static void filmNotFound(HttpServletResponse response) throws IOException {
        response.getWriter().println("Invalid request");
    }

    public static String getAction(HttpServletRequest request ) {
        return request.getParameter("action");
    }

    public static int getFilmId(HttpServletRequest request) {
        if (GetFilmAction.equals(getAction(request))) {
            return Integer.parseInt(request.getParameter("id"));
        }
        else return -1;
    }
}

