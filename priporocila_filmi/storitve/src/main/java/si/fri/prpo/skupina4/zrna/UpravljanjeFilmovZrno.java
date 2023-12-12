package si.fri.prpo.skupina4.zrna;

import si.fri.prpo.skupina4.*;
import si.fri.prpo.skupina4.dtos.IgralecDto;
import si.fri.prpo.skupina4.dtos.OcenaDto;
import si.fri.prpo.skupina4.dtos.UporabnikDto;
import si.fri.prpo.skupina4.dtos.ZanrDto;
import si.fri.prpo.skupina4.dtos.FilmDto;
import si.fri.prpo.skupina4.interceptorji.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * poslovna logika zrno
 */
@RequestScoped
public class UpravljanjeFilmovZrno {

    private Logger log = Logger.getLogger(UpravljanjeFilmovZrno.class.getName());
    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UpravljanjeFilmovZrno.class.getSimpleName());
        // inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UpravljanjeFilmovZrno.class.getSimpleName());
        // zapiranje virov
    }

    @Inject
    private FilmiZrno filmiZrno;

    @Inject
    private ZanrZrno zanrZrno;

    @Inject
    private IgralciZrno igralciZrno;

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Inject
    private OceneZrno oceneZrno;

    @Interceptors(ValidirajUporabnikDtoInterceptor.class)
    @Transactional
    public Uporabnik ustvariUporabnika(UporabnikDto uporabnikDto) {

        if (uporabnikDto.getUporabnisko_ime() == null || uporabnikDto.getGeslo() == null || uporabnikDto.getEmail() == null) {
            log.warning("Napaka pri ustvarjanju uporabnika - obezni parametri so uporabniško ime, geslo in email!");
            return null;
        }

        Uporabnik uporabnik = new Uporabnik();
        uporabnik.setUporabnisko_ime(uporabnikDto.getUporabnisko_ime());
        uporabnik.setGeslo(uporabnikDto.getGeslo());
        uporabnik.setStarost(uporabnikDto.getStarost());
        uporabnik.setSpol(uporabnikDto.getSpol());
        uporabnik.setEmail(uporabnikDto.getEmail());
        uporabnik.setZanr_preference(uporabnikDto.getZanr_preference());
//        to je mela asistentka v kodi - should we implement it?
//        uporabnik.setUstvarjen(Instant.now());
        uporabnikiZrno.dodajUporabnika(uporabnik);

//        also, ona je mela return uporabnikiZrno.dodajUporabnika(uporabnik) ampak se ne sklada z return tipom
        return uporabnik;
    }

    @Interceptors(ValidirajZanrDtoInterceptor.class)
    @Transactional
    public Zanr ustvariZanr(ZanrDto zanrDto) {
        Zanr zanr = new Zanr();
        zanr.setNaziv(zanrDto.getNaziv());
        zanrZrno.dodajZanr(zanr);

        return zanr;
    }

    @Interceptors(ValidirajIgralecDtoInterceptor.class)
    @Transactional
    public Igralec ustvariIgralca(IgralecDto igralecDto) {
        Jsonb jsonb = JsonbBuilder.create();

        Igralec igralec = new Igralec();
        igralec.setIme(igralecDto.getIme());
        igralec.setPriimek(igralecDto.getPriimek());
        igralec.setFilmi(jsonb.fromJson(igralecDto.getFilmi(), new ArrayList<Film>(){}.getClass().getGenericSuperclass()));
        igralciZrno.dodajIgralca(igralec);
        return igralec;
    }

    @Interceptors(ValidirajOcenaDtoInterceptor.class)
    @Transactional
    public Ocena ustvariOceno(OcenaDto ocenaDto) {Ocena ocena = new Ocena();
        ocena.setOcena(ocenaDto.getOcena());
        ocena.setKomentar(ocenaDto.getKomentar());
        ocena.setCas_objave(ocenaDto.getCas_objave());
        ocena.setUporabnik(ocenaDto.getUporabnik());
        ocena.setFilm(ocenaDto.getFilm());
        oceneZrno.dodajOceno(ocena);
        return ocena;
    }

    Jsonb jsonb = JsonbBuilder.create();
    @Interceptors(ValidirajFilmDtoInterceptor.class)
    @Transactional
    public Film ustvariFilm(FilmDto filmDto) {
        Film film = new Film();
        film.setNaslov(filmDto.getNaslov());
        film.setOpis(filmDto.getOpis());
        film.setLeto_izzida(filmDto.getLeto_izzida());
        film.setZanr(filmDto.getZanr());
        film.setOcena(0);
        film.setZasedba(jsonb.fromJson(filmDto.getZasedba(), new ArrayList<Igralec>(){}.getClass().getGenericSuperclass()));
        // film.setOcene(filmDto.getOcene());
        filmiZrno.dodajFilm(film);
        return film;
    }

    public void posodobiOcenoFilma (OcenaDto ocena){
        Ocena o = oceneZrno.getOcenaById(ocena.getOcena_id());
        o.setCas_objave(ocena.getCas_objave());

        String komentar = ocena.getKomentar();
        if(komentar != null && komentar.length() > 0) {
            o.setKomentar(komentar);
        }
        if(validirajOceno(ocena)){
            o.setOcena(ocena.getOcena());
        }

        oceneZrno.posodobiOceno(o);
    }

    public void posodobiUporabnika (UporabnikDto uporabnik){
        Uporabnik u = uporabnikiZrno.getUporabnikById(uporabnik.getUporabnik_id());
        String uIme = uporabnik.getUporabnisko_ime();
        if(uIme != null && !uIme.isEmpty()) {
            u.setUporabnisko_ime(uIme);
        }
        String geslo = uporabnik.getGeslo();
        if(geslo != null && !geslo.isEmpty()) {
            u.setGeslo(geslo);
        }
        String email = uporabnik.getEmail();
        if(email != null && !email.isEmpty()) {
            u.setEmail(email);
        }
        Character spol = uporabnik.getSpol();
        if(spol != null) {
            u.setSpol(spol);
        }
        Integer starost = uporabnik.getStarost();
        if(starost != null) {
            u.setStarost(starost);
        }
        List<Zanr> zanr = uporabnik.getZanr_preference();
        if(zanr != null && !zanr.isEmpty()) {
            u.setZanr_preference(zanr);
        }
        uporabnikiZrno.posodobiUporabnika(u);
    }

    public void posodobiIgralca(IgralecDto igralec) {
        Igralec i = igralciZrno.getIgralecById(igralec.getIgralec_id());
        String ime = igralec.getIme();
        if(ime != null && !ime.isEmpty()) {
            i.setIme(ime);
        }
        String priimek = igralec.getPriimek();
        if(priimek != null && !priimek.isEmpty()) {
            i.setPriimek(priimek);
        }
        String filmi = igralec.getFilmi();
        if(filmi != null && !filmi.isEmpty()) {
            i.setFilmi(jsonb.fromJson(filmi, new ArrayList<Film>(){}.getClass().getGenericSuperclass()));
        }
        igralciZrno.posodobiIgralca(i);
    }


    private Boolean validirajOceno(OcenaDto oDTO){
        Integer ocena = oDTO.getOcena();
        return ocena != null && (ocena < 0 || ocena > 10 )&&  ocena % 1 != 0;
    }

    public List<FilmDto> mapFilmToDTO(List<Film> seznam){
        List<FilmDto> result = new ArrayList<>();
        for (Film el : seznam) {
            FilmDto film = new FilmDto();
            film.setNaslov(el.getNaslov());
            film.setOpis(el.getOpis());
            film.setLeto_izzida(el.getLeto_izzida());
            film.setZanr(el.getZanr());
            film.setZasedba(el.getZasedba());
            film.setOcene(new ArrayList<>(el.getOcena()));
            result.add(film);
        }
        return result;
    }
}
