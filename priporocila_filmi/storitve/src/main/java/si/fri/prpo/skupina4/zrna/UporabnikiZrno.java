package si.fri.prpo.skupina4.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.skupina4.Film;
import si.fri.prpo.skupina4.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Serializable;
import java.util.UUID;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@SessionScoped
public class UporabnikiZrno implements Serializable {

    // omogoča logiranje
    private Logger log = Logger.getLogger(UporabnikiZrno.class.getName());
    @PostConstruct
    private void init() {
        UUID uuid = UUID.randomUUID();
        log.info("Inicializacija zrna " + UporabnikiZrno.class.getSimpleName()
                + ". request scoped - uuid: " + uuid);
        // inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UporabnikiZrno.class.getSimpleName());
        // zapiranje virov
    }

    @PersistenceContext(unitName = "priporocila-jpa")
    private EntityManager em;

    @Transactional
    public List<Uporabnik> getUporabniki() {
        Query query = em.createNamedQuery("Uporabnik.getAll", Uporabnik.class);
        return query.getResultList();
    }

    @Transactional
    public Uporabnik getUporabnikById(Integer id) {
        try {
            Query query = em.createNamedQuery("Uporabnik.getUporabnikById", Uporabnik.class);
            query.setParameter("id", id);
            return (Uporabnik) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void dodajUporabnika(Uporabnik uporabnik) {
        if (uporabnik != null) {
            try{
                em.persist(uporabnik);
            } catch (IllegalArgumentException | TransactionRequiredException e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional
    public void posodobiUporabnika(Uporabnik uporabnik) {
        try{
            em.merge(uporabnik);
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void odstraniUporabnika(Uporabnik uporabnik) {
        try{
            em.remove(em.merge(uporabnik));
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            e.printStackTrace();
        }
    }

    public List<Uporabnik> pridobiUporabnike() {
        List<Uporabnik> uporabniki = em.createNamedQuery(("Uporabnik.getAll"), Uporabnik.class).getResultList();
        return uporabniki;
    }


    public List<Uporabnik> pridobiUporabnike(QueryParameters query) {
        return JPAUtils.queryEntities(em, Uporabnik.class, query);
    }

    public Long pridobiUporabnikeCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Uporabnik.class, query);
    }
}
