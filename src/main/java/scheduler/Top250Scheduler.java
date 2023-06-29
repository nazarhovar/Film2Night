package scheduler;

import daos.Impl.Top250DaoImpl;
import entities.Top250;
import load.Top250Loader;
import util.PropertiesReader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Top250Scheduler {

    public void startScheduler() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleWithFixedDelay(this::runScheduler, 1, 1, TimeUnit.DAYS);
    }

    private void runScheduler() {
        try {
            Properties properties = PropertiesReader.loadProperties("/app/web/app.properties");
            boolean schedulerEnabled = Boolean.parseBoolean(properties.getProperty("top250.scheduler.enabled"));

            if (schedulerEnabled) {
                Top250Loader top250Loader = new Top250Loader();
                List<Top250> top250List = top250Loader.loadTop();

                Top250DaoImpl top250Dao = new Top250DaoImpl();
                List<Top250> existingFilms = top250Dao.getTop250();

                for (Top250 top250 : top250List) {
                    boolean filmExists = false;

                    for (Top250 existingFilm : existingFilms) {
                        if (existingFilm.getId() == top250.getId()) {
                            filmExists = true;
                            break;
                        }
                    }

                    if (filmExists) {
                        top250Dao.updateTop250(top250);
                        System.out.println("Film updated in Top250: " + top250.getName());
                    } else {
                        top250Dao.addTop250(Collections.singletonList(top250));
                        System.out.println("Film added to Top250: " + top250.getName());
                    }
                }
            } else {
                System.out.println("Top250Scheduler is disabled in the configuration.");
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            System.out.println("Error running Top250 scheduler: " + e.getMessage());
        }
    }
}