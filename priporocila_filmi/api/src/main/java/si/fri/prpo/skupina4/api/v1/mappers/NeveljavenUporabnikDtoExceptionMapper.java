package si.fri.prpo.skupina4.api.v1.mappers;

import si.fri.prpo.skupina4.izjeme.NeveljavenUporabnikDtoIzjema;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NeveljavenUporabnikDtoExceptionMapper implements ExceptionMapper<NeveljavenUporabnikDtoIzjema> {

    @Override
    public Response toResponse(NeveljavenUporabnikDtoIzjema e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{ \"napaka\":\"" + e.getMessage() + "\"}")
                .build();
    }
}
