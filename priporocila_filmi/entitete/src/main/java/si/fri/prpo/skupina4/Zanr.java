package si.fri.prpo.skupina4;

import javax.persistence.*;
import java.util.List;

@Entity(name = "zanr")
@NamedQueries(value = {
        @NamedQuery(name = "Zanr.getAll", query = "SELECT z FROM  zanr z")
})
public class Zanr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer zanr_id;

    private String naziv;

    @OneToMany(mappedBy = "zanr", cascade = CascadeType.ALL)
    private List<Film> filmi;


    public Integer getZanr_id() {
        return zanr_id;
    }

    public void setZanr_id(Integer zanr_id) {
        this.zanr_id = zanr_id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public List<Film> getFilmi() {
        return filmi;
    }

    public void setFilmi(List<Film> filmi) {
        this.filmi = filmi;
    }
}
