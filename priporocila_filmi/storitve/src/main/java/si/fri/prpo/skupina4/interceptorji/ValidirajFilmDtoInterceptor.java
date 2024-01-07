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
            if(film != null && film.getNaslov() == null || film.getNaslov().isEmpty()
                    || film.getLeto_izzida() == null) {
                String msg = ("Film mora vsebovati naslov in leto izzida!");
                log.severe(msg);
                throw new NeveljavenVnosIzjema(msg);
            }
        }
        return context.proceed();
    }



}
