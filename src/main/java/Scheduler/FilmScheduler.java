package Scheduler;

import Daos.Impl.FilmDaoImpl;
import Entities.Film;
import Load.FilmLoader;
import Util.PosterUtil;

import java.util.Random;

public class FilmScheduler implements Runnable {
    private final Random random = new Random();
    private final FilmDaoImpl filmDao;

    public FilmScheduler() {
        filmDao = new FilmDaoImpl();
    }

    @Override
    public void run() {
        int currentFilmId = getRandomFilmId();
        try {
            FilmLoader filmLoader = new FilmLoader();
            Film film = filmLoader.loadFilm(currentFilmId);

            filmDao.addFilm(film);
            //idea
//          String outputDirectory = "C:\\main\\iTechArt\\Posters";

            //docker
            String outputDirectory = "/app/Posters";
            PosterUtil.savePosterLocally(film, outputDirectory);
        } catch (Exception e) {
            System.out.println("Error loading film");
        }
    }

    private int getRandomFilmId() {
        return random.nextInt(50000) + 1;
    }
}