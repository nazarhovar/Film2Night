package Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ActionsUtil {

    public static void filmNotFound(HttpServletResponse response) throws IOException {
        response.getWriter().println("Invalid request");
    }
    public static int getFilmId(HttpServletRequest request) throws NumberFormatException {
        String idParam = request.getParameter("id");
        return Integer.parseInt(idParam);
    }

    public static String getAction(HttpServletRequest request) {
        return request.getParameter("action");
    }

}
