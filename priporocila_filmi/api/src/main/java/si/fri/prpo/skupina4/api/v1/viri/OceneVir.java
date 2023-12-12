package si.fri.prpo.skupina4.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.skupina4.Ocena;
import si.fri.prpo.skupina4.dtos.FilmDto;
import si.fri.prpo.skupina4.dtos.OcenaDto;
import si.fri.prpo.skupina4.interceptorji.BelezenjeKlicevInterceptor;
import si.fri.prpo.skupina4.zrna.OceneZrno;
import si.fri.prpo.skupina4.zrna.StatistikaZrno;
import si.fri.prpo.skupina4.zrna.UpravljanjeFilmovZrno;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("ocene")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Interceptors(BelezenjeKlicevInterceptor.class)
public class OceneVir {

    @Context
    protected UriInfo uriInfo;
    @Inject
    private StatistikaZrno statFilmZrno;
    @Inject
    private UpravljanjeFilmovZrno upravljanjeFilmovZrno;
    @Inject
    private OceneZrno oceneZrno;

    @GET
    @Path("topX")
    public Response getTopXFilms(@QueryParam("x") Integer x){
        List<FilmDto> filmi = statFilmZrno.topXFilmov(x);
        if (filmi == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.OK).entity(filmi).build();
    }

//    @GET
//    @Path("{id}")
//    public Response getOceneByFilm(@PathParam("id") Integer id){
//        Ocena o = oceneZrno.getOcenaById(id);
//        if (o == null){
//            return Response.status(Response.Status.NO_CONTENT).build();
//        }
//        return Response.status(Response.Status.OK).entity(o).build();
//    }

    @GET
    @Path("{id}")
    public Response pridobiOceneByFilm(@PathParam("id") Integer id) {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long oceneCount = oceneZrno.pridobiOceneCount(query);
        return Response
                .ok(oceneZrno.pridobiOcene(query))
                .header("X-Total-Count", oceneCount)
                .build();
    }

    @POST
    @Path("dodaj")
    public Response dodajOceno(OcenaDto ocenaDto){
        Ocena o = upravljanjeFilmovZrno.ustvariOceno(ocenaDto);
        if (o == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(o).build();
    }

    @PUT
    @Path("posodobi/{id}")
    public Response posodobiOceno(@PathParam("id") Integer id, OcenaDto ocenaDto){
        Ocena o = oceneZrno.getOcenaById(id);
        if (o == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        upravljanjeFilmovZrno.posodobiOcenoFilma(ocenaDto);
        return Response.status(Response.Status.OK).entity(ocenaDto).build();
    }
}
