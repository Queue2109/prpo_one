package si.fri.prpo.skupina4;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class FilmiZrno {

    @PersistenceContext(unitName = "priporocila-jpa")
    private EntityManager em;

    public List<Film> getFilmi() {

        Query query = em.createNamedQuery("Film.getAll", Film.class);
        return query.getResultList();
    }
}