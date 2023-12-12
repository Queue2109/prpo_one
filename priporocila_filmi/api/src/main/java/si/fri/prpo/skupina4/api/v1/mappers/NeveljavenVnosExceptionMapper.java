package si.fri.prpo.skupina4.api.v1.mappers;

import si.fri.prpo.skupina4.izjeme.NeveljavenVnosIzjema;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NeveljavenVnosExceptionMapper implements ExceptionMapper<NeveljavenVnosIzjema> {

    @Override
    public Response toResponse(NeveljavenVnosIzjema e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{ \"napaka\":\"" + e.getMessage() + "\"}")
                .build();
    }
}
