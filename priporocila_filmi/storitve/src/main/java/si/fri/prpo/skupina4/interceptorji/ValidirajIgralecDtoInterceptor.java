package si.fri.prpo.skupina4.interceptorji;

import si.fri.prpo.skupina4.dtos.IgralecDto;
import si.fri.prpo.skupina4.izjeme.NeveljavenVnosIzjema;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

public class ValidirajIgralecDtoInterceptor {

    Logger log = Logger.getLogger(si.fri.prpo.skupina4.interceptorji.ValidirajIgralecDtoInterceptor.class.getName());

    @AroundInvoke
    public Object validirajIgralca(InvocationContext context) throws Exception {

        if(context.getParameters().length == 1  && context.getParameters()[0] instanceof IgralecDto) {
            IgralecDto igralec = (IgralecDto) context.getParameters()[0];
            if(igralec.getIme() == null || igralec.getIme().isEmpty()
                    || igralec.getPriimek() == null || igralec.getPriimek().isEmpty()) {
                String msg = ("Igralec mora imati podano ime in priimek!");
                log.severe(msg);
                throw new NeveljavenVnosIzjema(msg);
            }
        }
        return context.proceed();
    }




}
