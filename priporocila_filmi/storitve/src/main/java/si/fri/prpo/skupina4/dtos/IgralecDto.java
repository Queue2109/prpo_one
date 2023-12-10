package si.fri.prpo.skupina4.dtos;

import si.fri.prpo.skupina4.Film;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.List;

public class IgralecDto {

    private Integer igralec_id;

    private String ime;

    private String priimek;

    private List<Film> filmi;

    public Integer getIgralec_id() {
        return igralec_id;
    }

    public void setIgralec_id(Integer igralec_id) {
        this.igralec_id = igralec_id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public String getFilmi() {
        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(filmi);
        return result;
    }

    public void setFilmi(List<Film> filmi) {
        this.filmi = filmi;
    }
}
