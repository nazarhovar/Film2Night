package Service.Impl;

import Daos.Impl.FilmDaoImpl;
import Entities.Film;
import Load.FilmLoader;
import Scheduler.FilmScheduler;
import Service.FilmService;
import Util.ActionsUtil;
import Util.PosterUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FilmServiceImpl implements FilmService {
    private final FilmDaoImpl filmDao;
    FilmLoader filmLoader = new FilmLoader();
    private ScheduledExecutorService executorService;
    //idea
//    String outputDirectory = "C:\\main\\iTechArt\\Posters";
    //docker
    String outputDirectory = "/app/Posters";

    public FilmServiceImpl() {
        filmDao = new FilmDaoImpl();
    }

    public void getFilm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Film film = filmDao.getFilmById(ActionsUtil.getFilmId(request));
        if (film == null)
            response.getWriter().println("Film not found");
        else {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(film);
        }
    }

    public void loadFilm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Film film = filmLoader.loadFilm(ActionsUtil.getFilmId(request));

            filmDao.addFilm(film);

            PosterUtil.savePosterLocally(film, outputDirectory);

            response.setCharacterEncoding("UTF-8");
            response.getWriter().println("Film loaded successfully: " + film.getNameOriginal());
        } catch (Exception e) {
            response.getWriter().println("Error loading film");
        }
    }

    public void getTwoDaysFilms(HttpServletResponse response) throws IOException {
        List<Integer> twoDaysFilms = filmDao.getTwoDaysFilms();
        response.getWriter().println(twoDaysFilms);
    }

    public void scheduleFilmLoading() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new FilmScheduler(), 0, 10, TimeUnit.SECONDS);
    }
}
