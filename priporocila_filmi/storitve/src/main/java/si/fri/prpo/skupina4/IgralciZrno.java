package si.fri.prpo.skupina4;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class IgralciZrno {
    @PersistenceContext(unitName = "priporocila-jpa")
    private EntityManager em;

    public List<Igralec> getIgralci() {

        Query query = em.createNamedQuery("Igralec.getAll", Igralec.class);
        return query.getResultList();
    }
}
