package si.fri.prpo.skupina4.interceptorji;

import si.fri.prpo.skupina4.dtos.UporabnikDto;
import si.fri.prpo.skupina4.izjeme.NeveljavenVnosIzjema;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

public class ValidirajUporabnikDtoInterceptor {

    Logger log = Logger.getLogger(ValidirajUporabnikDtoInterceptor.class.getName());

    @AroundInvoke
    public Object validirajUporabnika(InvocationContext context) throws Exception {

        if(context.getParameters().length == 1  && context.getParameters()[0] instanceof UporabnikDto) {
            UporabnikDto uporabnik = (UporabnikDto) context.getParameters()[0];
            if(uporabnik.getGeslo() == null || uporabnik.getGeslo().isEmpty()
            || uporabnik.getEmail() == null || uporabnik.getEmail().isEmpty()) {
                String msg = ("Email in geslo sta obvezna podatka!");
                log.severe(msg);
                throw new NeveljavenVnosIzjema(msg);
            }
        }
        return context.proceed();
    }
}
