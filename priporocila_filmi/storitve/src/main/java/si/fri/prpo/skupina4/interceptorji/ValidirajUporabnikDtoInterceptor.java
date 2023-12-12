package si.fri.prpo.skupina4.interceptorji;

import si.fri.prpo.skupina4.dtos.UporabnikDto;
import si.fri.prpo.skupina4.izjeme.NeveljavenUporabnikDtoIzjema;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

public class ValidirajUporabnikDtoInterceptor {

    Logger log = Logger.getLogger(ValidirajUporabnikDtoInterceptor.class.getName());

    @AroundInvoke
    public Object validirajUporabnika(InvocationContext context) throws Exception {

        if(context.getParameters().length == 1  && context.getParameters()[0] instanceof UporabnikDto) {
            UporabnikDto uporabnik = (UporabnikDto) context.getParameters()[0];
            if(uporabnik.getUporabnisko_ime() == null || uporabnik.getUporabnisko_ime().isEmpty()
            || uporabnik.getGeslo() == null || uporabnik.getGeslo().isEmpty()
            || uporabnik.getEmail() == null || uporabnik.getEmail().isEmpty()) {
                String msg = ("Uporabniško ime, geslo in email so obvezni podatki!");
                log.severe(msg);
                throw new NeveljavenUporabnikDtoIzjema(msg);
            }
        }
        return context.proceed();
    }
}
