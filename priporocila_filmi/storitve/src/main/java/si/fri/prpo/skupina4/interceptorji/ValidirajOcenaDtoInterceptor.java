package si.fri.prpo.skupina4.interceptorji;

import si.fri.prpo.skupina4.dtos.OcenaDto;
import si.fri.prpo.skupina4.izjeme.NeveljavenVnosIzjema;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

public class ValidirajOcenaDtoInterceptor {

    Logger log = Logger.getLogger(si.fri.prpo.skupina4.interceptorji.ValidirajOcenaDtoInterceptor.class.getName());

    @AroundInvoke
    public Object validirajOceno(InvocationContext context) throws Exception {

        if(context.getParameters().length == 1  && context.getParameters()[0] instanceof OcenaDto) {
            OcenaDto ocena = (OcenaDto) context.getParameters()[0];
            if(ocena.getOcena() == null
                    || ocena.getUporabnik() == null
                    || ocena.getFilm() == null) {
                String msg = ("Ocena mora imeti podanega uporabnika in film!");
                log.severe(msg);
                throw new NeveljavenVnosIzjema(msg);

            } else if(ocena.getOcena() != null && ((ocena.getOcena() < 0) || (ocena.getOcena() > 10)) && ((ocena.getOcena() % 1) != 0)){
                String msgg = ("Napaka pri ustvarjanju ocene - ocena mora biti med 0 in 10!");
                throw new NeveljavenVnosIzjema(msgg);
            }
        }
        return context.proceed();
    }


}
