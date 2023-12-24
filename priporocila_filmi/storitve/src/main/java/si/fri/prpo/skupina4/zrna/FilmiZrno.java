package si.fri.prpo.skupina4.zrna;

import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.skupina4.Film;
import si.fri.prpo.skupina4.dtos.FilmDto;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;
import javax.ws.rs.BeanParam;
import java.util.List;
import java.util.logging.Logger;
import com.kumuluz.ee.rest.beans.QueryParameters;

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


    public List<Film> getFilmsByZanr(Integer zanrId){
        Query query = em.createNamedQuery("Film.getAllFilmsByGenre");
        query.setParameter("zanr", zanrId);
        return query.getResultList();
    }

    public List<Film> pridobiFilme() {
        List<Film> filmi = em.createNamedQuery(("Film.getAll"), Film.class).getResultList();
        return filmi;
    }

    public Film pridobiFilm(Integer id) {
        List<Film> filmi = this.getFilmi();
        for (Film f: filmi) {
            if(id.equals(f.getFilm_id())) {
                return f;
            }
        }
        return null;
    }


    public List<Film> pridobiFilme(QueryParameters query) {
        return JPAUtils.queryEntities(em, Film.class, query);
    }

    public Long pridobiFilmeCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Film.class, query);
    }
}
