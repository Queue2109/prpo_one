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
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        Uporabnik uporabnik = new Uporabnik();
        String uporabnisko_ime = uporabnikDto.getUporabnisko_ime();
        if(uporabnisko_ime != null && !uporabnisko_ime.isEmpty()) {
            uporabnik.setUporabnisko_ime(uporabnikDto.getUporabnisko_ime());
        }
        uporabnik.setGeslo(uporabnikDto.getGeslo());
        uporabnik.setEmail(uporabnikDto.getEmail());
        Integer starost = uporabnikDto.getStarost();
        if(starost != null) {
            uporabnik.setStarost(starost);
        }
        Character spol = uporabnikDto.getSpol();
        if(spol != null) {
            uporabnik.setSpol(spol);
        }
        List<Zanr> zanr_preference = uporabnikDto.getZanr_preference();
        if(zanr_preference != null && !zanr_preference.isEmpty()) {
            List<Zanr> zanr_preference1 = new ArrayList<>();
            for(Zanr zanr : uporabnikDto.getZanr_preference()) {
                zanr_preference1.add(zanrZrno.getZanrById(zanr.getZanr_id()));
            }
            uporabnik.setZanr_preference(zanr_preference1);
        }
        uporabnikiZrno.dodajUporabnika(uporabnik);
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
        Set<Film> filmi = new HashSet<>();
        Set<Film> vneseniFilmi = igralecDto.getFilmiList();
        for (Film film : vneseniFilmi) {
            Integer id = film.getFilm_id();
            if(id != null) {
                Film f = filmiZrno.getFilmById(id);
                if(f != null) {
                    filmi.add(f);
                    f.getZasedba().add(igralec);
                }
            }
        }
        log.info("Filmi: " + filmi);
        igralec.setFilmi(filmi);
        igralciZrno.dodajIgralca(igralec);
        return igralec;
    }

    @Interceptors(ValidirajOcenaDtoInterceptor.class)
    @Transactional
    public Ocena ustvariOceno(OcenaDto ocenaDto) {
        Ocena ocena = new Ocena();
//        ti pogoji se preverijo v interceptorju
        ocena.setOcena(ocenaDto.getOcena());
        Film film = filmiZrno.getFilmById(ocenaDto.getFilm().getFilm_id());
        ocena.setFilm(film);
        Uporabnik uporabnik = uporabnikiZrno.getUporabnikById(ocenaDto.getUporabnik().getUporabnik_id());
        ocena.setUporabnik(uporabnik);
        String komentar = ocenaDto.getKomentar();
        if(komentar != null && !komentar.isEmpty()) {
            ocena.setKomentar(komentar);
        }
        ocena.setCas_objave(new Date(System.currentTimeMillis()));
        ocenaDto.setCas_objave((Date) ocena.getCas_objave());

        oceneZrno.dodajOceno(ocena);
        filmiZrno.pridobiFilm(ocenaDto.getFilm().getFilm_id()).addOcena(ocena);


        return ocena;
    }

    Jsonb jsonb = JsonbBuilder.create();
    @Interceptors(ValidirajFilmDtoInterceptor.class)
    @Transactional
    public Film ustvariFilm(FilmDto filmDto) {
        Film film = new Film();
//        ti pogoji se preverijo v interceptorju
        film.setNaslov(filmDto.getNaslov());
        film.setLeto_izzida(filmDto.getLeto_izzida());
        if(filmDto.getZanr() != null) {
            film.setZanr(zanrZrno.getZanrById(filmDto.getZanr().getZanr_id()));
        }
        if(filmDto.getZasedba() != null) {
            Set<Igralec> igralci = new HashSet<>();
            for(Igralec igralec : film.getZasedba()) {
                igralci.add(igralciZrno.getIgralecById(igralec.getIgralec_id()));
            }
            film.setZasedba(igralci);
        }

        String opis = filmDto.getOpis();
        if(opis != null && !opis.isEmpty()) {
            film.setOpis(opis);
        }
        if(filmDto.getOcene() != null) {
            List<Ocena> ocene = new ArrayList<>();
            for(Ocena ocena : filmDto.getOcene()) {
                ocene.add(oceneZrno.getOcenaById(ocena.getOcena_id()));
            }
            film.setOcene(ocene);
            film.setOcena();
        }
        filmiZrno.dodajFilm(film);
        return film;
    }

    public void posodobiOcenoFilma (OcenaDto ocena){
        Ocena o = oceneZrno.getOcenaById(ocena.getOcena_id());
        o.setCas_objave( new Date(System.currentTimeMillis()));

        String komentar = ocena.getKomentar();
        if(komentar != null && komentar.isEmpty()) {
            o.setKomentar(komentar);
        }
        if(validirajOceno(ocena)){
            o.setOcena(ocena.getOcena());
        }
        if(ocena.getUporabnik() != null) {
            Uporabnik oldUporabnik = o.getUporabnik();
            if (oldUporabnik != null) {
                oldUporabnik.getOcene().remove(oceneZrno.getOcenaById(ocena.getOcena_id()));
                uporabnikiZrno.posodobiUporabnika(oldUporabnik);
            }

            // Add a new ocena for the uporabnik
            Uporabnik newUporabnik = uporabnikiZrno.getUporabnikById(ocena.getUporabnik().getUporabnik_id());
            o.setUporabnik(newUporabnik);
            newUporabnik.getOcene().add(o);
            uporabnikiZrno.posodobiUporabnika(newUporabnik);
        }
        if (ocena.getFilm() != null) {
//            odstrani staro oceno filma
            Ocena ocenaToRemove = null;
            Integer ocena_id = ocena.getOcena_id();
            List<Film> filmi = filmiZrno.getFilmi();
            for(Film f : filmi) {
                List<Ocena> ocene = f.getOcene();
                for(Ocena o1 : ocene) {
                    if(o1.getOcena_id().equals(ocena_id)) {
                        ocene.remove(o1);
                        ocenaToRemove = o1;
                        break;
                    }
                }
                if(ocenaToRemove != null) {
                    f.setOcene(ocene);
                    filmiZrno.posodobiFilm(f);
                }
            }
//            dodaj novo oceno filma
            Film film = filmiZrno.getFilmById(ocena.getFilm().getFilm_id());
            o.setFilm(film);
            film.getOcene().add(o);
            filmiZrno.posodobiFilm(film);
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
        List<Zanr> zanr_preference = uporabnik.getZanr_preference();
        if(zanr_preference != null && !zanr_preference.isEmpty()) {
            List<Zanr> obstojeciZanri = zanrZrno.getZanri();
            if(obstojeciZanri == null) {
                obstojeciZanri = new ArrayList<>();
            }
            for(Zanr zanr : uporabnik.getZanr_preference()) {
                obstojeciZanri.add(zanrZrno.getZanrById(zanr.getZanr_id()));
            }
            u.setZanr_preference(obstojeciZanri);
        }
        uporabnikiZrno.posodobiUporabnika(u);
    }

    public void posodobiIgralca(IgralecDto igralec) {
        Igralec i = igralciZrno.getIgralecById(igralec.getIgralec_id());
        if(i == null) {
            log.warning("Igralec ne obstaja!");
            return;
        }
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

        }
        igralciZrno.posodobiIgralca(i);
    }

    public void posodobiFilm(FilmDto filmDto) {
        Film film = filmiZrno.pridobiFilm(filmDto.getFilm_id());
        String naslov = filmDto.getNaslov();
        if(naslov != null && !naslov.isEmpty()) {
            film.setNaslov(naslov);
        }
        String opis = filmDto.getOpis();
        if( opis != null && !opis.isEmpty()) {
            film.setNaslov(opis);
        }
        Integer leto = filmDto.getLeto_izzida();
        if(leto != null) {
            film.setLeto_izzida(leto);
        }
        Zanr zanr = filmDto.getZanr();
        if(zanr != null) {
            film.setZanr(zanr);
        }
        String zasedba = filmDto.getZasedba();
        if(zasedba != null) {
            film.setZasedba(jsonb.fromJson(zasedba, new ArrayList<Igralec>(){}.getClass().getGenericSuperclass()));
        }

        List<Ocena> ocene = filmDto.getOcene();
        if(ocene != null) {
            film.setOcene(ocene);
        }
        filmiZrno.posodobiFilm(film);
    }

    private Boolean validirajOceno(OcenaDto oDTO){
        Double ocena = oDTO.getOcena();
        return ocena != null && (ocena < 0 || ocena > 10 );
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
            film.setPovprecna_ocena(el.getOcena().doubleValue());
            film.setOcene(el.getOcene());
            result.add(film);
        }
        return result;
    }

    // delete ocena from film
    public Boolean odstraniOceno(Integer filmId, Integer ocenaId) {
        Film film = filmiZrno.getFilmById(filmId);
        Ocena ocena = oceneZrno.getOcenaById(ocenaId);
        if(film != null && ocena != null) {
            film.getOcene().remove(ocena);
            filmiZrno.posodobiFilm(film);
            return true;
        }
        return false;
    }
}
