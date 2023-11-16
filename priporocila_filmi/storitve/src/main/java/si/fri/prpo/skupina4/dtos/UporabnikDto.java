package si.fri.prpo.skupina4.dtos;

import si.fri.prpo.skupina4.Zanr;
import java.util.List;

public class UporabnikDto {

    private Integer uporabnik_id;

    private String uporabnisko_ime;

    private String geslo;

    private String email;

    private Character spol;

    private Integer starost;

    private List<Zanr> zanr_preference;

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
}
