package si.fri.prpo.skupina4;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "ocena")
@NamedQuery(name = "Ocena.getAll", query = "SELECT o FROM  ocena o")
public class Ocena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ocena_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="uporabnik_id")
    private Uporabnik uporabnik;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    private int ocena;

    private String komentar;

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

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
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
