package si.fri.prpo.skupina4.api.v1.viri;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import si.fri.prpo.skupina4.Ocena;
import si.fri.prpo.skupina4.Uporabnik;
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

@Tag(name="Ocene")
@Path("ocene")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Interceptors(BelezenjeKlicevInterceptor.class)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
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

    @Operation(description = "Vrne vse ocena", summary = "Seznam ocen.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam ocen",
                    content = @Content(schema = @Schema(implementation = Ocena.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih ocen")}
            )
    })
    @GET
    public Response pridobiOcene() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long oceneCount = oceneZrno.pridobiOceneCount(query);
        return Response
                .ok(oceneZrno.pridobiOcene(query))
                .header("X-Total-Count", oceneCount)
                .build();
    }

    @Operation(description = "Dodaj oceno", summary = "Dodajanje nove ocene")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Ocena uspešno dodan"),
            @APIResponse(responseCode = "500",
                    description = "Napaka na strežniku"),
            @APIResponse(responseCode = "405",
                    description = "Validacijska napaka"),
            @APIResponse(responseCode = "400",
                    description = "Napaka pri dodajanju ocene")
    })
    @POST
    @Path("dodaj")
    public Response dodajOceno(OcenaDto ocenaDto){
        Ocena o = upravljanjeFilmovZrno.ustvariOceno(ocenaDto);
        if (o == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(o).build();
    }

    @Operation(description = "Posodobi oceno", summary = "Posodabljanje ocene.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Ocena uspešno posodobljen",
                    content = @Content(schema = @Schema(implementation = Ocena.class))
            ),
            @APIResponse(responseCode = "400",
                    description = "Neveljaven ID ocene"
            ),
            @APIResponse(responseCode = "404",
                    description = "Ocena ne obstaja"
            )
    })
    @PUT
    @Path("posodobi/{id}")
    public Response posodobiOceno(@PathParam("id") Integer id, OcenaDto ocenaDto){
        Ocena o = oceneZrno.getOcenaById(id);
        if (o == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        ocenaDto.setOcena_id(id);
        upravljanjeFilmovZrno.posodobiOcenoFilma(ocenaDto);
        return Response.status(Response.Status.OK).entity(ocenaDto).build();
    }
}
