package si.fri.prpo.skupina4.dtos;

import si.fri.prpo.skupina4.Igralec;
import si.fri.prpo.skupina4.Ocena;
import si.fri.prpo.skupina4.Zanr;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.Serializable;
import java.util.List;

public class FilmDto implements Serializable{
    Jsonb jsonb = JsonbBuilder.create();
    private Integer film_id;

    private String naslov;

    private String opis;

    private Integer leto_izzida;

    private Zanr zanr;
    private Double povprecna_ocena;

    private List<Igralec> zasedba;

    private List<Integer> ocene;

    public Integer getFilm_id() {
        return film_id;
    }

    public void setFilm_id(Integer film_id) {
        this.film_id = film_id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Integer getLeto_izzida() {
        return leto_izzida;
    }

    public void setLeto_izzida(Integer leto_izzida) {
        this.leto_izzida = leto_izzida;
    }

    public Zanr getZanr() {
        return zanr;
    }

    public void setZanr(Zanr zanr) {
        this.zanr = zanr;
    }

    public Double getPovprecna_ocena() {
        int sum = 0;
        int n = 0;
        for (int o: this.ocene ){
            sum += o;//.getOcena();
            n++;
        }
        povprecna_ocena = n > 0 ? sum/n : 0.;
        return povprecna_ocena;
    }

    public void setPovprecna_ocena(Double povprecna_ocena) {
        this.povprecna_ocena = povprecna_ocena;
    }

    public String getZasedba() {
        return jsonb.toJson(zasedba);
    }

    public void setZasedba(List<Igralec> zasedba) {
        this.zasedba = zasedba;
    }

    public List<Integer> getOcene() {
        return ocene;
    }

    public void setOcene(List<Integer> ocene) {
        this.ocene = ocene;
    }
}
