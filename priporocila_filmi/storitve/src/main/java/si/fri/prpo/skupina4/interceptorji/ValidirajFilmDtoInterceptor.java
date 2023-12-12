package si.fri.prpo.skupina4.interceptorji;

import si.fri.prpo.skupina4.dtos.FilmDto;
import si.fri.prpo.skupina4.izjeme.NeveljavenVnosIzjema;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

public class ValidirajFilmDtoInterceptor {

    Logger log = Logger.getLogger(si.fri.prpo.skupina4.interceptorji.ValidirajFilmDtoInterceptor.class.getName());

    @AroundInvoke
    public Object validirajFilm(InvocationContext context) throws Exception {

        if(context.getParameters().length == 1  && context.getParameters()[0] instanceof FilmDto) {
            FilmDto film = (FilmDto) context.getParameters()[0];
            if(film.getNaslov() == null || film.getNaslov().isEmpty()
                    || film.getZanr() == null
                    || film.getLeto_izzida() == null
                    || film.getZasedba() == null
                    || film.getOcene() == null) {
                String msg = ("Film mora vsebovati naslov, žanr, leto izzida, zasedbo in ocene!");
                log.severe(msg);
                throw new NeveljavenVnosIzjema(msg);
            }
        }
        return context.proceed();
    }



}
