package si.fri.prpo.skupina4;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    @Inject
    private FilmiZrno filmiZrno;

    @Inject
    private IgralciZrno igralciZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Film> filmi = filmiZrno.getFilmi();
        List<Igralec> igralci = igralciZrno.getIgralci();

        // izpis filmov na spletno stran
        PrintWriter writer = resp.getWriter();

        writer.append("printing movies\n");
        for (Film film: filmi) {
            writer.append(film.toString());

        }

        writer.append("\nprinting actors\n");
        for (Igralec i: igralci)
            writer.append(i.toString() + "\n");
    }

}
