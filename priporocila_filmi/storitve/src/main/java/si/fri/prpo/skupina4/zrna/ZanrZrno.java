package si.fri.prpo.skupina4.zrna;

import si.fri.prpo.skupina4.Film;
import si.fri.prpo.skupina4.Uporabnik;
import si.fri.prpo.skupina4.Zanr;
import si.fri.prpo.skupina4.dtos.FilmDto;
import si.fri.prpo.skupina4.dtos.ZanrDto;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ZanrZrno {

    // omogoča logiranje
    private Logger log = Logger.getLogger(ZanrZrno.class.getName());
    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + ZanrZrno.class.getSimpleName());
        // inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + ZanrZrno.class.getSimpleName());
        // zapiranje virov
    }

    @PersistenceContext(unitName = "priporocila-jpa")
    private EntityManager em;

    @Transactional
    public List<Zanr> getZanri() {
        Query query = em.createNamedQuery("Zanr.getAll", Zanr.class);
        return query.getResultList();
    }

    @Transactional
    public Zanr getZanrById(Integer id) {
        try {
            Query query = em.createNamedQuery("Zanr.getZanrById", Uporabnik.class);
            query.setParameter("id", id);
            return (Zanr) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void dodajZanr(Zanr zanr) {
        if (zanr != null) {
            try{
                em.persist(zanr);
            } catch (IllegalArgumentException | TransactionRequiredException e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional
    public void posodobiZanr(Zanr zanr) {
        try{
            em.merge(zanr);
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void odstraniZanr(Zanr zanr) {
        try{
            em.remove(em.merge(zanr));
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            e.printStackTrace();
        }
    }

    public List<ZanrDto> mapZanrToDTO(List<Zanr> seznam){
        List<ZanrDto> result = new ArrayList<>();
        for (Zanr el : seznam) {
            ZanrDto zanr = new ZanrDto();
            zanr.setZanr_id(el.getZanr_id());
            zanr.setNaziv(el.getNaziv());
            result.add(zanr);
        }
        return result;
    }

}
