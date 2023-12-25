package si.fri.prpo.skupina4.api.v1.viri;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
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

@Tag(name="Uporabniki")
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

    @Operation(description = "Vrne seznam uporabnikov", summary = "Seznam uporabnikov.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam uporabnikov",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih uporabnikov")}
            )
    })
    @GET
    public Response pridobiUporabnike() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long uporabnikiCount = uporabnikiZrno.pridobiUporabnikeCount(query);
        return Response
                .ok(uporabnikiZrno.pridobiUporabnike(query))
                .header("X-Total-Count", uporabnikiCount)
                .build();
    }

    @Operation(description = "Dodaj uporabnika", summary = "Dodajanje novega uporabnika.")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Uporabnik uspešno dodan"),
            @APIResponse(responseCode = "500",
                    description = "Napaka na strežniku"),
            @APIResponse(responseCode = "405",
                    description = "Validacijska napaka"),
            @APIResponse(responseCode = "400",
                    description = "Napaka pri dodajanju uporabnika")
    })
    @POST
    @Path("dodaj")
    public Response ustvariNovegaUporabnika(UporabnikDto uporabnikDto){
        Uporabnik uporabnik = upravljanjeFilmovZrno.ustvariUporabnika(uporabnikDto);

        if (uporabnik == null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }


    @Operation(description = "Posodobi uporabnika", summary = "Posodabljanje uporabnika.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Uporabnik uspešno posodobljen",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))
            ),
            @APIResponse(responseCode = "400",
                    description = "Neveljaven ID uporabnika"
            ),
            @APIResponse(responseCode = "404",
                    description = "Uporabnik ne obstaja"
            )
    })
    @PUT
    @Path("posodobi/{id}")
    public Response posodobiUporabnika(@PathParam("id") Integer id, UporabnikDto uporabnikDto){
        Uporabnik u = uporabnikiZrno.getUporabnikById(id);
        if (u == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        uporabnikDto.setUporabnik_id(id);
        upravljanjeFilmovZrno.posodobiUporabnika(uporabnikDto);
        return Response.status(Response.Status.OK).entity(uporabnikDto).build();
    }
}

