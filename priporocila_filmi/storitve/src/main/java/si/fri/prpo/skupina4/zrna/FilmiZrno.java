package si.fri.prpo.skupina4.zrna;

import si.fri.prpo.skupina4.Film;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class FilmiZrno {

    // omogoča logiranje
    private Logger log = Logger.getLogger(FilmiZrno.class.getName());
    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + FilmiZrno.class.getSimpleName());
        // inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + FilmiZrno.class.getSimpleName());
        // zapiranje virov
    }

    @PersistenceContext(unitName = "priporocila-jpa")
    private EntityManager em;

    @Transactional
    public List<Film> getFilmi() {
        Query query = em.createNamedQuery("Film.getAll", Film.class);
        return query.getResultList();
    }

    @Transactional
    public Film getFilmById(Integer id) {
        try {
            Query query = em.createNamedQuery("Film.getFilmById", Film.class);
            query.setParameter("id", id);
            return (Film) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void dodajFilm(Film film) {
        if (film != null) {
            try{
                em.persist(film);
            } catch (IllegalArgumentException | TransactionRequiredException e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional
    public void posodobiFilm(Film film) {
        try{
            em.merge(film);
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void odstraniFilm(Film film) {
        try{
            em.remove(em.merge(film));
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            e.printStackTrace();
        }
    }
}
