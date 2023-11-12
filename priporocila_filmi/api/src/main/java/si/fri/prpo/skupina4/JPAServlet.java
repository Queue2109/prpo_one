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

    @Inject
    private OceneZrno oceneZrno;
    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Inject
    private ZanrZrno zanriZrno;



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Film> filmi = filmiZrno.getFilmi();
        List<Igralec> igralci = igralciZrno.getIgralci();
        List<Ocena> ocene = oceneZrno.getOcene();
        List<Uporabnik> uporabniki = uporabnikiZrno.getUporabniki();
        List<Zanr> zanri = zanriZrno.getZanri();

        // izpis filmov na spletno stran
        PrintWriter writer = resp.getWriter();

        writer.append("printing movies\n");
        for (Film film: filmi) {
            writer.append(film.toString() + "\n");

        }

        writer.append("\nprinting actors\n");
        for (Igralec i: igralci)
            writer.append(i.toString() + "\n");

        writer.append("\nprinting ocene\n");
        for (Ocena o: ocene)
            writer.append(o.toString() + "\n");

        writer.append("\nprinting uporabniki\n");
        for (Uporabnik u: uporabniki)
            writer.append(u.toString() + "\n");

        writer.append("\nprinting zanri\n");
        for (Zanr z: zanri)
            writer.append(z.toString() + "\n");
    }

}
