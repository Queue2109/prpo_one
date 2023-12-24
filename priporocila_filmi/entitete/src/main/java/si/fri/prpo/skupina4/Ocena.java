package si.fri.prpo.skupina4;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "ocena")
@NamedQueries(value= {
        @NamedQuery(name="Ocena.getAll", query = "SELECT o FROM  ocena o"),
        // pridobi oceno z določenim id-jem
        @NamedQuery(name="Ocena.getOcenaById", query = "SELECT o FROM  ocena o WHERE o.ocena_id = :id"),
        // uredi po času oddaje - od najbolj sveže ocene do najstarejše
        @NamedQuery(name="Ocena.orderByDateTime", query = "SELECT o FROM  ocena o ORDER BY o.cas_objave"),
        // uredi po oceni - najboljša do najslabša
        @NamedQuery(name = "Ocena.orderByOcena", query = "SELECT o FROM ocena o ORDER BY o.ocena"),
        // izbrisi oceno
        @NamedQuery(name = "Ocena.delete", query = "DELETE FROM ocena o WHERE o.ocena_id = :ocena_id ")
})
public class Ocena implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ocena_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="uporabnik_id")
    private Uporabnik uporabnik;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    private Integer ocena;

    private String komentar;

    private Date cas_objave;

    public Integer getOcena_id() {
        return ocena_id;
    }

    public void setOcena_id(Integer ocena_id) {
        this.ocena_id = ocena_id;
    }

    public String getUporabnik() {
        return uporabnik != null ? uporabnik.toString() : null;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public String getFilm() {
        return film != null ? film.toString() : null;
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

    @Override
    public String toString() {
        this.setCas_objave(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append("Ocena{").append("ocena_id=").append(ocena_id).append('\'');;
        sb.append(", uporabnik=").append(uporabnik.toString()).append('\'');;
        sb.append(", film=").append(film.toString()).append('\'');;
        sb.append(", ocena=").append(ocena).append('\'');;
        sb.append(", komentar='").append(komentar).append('\'');
        sb.append(", cas_objave=").append(cas_objave).append('\'');;
        return sb.toString();
    }

}
