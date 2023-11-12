package si.fri.prpo.skupina4;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class ZanrZrno {
    @PersistenceContext(unitName = "priporocila-jpa")
    private EntityManager em;
    public List<Zanr> getZanri() {
        Query query = em.createNamedQuery("Zanr.getAll", Zanr.class);
        return query.getResultList();
    }

}
