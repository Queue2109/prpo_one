package si.fri.prpo.skupina4;

import javax.persistence.*;
import java.util.List;

@Entity(name = "film")
@NamedQueries(value = {
        @NamedQuery(name = "Film.getAll", query = "SELECT f FROM film f")
})
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer film_id;

    // če se atribut imenuje enako na bazi, ni treba pisati @Column(name=...)
    private String naslov;

    private String opis;

    private Integer leto_izzida;

    @ManyToOne
    @JoinColumn(name = "zanr_id")
    private Zanr zanr;
    private Integer ocena;

    @ManyToMany
    @JoinTable(name = "film_igralec", joinColumns = @JoinColumn(name = "film_id"), inverseJoinColumns = @JoinColumn(name = "igralec_id"))
    private List<Igralec> zasedba;


    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Ocena> ocene;


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

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }

    public List<Igralec> getZasedba() {
        return zasedba;
    }

    public void setZasedba(List<Igralec> zasedba) {
        this.zasedba = zasedba;
    }

    public List<Ocena> getOcene() {
        return ocene;
    }

    public void setOcene(List<Ocena> ocene) {
        this.ocene = ocene;
    }
}
