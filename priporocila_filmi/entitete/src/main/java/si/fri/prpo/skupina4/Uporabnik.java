package si.fri.prpo.skupina4;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "uporabnik")
@NamedQueries(value = {
        @NamedQuery(name = "Uporabnik.getAll", query = "SELECT u FROM uporabnik u"),
        // pridobi uporabnika z določenim id-jem
        @NamedQuery(name = "Uporabnik.getUporabnikById", query = "SELECT u FROM uporabnik u WHERE u.uporabnik_id = :id"),
        // pridobi uporabnika glede na njegovo uporabnisko ime
        @NamedQuery(name = "Uporabnik.getByUsername", query = "SELECT u FROM uporabnik u WHERE u.uporabnisko_ime = :uporabnisko_ime"),
        // pridobi uporabnike v določeni starostni skupini
        @NamedQuery(name = "Uporabnik.getByAgeRange", query = "SELECT u FROM uporabnik u WHERE u.starost BETWEEN :minStarost AND :maxStarost"),
        // pridobi imena žanrov, ki jih ima uporabnik najraje
        @NamedQuery(name = "Uporabnik.getGenrePreferences", query = "SELECT z.naziv FROM uporabnik u JOIN u.zanr_preference z WHERE u.uporabnik_id = :uporabnik_id")
})
public class Uporabnik implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uporabnik_id;

    private String uporabnisko_ime;

    private String geslo;

    private String email;

    private Character spol;

    private Integer starost;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "uporabnik_zanr", joinColumns = @JoinColumn(name = "uporabnik_id"),
            inverseJoinColumns = @JoinColumn(name = "zanr_id"))
    private List<Zanr> zanr_preference;


    @OneToMany(mappedBy = "uporabnik", cascade = CascadeType.ALL)
    private List<Ocena> ocene;

    public Integer getUporabnik_id() {
        return uporabnik_id;
    }

    public void setUporabnik_id(Integer uporabnik_id) {
        this.uporabnik_id = uporabnik_id;
    }

    public String getUporabnisko_ime() {
        return uporabnisko_ime;
    }

    public void setUporabnisko_ime(String uporabnisko_ime) {
        this.uporabnisko_ime = uporabnisko_ime;
    }

    public String getGeslo() {
        return geslo;
    }

    public void setGeslo(String geslo) {
        this.geslo = geslo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Character getSpol() {
        return spol;
    }

    public void setSpol(Character spol) {
        this.spol = spol;
    }

    public Integer getStarost() {
        return starost;
    }

    public void setStarost(Integer starost) {
        this.starost = starost;
    }

    public List<Zanr> getZanr_preference() {
        return zanr_preference;
    }

    public void setZanr_preference(List<Zanr> zanr_preference) {
        this.zanr_preference = zanr_preference;
    }

    public List<Ocena> getOcene() {
        return ocene;
    }

    public void setOcene(List<Ocena> ocene) {
        this.ocene = ocene;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Uporabnik{" + "uporabnik_id=").append(uporabnik_id)
        .append(", uporabnisko_ime='").append(uporabnisko_ime).append('\'')
        .append(", geslo='").append(geslo).append('\'')
        .append(", email='").append(email).append('\'')
        .append(", spol=").append(spol).append('\'')
        .append(", starost=").append(starost).append('\'')
        .append(", zanr_id=[");
        for (Zanr z : zanr_preference) {
            sb.append(z.getZanr_id()).append(", ");
        }
        sb.reverse().delete(0, 2).reverse().append("]}").append('\'');;

        return sb.toString();
    }


}