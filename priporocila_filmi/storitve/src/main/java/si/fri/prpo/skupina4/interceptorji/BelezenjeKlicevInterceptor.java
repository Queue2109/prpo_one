package si.fri.prpo.skupina4.interceptorji;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.HashMap;
import java.util.logging.Logger;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {

    private Logger log = Logger.getLogger(BelezenjeKlicevInterceptor.class.getName());

    static HashMap<String, Integer> klici = new HashMap<>();

    @AroundInvoke
    public Object beleziKlice(InvocationContext context) throws Exception {
        String metoda = context.getMethod().toString();
        log.info("vstop v metodo: " + metoda);

        int st = klici.getOrDefault(metoda, 0) + 1;
        klici.put(metoda, st);
        log.info(Integer.toString(st));

        try{
            Object result = context.proceed();
            log.info("izstop iz metode: " + metoda);
            return result;
        } catch (Exception e) {
            log.info("izjema v metodi: " + metoda);
            throw e;
        }
    }
}
