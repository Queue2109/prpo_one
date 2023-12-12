package si.fri.prpo.skupina4.interceptorji;

import si.fri.prpo.skupina4.dtos.ZanrDto;
import si.fri.prpo.skupina4.izjeme.NeveljavenVnosIzjema;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

public class ValidirajZanrDtoInterceptor {

    Logger log = Logger.getLogger(si.fri.prpo.skupina4.interceptorji.ValidirajZanrDtoInterceptor.class.getName());

    @AroundInvoke
    public Object validirajZanr(InvocationContext context) throws Exception {

        if(context.getParameters().length == 1  && context.getParameters()[0] instanceof ZanrDto) {
            ZanrDto zanr = (ZanrDto) context.getParameters()[0];
            if(zanr.getNaziv() == null || zanr.getNaziv().isEmpty()) {
                String msg = ("Žanr mora vsebovati naziv!");
                log.severe(msg);
                throw new NeveljavenVnosIzjema(msg);
            }
        }
        return context.proceed();
    }


}