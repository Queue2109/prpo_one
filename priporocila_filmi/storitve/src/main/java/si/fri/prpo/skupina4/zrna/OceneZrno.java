package si.fri.prpo.skupina4.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.skupina4.Film;
import si.fri.prpo.skupina4.Ocena;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RequestScoped
public class OceneZrno {

    private Logger log = Logger.getLogger(OceneZrno.class.getName());

    @PostConstruct
    private void init() {
        UUID uuid = UUID.randomUUID();
        log.info("Inicializacija zrna " + OceneZrno.class.getSimpleName()
        + ". request scoped - uuid: " + uuid);
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + OceneZrno.class.getSimpleName());
    }
    @PersistenceContext(unitName = "priporocila-jpa")
    private EntityManager em;

    @Transactional
    public List<Ocena> getOcene() {
        Query query = em.createNamedQuery("Ocena.getAll", Ocena.class);
        return query.getResultList();
    }

    @Transactional
    public Ocena getOcenaById(Integer id) {
        try {
            Query query = em.createNamedQuery("Ocena.getOcenaById", Ocena.class);
            query.setParameter("id", id);
            return (Ocena) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public List<Ocena> getOcenaByFilmId(Integer film_id) {
        try {
            Query query = em.createNamedQuery("Ocena.getOceneByFilmId", Ocena.class);
            query.setParameter("film_id", film_id);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void dodajOceno(Ocena ocena) {
        try{
            em.persist(ocena);
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            e.printStackTrace();
        }

    }

    @Transactional
    public void posodobiOceno(Ocena ocena) {
        try{
            em.merge(ocena);
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public Boolean odstraniOceno(Ocena ocena) {
        try{
            em.remove(em.merge(ocena));
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Ocena> pridobiOcene() {
        List<Ocena> ocene = em.createNamedQuery(("Ocena.getAll"), Ocena.class).getResultList();
        return ocene;
    }


    public List<Ocena> pridobiOcene(QueryParameters query) {
        return JPAUtils.queryEntities(em, Ocena.class, query);
    }

    public Long pridobiOceneCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Ocena.class, query);
    }
}
