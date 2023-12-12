package si.fri.prpo.skupina4.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.skupina4.Film;
import si.fri.prpo.skupina4.Zanr;
import si.fri.prpo.skupina4.dtos.FilmDto;
import si.fri.prpo.skupina4.dtos.ZanrDto;
import si.fri.prpo.skupina4.interceptorji.BelezenjeKlicevInterceptor;
import si.fri.prpo.skupina4.zrna.FilmiZrno;
import si.fri.prpo.skupina4.zrna.UpravljanjeFilmovZrno;
import si.fri.prpo.skupina4.zrna.ZanrZrno;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.awt.desktop.SystemSleepEvent;
import java.util.ArrayList;
import java.util.List;

@Path("zanr")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Interceptors(BelezenjeKlicevInterceptor.class)
public class ZanrVir {
    @Context
    protected UriInfo uriInfo;
    @Inject
    ZanrZrno zanrZrno;

    @Inject
    FilmiZrno filmiZrno;
    @Inject
    UpravljanjeFilmovZrno upravljanjeFilmovZrno;

//    @GET
//    public Response vrniZanre(){
//
//        List<Zanr> zanri = zanrZrno.getZanri();
//
//        List<ZanrDto> zanriDto = new ArrayList<>(zanrZrno.mapZanrToDTO(zanri));
//
//        return Response
//                .status(Response.Status.OK)
//                .entity(zanriDto)
//                .build();
//    }

    @GET
    public Response pridobiZanre() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long zanriCount = zanrZrno.pridobiZanreCount(query);
        return Response
                .ok(zanrZrno.pridobiZanre(query))
                .header("X-Total-Count", zanriCount)
                .build();
    }
    @GET
    @Path("{id}")
    public Response getFilmsByZanr(@PathParam("id") Integer zanrId){
        List<Film> f = filmiZrno.getFilmsByZanr(zanrId);
        List<FilmDto> filmidto = new ArrayList<>(upravljanjeFilmovZrno.mapFilmToDTO(f));

        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(filmidto);

        return Response.status(Response.Status.OK).entity(result).build();
    }

    @POST
    @Path("ustvariNovZanr")
    public Response ustvariNovZanr(ZanrDto zanrDto){
        Zanr novZanr = upravljanjeFilmovZrno.ustvariZanr(zanrDto);

        if (novZanr != null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

}
