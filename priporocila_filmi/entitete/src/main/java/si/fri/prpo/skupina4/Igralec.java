package si.fri.prpo.skupina4;

import javax.persistence.*;
import java.util.List;

@Entity(name = "igralec")
@NamedQueries(value = {
        @NamedQuery(name = "Igralec.getAll", query = "SELECT i FROM igralec i")
        // uredi vrstni red igralcev po imenu
        @NamedQuery(name = "Igralec.sortByName", query="SELECT i FROM igralec i ORDER BY i.ime")
        // uredi vrstni red igralcev po priimku
        @NamedQuery(name = "Igralec.sortBySurname", query="SELECT i FROM igralec i ORDER BY i.priimek")
        // pridobi vsa imena in priimke igralcev
        @NamedQuery(name = "Igralec.getFullNames", query="SELECT i.ime, i.priimek FROM igralec i")
})
public class Igralec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer igralec_id;

    private String ime;

    private String priimek;

    @ManyToMany(mappedBy = "zasedba")
    private List<Film> filmi;

    public Integer getIgralec_id() {
        return igralec_id;
    }

    public void setIgralec_id(Integer igralec_id) {
        this.igralec_id = igralec_id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public List<Film> getFilmi() {
        return filmi;
    }

    public void setFilmi(List<Film> filmi) {
        this.filmi = filmi;
    }
}
