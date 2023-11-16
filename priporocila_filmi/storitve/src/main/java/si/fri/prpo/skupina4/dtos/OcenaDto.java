package si.fri.prpo.skupina4.dtos;

import si.fri.prpo.skupina4.Film;
import si.fri.prpo.skupina4.Uporabnik;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Date;

public class OcenaDto {

    private Integer ocena_id;

    private Uporabnik uporabnik;

    private Film film;

    private Integer ocena;

    private String komentar;

    @Temporal(TemporalType.TIMESTAMP)
    private Date cas_objave;

    public Integer getOcena_id() {
        return ocena_id;
    }

    public void setOcena_id(Integer ocena_id) {
        this.ocena_id = ocena_id;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public Date getCas_objave() {
        return cas_objave;
    }

    public void setCas_objave(Date cas_objave) {
        this.cas_objave = cas_objave;
    }
}
