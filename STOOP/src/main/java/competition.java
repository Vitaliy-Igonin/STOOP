import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/competition")
public class competition extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //получаем объект для записи данных на страницу
        PrintWriter out = resp.getWriter();

        try {
            // получаем параметры запроса
            int points = Integer.parseInt(req.getParameter("points"));

            //создаем подключение к СУБД
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id, name, points from competition WHERE points >= " + points);

            while (rs.next()) {
                out.println(rs.getString("id") + " " + rs.getString("name") + " " + rs.getString("points"));
            }

        } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
            out.print(ex);
        }
        out.close();
    }
}
