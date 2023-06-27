package util;

import entities.Film;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class PosterUtil {

    public static void savePosterLocally(Film film, String outputDirectory) {
        try {
            File outputFile = new File(outputDirectory, film.getKinopoiskId() + ".jpg");

            InputStream inputStream = new URL(film.getPosterUrl()).openStream();

            FileOutputStream outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();

            System.out.println("Poster saved successfully: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error saving poster: " + e.getMessage());
        }
    }
}
