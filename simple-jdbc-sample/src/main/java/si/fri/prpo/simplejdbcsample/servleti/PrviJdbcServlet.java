package si.fri.prpo.simplejdbcsample.servleti;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.prpo.simplejdbcsample.jdbc.BaseDao;
import si.fri.prpo.simplejdbcsample.jdbc.Entiteta;
import si.fri.prpo.simplejdbcsample.jdbc.Uporabnik;
import si.fri.prpo.simplejdbcsample.jdbc.UporabnikDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/servlet")
public class PrviJdbcServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        System.out.println("Moj prvi servlet.");

        //izpis konfiguracije mikrostoritev
        Optional<String> microserviceName = ConfigurationUtil.getInstance().get("kumuluzee.name");
        microserviceName.ifPresent(s -> writer.println("Izpis generiran v mikrostoritvi: " + s + "\n"));

        // dostop do podatkovne baze
        BaseDao uporabnikDao = UporabnikDaoImpl.getInstance();

        Uporabnik uporabnik = new Uporabnik("Miha", "Novak", "mihanovak");
        uporabnik.setId(2);
        Entiteta entiteta = (Entiteta) uporabnik;

        // dodajanje uporabnika
        writer.append("Dodajam prvega uporabnika:\n" + uporabnik.toString());
        uporabnikDao.vstavi(entiteta);
        writer.append("\n\n");


        // izpis vseh uporabnikov
        writer.append("Seznam obstojecih uporabnikov:\n");
        List<Entiteta> uporabniki = uporabnikDao.vrniVse();
        uporabniki.stream().forEach(u -> writer.append(u.toString() + "\n"));


        uporabnik.setIme("Mihec");
        uporabnik.setPriimek("Novak");
        uporabnik.setUporabniskoIme("mihecnovak");
        entiteta = (Entiteta) uporabnik;
        uporabnikDao.posodobi(entiteta);

        writer.append("\nSeznam obstojecih uporabnikov po posodobitvi:\n");
        uporabniki = uporabnikDao.vrniVse();
        uporabniki.stream().forEach(u -> writer.append(u.toString() + "\n"));


        //  brisanje uporabnika
        uporabnikDao.odstrani(2);
        writer.append("\nSeznam obstojecih uporabnikov po izbrisu:\n");
        uporabniki = uporabnikDao.vrniVse();
        uporabniki.stream().forEach(u -> writer.append(u.toString() + "\n"));


    }
}
