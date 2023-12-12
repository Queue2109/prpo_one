package si.fri.prpo.skupina4.api.v1.viri;

import si.fri.prpo.skupina4.Film;
import si.fri.prpo.skupina4.dtos.FilmDto;
import si.fri.prpo.skupina4.interceptorji.BelezenjeKlicevInterceptor;
import si.fri.prpo.skupina4.zrna.FilmiZrno;
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
@Path("filmi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Interceptors(BelezenjeKlicevInterceptor.class)
public class FilmiVir {
    @Context
    protected UriInfo uriInfo;

    @Inject
    private FilmiZrno filmiZrno;

    @Inject
    private UpravljanjeFilmovZrno upravljanjeFilmovZrno;

//    @GET
//    @Path("{id}")
//    public Response getFilmById(@PathParam("id") Integer id){
//        List<Film> f = new ArrayList<>();
//        f.add(filmiZrno.getFilmById(id));
//        List<FilmDto> fdto = upravljanjeFilmovZrno.mapFilmToDTO(f);
//        return Response.ok(fdto).build();
//    }

    @GET
    public Response pridobiFilme() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long filmiCount = filmiZrno.pridobiFilmeCount(query);
        return Response
                .ok(filmiZrno.pridobiFilme(query))
                .header("X-Total-Count", filmiCount)
                .build();
    }


    @POST
    @Path("dodaj")
    public Response ustvariNovFilm(FilmDto filmDto){
        Film novFilm = upravljanjeFilmovZrno.ustvariFilm(filmDto);

        if (novFilm == null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

}
