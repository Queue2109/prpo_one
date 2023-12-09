package si.fri.prpo.skupina4.api.v1.viri;

import si.fri.prpo.skupina4.Film;
import si.fri.prpo.skupina4.Zanr;
import si.fri.prpo.skupina4.dtos.FilmDto;
import si.fri.prpo.skupina4.dtos.ZanrDto;
import si.fri.prpo.skupina4.zrna.FilmiZrno;
import si.fri.prpo.skupina4.zrna.UpravljanjeFilmovZrno;
import si.fri.prpo.skupina4.zrna.ZanrZrno;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("zanr")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ZanrVir {

    @Inject
    ZanrZrno zanrZrno;

    @Inject
    FilmiZrno filmiZrno;
    @Inject
    UpravljanjeFilmovZrno upravljanjeFilmovZrno;

    @GET
    public Response vrniZanre(){

        List<Zanr> zanri = zanrZrno.getZanri();

        List<ZanrDto> zanriDto = new ArrayList<>(zanrZrno.mapZanrToDTO(zanri));

        return Response
                .status(Response.Status.OK)
                .entity(zanriDto)
                .build();
    }

    @GET
    @Path("{id}")
    public Response getFilmsByZanr(@PathParam("id") Integer zanrId){
        List<Film> f = filmiZrno.getFilmsByZanr(zanrId);
        List<FilmDto> filmidto = new ArrayList<>(upravljanjeFilmovZrno.mapFilmToDTO(f));

        return Response.status(Response.Status.OK).entity(filmidto).build();
    }

}
