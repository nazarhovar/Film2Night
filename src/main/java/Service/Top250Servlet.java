package Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public interface Top250Servlet {
    void getTop(HttpServletResponse response) throws IOException, SQLException;
    void loadTop(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException;
}