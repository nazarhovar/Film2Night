package Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import ActionsEnum.Actions;
import Service.Impl.FilmServiceImpl;
import Util.ActionsUtil;

@WebServlet(name = "FilmServlet" , urlPatterns = {"/FilmServlet"})
public class FilmServletImpl extends HttpServlet {

    private FilmServiceImpl filmServiceImpl;

    @Override
    public void init() throws ServletException {
        super.init();
        filmServiceImpl = new FilmServiceImpl();
    }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = ActionsUtil.getAction(request);
            if (action == null) {
                response.getWriter().println("Action is null");
                return;
            }
            switch (Actions.valueOf(action)) {
                case getFilm:
                    filmServiceImpl.getFilm(request, response);
                    break;
                case loadFilm:
                    filmServiceImpl.loadFilm(request,response);
                    break;
                case twoDaysFilms:
                    filmServiceImpl.getTwoDaysFilms(response);
                    break;
                case scheduler:
                    filmServiceImpl.scheduleFilmLoading();
                    response.getWriter().println("Scheduler loading films");
                    break;
                default:
                    ActionsUtil.filmNotFound(response);
            }
    }

    @Override
    public void destroy() {
        super.destroy();
        filmServiceImpl = null;
        }
}
