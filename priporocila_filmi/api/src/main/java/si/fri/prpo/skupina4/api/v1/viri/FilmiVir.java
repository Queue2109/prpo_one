package si.fri.prpo.skupina4.api.v1.viri;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.skupina4.Film;
import si.fri.prpo.skupina4.dtos.FilmDto;
import si.fri.prpo.skupina4.interceptorji.BelezenjeKlicevInterceptor;
import si.fri.prpo.skupina4.odjemalci.LoveCalcApiOdjemalec;
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

    @Inject
    private LoveCalcApiOdjemalec loveCalcApiOdjemalec;

//    @GET
//    @Path("{id}")
//    public Response getFilmById(@PathParam("id") Integer id){
//        List<Film> f = new ArrayList<>();
//        f.add(filmiZrno.getFilmById(id));
//        List<FilmDto> fdto = upravljanjeFilmovZrno.mapFilmToDTO(f);
//        return Response.ok(fdto).build();
//    }

//    @Operation(description = "Vrne podrobnosti filma", summary = "Podrobnosti filma."),
//    @APIResponses({
//            @APIResponse(responseCode = "200",
//                    description = "Podrobnosti filma",
//                    content = @Content(schema = @Schema(implementation = Film.class, type = SchemaType.ARRAY))
//    }),
//    @GET,
//    @Path("{id}")

    @Operation(description = "Vrne podrobnosti filma", summary = "Podrobnosti filma.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Podrobnosti filma",
                    content = @Content(schema = @Schema(implementation = Film.class, type = SchemaType.OBJECT)))
    })
    @GET
    @Path("{id}")
    public Response pridobiFilm(@PathParam("id") Integer id) {
        Film film = filmiZrno.pridobiFilm(id);
        if (film != null) {
            return Response.ok(film).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(description = "Vrne seznam vseh filmov", summary = "Seznam filmov.")
    @APIResponses({
            @APIResponse(responseCode = "200",
            description = "Seznam filmov",
            content = @Content(schema = @Schema(implementation = Film.class, type = SchemaType.ARRAY)),
            headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih filmov")})
    })
    @GET
    public Response pridobiFilme() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long filmiCount = filmiZrno.pridobiFilmeCount(query);
        return Response
                .ok(filmiZrno.pridobiFilme(query))
                .header("X-Total-Count", filmiCount)
                .build();
    }

    @Operation(description = "Dodaj nov film", summary = "Dodajanje novega filma")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Film uspešno dodan"),
            @APIResponse(responseCode = "500",
                    description = "Napaka na strežniku"),
            @APIResponse(responseCode = "405",
                    description = "Validacijska napaka"),
            @APIResponse(responseCode = "400",
                    description = "Napaka pri dodajanju filma")
    })
    @POST
    @Path("dodaj")
    public Response ustvariNovFilm(
            @RequestBody(description = "DTO objekt filma",
                        required = true,
                        content = @Content(schema = @Schema(implementation = Film.class)))
            FilmDto filmDto) {

        Film novFilm = upravljanjeFilmovZrno.ustvariFilm(filmDto);

        if (novFilm == null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @Operation(description = "posodobi film", summary = "Posodobitev filma")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Film uspešno posodobljen"),
            @APIResponse(responseCode = "500",
                    description = "Napaka na strežniku"),
            @APIResponse(responseCode = "404",
                    description = "Film ne obstaja")
    })
    @PUT
    @Path("posodobi/{id}")
    public Response posodobiFilm(@PathParam("id") Integer id, FilmDto filmDto){
        Film f = filmiZrno.getFilmById(id);
        if (f == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        filmDto.setFilm_id(id);
        upravljanjeFilmovZrno.posodobiFilm(filmDto);
        return Response.status(Response.Status.OK).entity(filmDto).build();
    }

    @Operation(description = "izbriši oceno filmu", summary = "Brisanje ocene filma")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Ocena uspešno odstranjena"),
            @APIResponse(responseCode = "500",
                    description = "Napaka na strežniku"),
            @APIResponse(responseCode = "404",
                    description = "Film s to oceno ne obstaja")
    })
    @DELETE
    @Path("{f_id}/ocena/{o_id}")
    public Response odstraniOceno(@PathParam("f_id") Integer f_id, @PathParam("o_id") Integer o_id){
        Film f = filmiZrno.getFilmById(f_id);
        if (f == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(upravljanjeFilmovZrno.odstraniOceno(f_id, o_id))
            return Response.status(Response.Status.OK).entity(f_id).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

}
