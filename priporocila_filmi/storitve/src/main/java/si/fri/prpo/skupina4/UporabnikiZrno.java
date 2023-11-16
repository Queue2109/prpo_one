package si.fri.prpo.skupina4;

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
public class UporabnikiZrno {

    // omogoča logiranje
    private Logger log = Logger.getLogger(UporabnikiZrno.class.getName());
    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UporabnikiZrno.class.getSimpleName());
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
    public Uporabnik getUporabnikById(int id) {
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
}
