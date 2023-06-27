package scheduler;

import daos.Impl.Top250DaoImpl;
import entities.Top250;
import load.Top250Loader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class Top250Scheduler implements Runnable {

    @Override
    public void run() {
        try {
            List<Top250> top250List = Top250Loader.loadTop();

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

        } catch (IOException | SQLException | ClassNotFoundException e) {
            System.out.println("Error running Top250 scheduler: " + e.getMessage());
        }
    }
}