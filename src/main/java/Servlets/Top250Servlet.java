package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name = "TOP_250")
public class Top250Servlet extends HttpServlet {

    private static final String API_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/";
    private static final String API_KEY = "e6a7be3b-3fc1-4e73-a048-b4bb298621b8";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
