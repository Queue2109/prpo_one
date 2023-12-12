package si.fri.prpo.skupina4.zrna;

import si.fri.prpo.skupina4.*;
import si.fri.prpo.skupina4.dtos.IgralecDto;
import si.fri.prpo.skupina4.dtos.OcenaDto;
import si.fri.prpo.skupina4.dtos.UporabnikDto;
import si.fri.prpo.skupina4.dtos.ZanrDto;
import si.fri.prpo.skupina4.dtos.FilmDto;
import si.fri.prpo.skupina4.interceptorji.ValidirajUporabnikDtoInterceptor;

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

    @Transactional
    public Zanr ustvariZanr(ZanrDto zanrDto) {

        if(zanrDto.getNaziv() == null) {
            log.warning("zahtevan je naziv žanra!");
        }

        Zanr zanr = new Zanr();
        zanr.setNaziv(zanrDto.getNaziv());
        zanrZrno.dodajZanr(zanr);

        return zanr;
    }

    @Transactional
    public Igralec ustvariIgralca(IgralecDto igralecDto) {

        if(igralecDto.getIme() == null || igralecDto.getPriimek() == null) {
            log.warning("Zahtevana sta ime in priimek igralca!");
            return null;
        }
        Jsonb jsonb = JsonbBuilder.create();

        Igralec igralec = new Igralec();
        igralec.setIme(igralecDto.getIme());
        igralec.setPriimek(igralecDto.getPriimek());
        igralec.setFilmi(jsonb.fromJson(igralecDto.getFilmi(), new ArrayList<Film>(){}.getClass().getGenericSuperclass()));
        igralciZrno.dodajIgralca(igralec);
        return igralec;
    }

    @Transactional
    public Ocena ustvariOceno(OcenaDto ocenaDto) {

        if(ocenaDto.getFilm() == null || ocenaDto.getUporabnik() == null) {
            log.warning("Ocena mora imeti podan film ter uporabnika!");
            return null;
        }

        Ocena ocena = new Ocena();
        ocena.setOcena(ocenaDto.getOcena());
        ocena.setKomentar(ocenaDto.getKomentar());
        ocena.setCas_objave(ocenaDto.getCas_objave());
        ocena.setUporabnik(ocenaDto.getUporabnik());
        ocena.setFilm(ocenaDto.getFilm());
        oceneZrno.dodajOceno(ocena);
        return ocena;
    }

    Jsonb jsonb = JsonbBuilder.create();
    @Transactional
    public Film ustvariFilm(FilmDto filmDto) {

        if(filmDto.getNaslov() == null || filmDto.getZasedba() == null || filmDto.getLeto_izzida() == null || filmDto.getZanr() == null) {
            log.warning("Manjkajoci parametri!");
            return null;
        }

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
