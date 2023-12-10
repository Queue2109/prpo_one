package si.fri.prpo.skupina4;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "film_igralec")
public class FilmIgralec implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer filmIgralecId;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    @ManyToOne
    @JoinColumn(name = "igralec_id")
    private Igralec igralec;

    public Integer getFilmIgralecId() {
        return filmIgralecId;
    }

    public void setFilmIgralecId(Integer filmIgralecId) {
        this.filmIgralecId = filmIgralecId;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Igralec getIgralec() {
        return igralec;
    }

    public void setIgralec(Igralec igralec) {
        this.igralec = igralec;
    }

    @Override
    public String toString() {
        return "FilmIgralec{" +
                "filmIgralecId=" + filmIgralecId +
                ", film=" + film +
                ", igralec=" + igralec +
                '}';
    }


}
