package si.fri.prpo.skupina4.odjemalci;

import si.fri.prpo.skupina4.dtos.LoveCalcApiOdjemalecDto;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

@ApplicationScoped
public class LoveCalcApiOdjemalec {

    private Logger log = Logger.getLogger(LoveCalcApiOdjemalec.class.getName());

    private Client httpClient;

    private String baseUrl;

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + LoveCalcApiOdjemalec.class.getSimpleName());

        httpClient = ClientBuilder.newClient();
        baseUrl = "https://love-calculator.p.rapidapi.com";
    }

    public LoveCalcApiOdjemalecDto getLoveCalc(String ime1, String ime2) {

        JsonObject response;

        try{
            response = httpClient
                    .target(baseUrl + "//etPercentage")
                    .queryParam("fname", ime1)
                    .queryParam("sname", ime2)
                    .request(MediaType.APPLICATION_JSON)
                    .header("X-Rapidapi-Key", "1b9cf2e86cmshdc2c4e1d93663a5p1575fejsn301bd55b28c6")
                    .header("X-Rapidapi-Host", "love-calculator.p.rapidapi.com")
                    .get()
                    .readEntity(JsonObject.class);
        } catch (WebApplicationException | ProcessingException e) {
            log.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }

        log.info("response: " + response);

        LoveCalcApiOdjemalecDto odgovor = new LoveCalcApiOdjemalecDto();

        if (response != null) {
            odgovor.setIme1(response.getString("fname"));
            odgovor.setIme2(response.getString("sname"));
            odgovor.setProcent(response.getJsonNumber("percentage").doubleValue());
            odgovor.setKomentar(response.getString("result"));
        }

        return odgovor;
    }
}
