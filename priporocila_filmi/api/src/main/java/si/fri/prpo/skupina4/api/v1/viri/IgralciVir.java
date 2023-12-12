package si.fri.prpo.skupina4.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.skupina4.Igralec;
import si.fri.prpo.skupina4.Uporabnik;
import si.fri.prpo.skupina4.dtos.IgralecDto;
import si.fri.prpo.skupina4.dtos.UporabnikDto;
import si.fri.prpo.skupina4.interceptorji.BelezenjeKlicevInterceptor;
import si.fri.prpo.skupina4.zrna.UpravljanjeFilmovZrno;
import si.fri.prpo.skupina4.zrna.IgralciZrno;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("igralci")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Interceptors(BelezenjeKlicevInterceptor.class)
public class IgralciVir {
    @Context
    protected UriInfo uriInfo;

    @Inject
    private IgralciZrno igralciZrno;

    @Inject
    UpravljanjeFilmovZrno upravljanjeFilmovZrno;
    @GET
    public Response pridobiIgralce() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long igralciCount = igralciZrno.pridobiIgralceCount(query);
        return Response
                .ok(igralciZrno.pridobiIgralce(query))
                .header("X-Total-Count", igralciCount)
                .build();
    }


    @POST
    @Path("dodaj")
    public Response ustvariNovegaIgralca(IgralecDto igralecDto){
        Igralec igralec = upravljanjeFilmovZrno.ustvariIgralca(igralecDto);

        if (igralec == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("posodobi/{id}")
    public Response posodobiIgralca(@PathParam("id") Integer id, IgralecDto igralecDto){
        Igralec i = igralciZrno.getIgralecById(id);
        if (i == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        upravljanjeFilmovZrno.posodobiIgralca(igralecDto);
        return Response.status(Response.Status.OK).entity(igralecDto).build();
    }



}
