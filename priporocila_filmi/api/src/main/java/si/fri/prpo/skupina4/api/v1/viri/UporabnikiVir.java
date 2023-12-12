package si.fri.prpo.skupina4.api.v1.viri;

import si.fri.prpo.skupina4.Uporabnik;
import si.fri.prpo.skupina4.dtos.UporabnikDto;
import si.fri.prpo.skupina4.interceptorji.BelezenjeKlicevInterceptor;
import si.fri.prpo.skupina4.zrna.UporabnikiZrno;
import si.fri.prpo.skupina4.zrna.UpravljanjeFilmovZrno;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.kumuluz.ee.rest.beans.QueryParameters;

// Root path /api/v1
@Path("uporabniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Interceptors(BelezenjeKlicevInterceptor.class)
public class UporabnikiVir {
    @Context
    protected UriInfo uriInfo;

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Inject UpravljanjeFilmovZrno upravljanjeFilmovZrno;

    @GET
    public Response pridobiUporabnike() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long uporabnikiCount = uporabnikiZrno.pridobiUporabnikeCount(query);
        return Response
                .ok(uporabnikiZrno.pridobiUporabnike(query))
                .header("X-Total-Count", uporabnikiCount)
                .build();
    }


    @POST
    @Path("dodaj")
    public Response ustvariNovegaUporabnika(UporabnikDto uporabnikDto){
        Uporabnik uporabnik = upravljanjeFilmovZrno.ustvariUporabnika(uporabnikDto);

        if (uporabnik == null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("posodobi/{id}")
    public Response posodobiUporabnika(@PathParam("id") Integer id, UporabnikDto uporabnikDto){
        Uporabnik u = uporabnikiZrno.getUporabnikById(id);
        if (u == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        upravljanjeFilmovZrno.posodobiUporabnika(uporabnikDto);
        return Response.status(Response.Status.OK).entity(uporabnikDto).build();
    }
}

