package si.fri.prpo.skupina4;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class UporabnikiZrno {
    @PersistenceContext(unitName = "priporocila-jpa")
    private EntityManager em;

    public List<Uporabnik> getUporabniki() {

        Query query = em.createNamedQuery("Uporabnik.getAll", Uporabnik.class);
        return query.getResultList();
    }
}
