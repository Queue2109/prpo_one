package si.fri.prpo.skupina4;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;;
import java.io.Serializable;
import java.util.List;

@Entity(name = "film")
@NamedQueries(value = {
        @NamedQuery(name = "Film.getAll", query = "SELECT f FROM film f"),
        // pridobi film z določenim id-jem
        @NamedQuery(name="Film.getFilmById", query = "SELECT f FROM film f WHERE f.film_id = :id"),
        // pridobi vse naslove filmov
        @NamedQuery(name = "Film.getAllTitles", query = "SELECT f.naslov FROM film f"),
        // pridobi imena in priimke vseh igralcev
        @NamedQuery(name = "Film.getActors", query = "SELECT i.ime, i.priimek FROM film f JOIN f.zasedba i"),
        // pridobi vse filme, ki so določenega žanra
        @NamedQuery(name = "Film.getAllFilmsByGenre", query = "SELECT f FROM film f WHERE f.zanr.zanr_id = :zanr"),
        // pridobi filme, ki so izšli po določenem letu
        @NamedQuery(name = "Film.getFilmsReleasedAfterYear", query = "SELECT f FROM film f WHERE f.leto_izzida > :leto"),
        // pridobi filme, ki imajo oceno višjo oz. enako od določene ocene
        @NamedQuery(name = "Film.getFilmsWithMinRating", query = "SELECT f FROM film f WHERE f.ocena >= :minOcena"),
        // pridobi filme določenega žanra
        @NamedQuery(name = "Film.getFilmsByGenreName", query = "SELECT f FROM film f WHERE f.zanr.naziv = :zanrNaziv ")
})
public class Film implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer film_id;

    // če se atribut imenuje enako na bazi, ni treba pisati @Column(name=...)
    private String naslov;

    private String opis;

    private Integer leto_izzida;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "zanr_id")
    private Zanr zanr;
    private Integer ocena;

    @ManyToMany(cascade = CascadeType.ALL)
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

    @Override
    public String toString() {
        StringBuilder film = new StringBuilder();
        film.append("Film{");
        film.append("film_id=").append(film_id);
        film.append(", naslov='").append(naslov).append('\'');
        if(opis != null && !opis.isEmpty()) {
            film.append(", opis='").append(opis).append('\'');
        }
        if(leto_izzida != null) {
            film.append(", leto_izzida=").append(leto_izzida).append('\'');;
        }
        if(zanr != null) {
            film.append(", zanr=").append(zanr.toString());
        }
        if(ocena != null) {
            film.append(", ocena=").append(ocena).append('\'');;
        }
        if(zasedba != null) {
            film.append(", zasedba=[");
            for (Igralec i: zasedba) {
                film.append(i.toString()).append(", ");
            }
            film.reverse().replace(0,2,"").reverse().append("]").append('\'');;
        }
        if(ocene != null) {
            film.append(", ocene_id=[");
            for (Ocena o: ocene) {
                film.append(o.getOcena_id()).append(", ");
            }
            film.reverse().replace(0,2,"").reverse().append("]");
        }
        film.append('}').append('\'');;
        return film.toString();
    }

}
