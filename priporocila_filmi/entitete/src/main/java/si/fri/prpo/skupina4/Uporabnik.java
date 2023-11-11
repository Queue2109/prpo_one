package si.fri.prpo.skupina4;

import javax.persistence.*;
import java.util.List;

@Entity(name = "uporabnik")
@NamedQueries(value = {
        @NamedQuery(name = "Uporabnik.getAll", query = "SELECT u FROM uporabnik u")
})
public class Uporabnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uporabnik_id;

    private String uporabnisko_ime;

    private String geslo;

    private String email;

    private Character spol;

    private Integer starost;

    @ManyToMany
    @JoinTable(name = "uporabnik_zanr", joinColumns = @JoinColumn(name = "uporabnik_id"),
    inverseJoinColumns = @JoinColumn(name = "zanr_id"))
    private List<Zanr> zanr_preference;


    @OneToMany(mappedBy = "uporabnik", cascade = CascadeType.ALL)
    private List<Ocena> ocene;
}
