package si.fri.prpo.skupina4;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "igralec")
@NamedQueries(value = {
        @NamedQuery(name = "Igralec.getAll", query = "SELECT i FROM igralec i"),
        // pridobi igralca z določenim id-jem
        @NamedQuery(name = "Igralec.getIgralecById", query = "SELECT i FROM igralec i WHERE i.igralec_id = :id"),
        // uredi vrstni red igralcev po imenu
        @NamedQuery(name = "Igralec.sortByName", query="SELECT i FROM igralec i ORDER BY i.ime"),
        // uredi vrstni red igralcev po priimku
        @NamedQuery(name = "Igralec.sortBySurname", query="SELECT i FROM igralec i ORDER BY i.priimek"),
        // pridobi vsa imena in priimke igralcev
        @NamedQuery(name = "Igralec.getFullNames", query="SELECT i.ime, i.priimek FROM igralec i")
})
public class Igralec implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer igralec_id;

    private String ime;

    private String priimek;

    @ManyToMany(mappedBy = "zasedba")
    @JsonbTransient
    private Set<Film> filmi = new HashSet<>();

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

    public Set<Film> getFilmi() {
        return filmi;
    }
    private String filmi() {
        return filmi.toString();
    }

    public String getFilmiJSON(){
        StringBuilder sb = new StringBuilder();
        if(filmi != null) {
            for (Film f : filmi) {
                sb.append(f.toString()).append(", ");
            }
        }
        return sb.toString();
    }

    public void setFilmi(Set<Film> filmi) {
        this.filmi = filmi;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(filmi != null) {
            for (Film f : filmi) {
                sb.append(f.getFilm_id()).append(", ");
            }
        }
        sb.reverse().replace(0,2,"").reverse().append("]");
        return "Igralec{" +
                "igralec_id=" + igralec_id +
                ", ime='" + ime + '\'' +
                ", priimek='" + priimek + '\'' +
                ", filmiId=[" + sb +
                '}' + '\'';
    }

}
