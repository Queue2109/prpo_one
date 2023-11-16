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
public class IgralciZrno {

    private Logger log = Logger.getLogger(IgralciZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + IgralciZrno.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + IgralciZrno.class.getSimpleName());
    }

    @PersistenceContext(unitName = "priporocila-jpa")
    private EntityManager em;

    @Transactional
    public List<Igralec> getIgralci() {
        Query query = em.createNamedQuery("Igralec.getAll", Igralec.class);
        return query.getResultList();
    }

    @Transactional
    public Igralec getIgralecById(int id) {
        try {
            Query query = em.createNamedQuery("Igralec.getIgralecById", Igralec.class);
            query.setParameter("id", id);
            return (Igralec) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void dodajIgralca(Igralec igralec) {
        if (igralec != null) {
            try{
                em.persist(igralec);
            } catch (IllegalArgumentException | TransactionRequiredException e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional
    public void posodobiIgralca(Igralec igralec) {
        try{
            em.merge(igralec);
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void odstraniIgralca(Igralec igralec) {
        try{
            em.remove(em.merge(igralec));
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            e.printStackTrace();
        }
    }

}
