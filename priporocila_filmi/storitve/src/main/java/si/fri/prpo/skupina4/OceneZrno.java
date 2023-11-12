package si.fri.prpo.skupina4;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class OceneZrno {
    @PersistenceContext(unitName = "priporocila-jpa")
    private EntityManager em;

    public List<Ocena> getOcene() {

        Query query = em.createNamedQuery("Ocena.getAll", Ocena.class);
        return query.getResultList();
    }
}
