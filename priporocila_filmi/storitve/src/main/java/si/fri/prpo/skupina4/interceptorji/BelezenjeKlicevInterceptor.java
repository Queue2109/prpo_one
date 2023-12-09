package si.fri.prpo.skupina4.interceptorji;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {

    private Logger log = Logger.getLogger(BelezenjeKlicevInterceptor.class.getName());

    @AroundInvoke
    public Object beleziKlice(InvocationContext context) throws Exception {
        log.info("vstop v metodo: " + context.getClass().getName());
        try{
            Object result = context.proceed();
            log.info("izstop iz metode: " + context.getClass().getName());
            return result;
        } catch (Exception e) {
            log.info("izjema v metodi: " + context.getClass().getName());
            throw e;
        }
    }
}
