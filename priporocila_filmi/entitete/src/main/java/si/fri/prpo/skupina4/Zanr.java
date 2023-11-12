package si.fri.prpo.skupina4;

import javax.persistence.*;

@Entity(name = "zanr")
@NamedQueries(value = {
        @NamedQuery(name = "Zanr.getAll", query = "SELECT z FROM  zanr z"),
        // pridobi vsa imena žanrov
        @NamedQuery(name = "Zanr.getAllNames", query = "SELECT z.naziv FROM zanr z "),
        // uredi imena žanrov po abecednem vrstnem redu
        @NamedQuery(name = "Zanr.orderByName", query = "SELECT z FROM zanr z ORDER BY z.naziv"),
})
public class Zanr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer zanr_id;

    private String naziv;


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

    @Override
    public String toString() {
        return "Zanr{" +
                "zanr_id=" + zanr_id +
                ", naziv='" + naziv + '\'' +
                '}';
    }


}
