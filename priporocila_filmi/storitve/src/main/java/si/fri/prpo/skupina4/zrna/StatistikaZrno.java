package si.fri.prpo.skupina4.zrna;

import si.fri.prpo.skupina4.dtos.FilmDto;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class StatistikaZrno {
    @PersistenceContext(unitName = "priporocila-jpa")
    private EntityManager em;
    private Logger log = Logger.getLogger(StatistikaZrno.class.getName());

    @Inject UpravljanjeFilmovZrno upravljanjeFilmovZrno;

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + StatistikaZrno.class.getSimpleName());
        // inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + StatistikaZrno.class.getSimpleName());
        // zapiranje virov
    }

    public List<FilmDto> topXFilmov(int limit){
        List<FilmDto> result = new ArrayList<>();

        Query q = em.createQuery("SELECT f FROM film f ORDER BY f.ocena DESC");
        q.setMaxResults(limit);

            result = upravljanjeFilmovZrno.mapFilmToDTO(q.getResultList());

        return result;
    }

    public FilmDto vrniPovpOcenoFilma(FilmDto film){
        Query q = em.createQuery("SELECT AVG(f.ocena) FROM film f WHERE f.film_id = :id AND f.ocena IS NOT NULL", Double.class);
        q.setParameter("id", film.getFilm_id());
        Double povp = null;
        try {
            povp = (Double) q.getSingleResult();
        }catch (EntityNotFoundException e){
            log.severe("ni ocene za film. " + e);
        }finally {
            film.setPovprecna_ocena(povp);
            return film;
        }
    }


}
