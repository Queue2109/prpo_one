package si.fri.prpo.skupina4.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.skupina4.Igralec;
import si.fri.prpo.skupina4.Uporabnik;
import si.fri.prpo.skupina4.dtos.IgralecDto;
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

    @Operation(description = "Vrne seznam igralcev", summary = "Seznam igralcev.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam igralcev",
                    content = @Content(schema = @Schema(implementation = Igralec.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih igralcev")}
            )
    })
    @GET
    public Response pridobiIgralce() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long igralciCount = igralciZrno.pridobiIgralceCount(query);
        return Response
                .ok(igralciZrno.pridobiIgralce(query))
                .header("X-Total-Count", igralciCount)
                .build();
    }

    @Operation(description = "Dodaj novega igralca", summary = "Dodajanje novega igralca")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Igralec uspešno dodan"),
            @APIResponse(responseCode = "500",
                    description = "Napaka na strežniku"),
            @APIResponse(responseCode = "405",
                    description = "Validacijska napaka"),
            @APIResponse(responseCode = "400",
                    description = "Napaka pri dodajanju igralca")
    })
    @POST
    @Path("dodaj")
    public Response ustvariNovegaIgralca(IgralecDto igralecDto){
        Igralec igralec = upravljanjeFilmovZrno.ustvariIgralca(igralecDto);

        if (igralec == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @Operation(description = "Posodobi igralca", summary = "Posodabljanje igralca.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Igralec uspešno posodobljen",
                    content = @Content(schema = @Schema(implementation = Igralec.class))
            ),
            @APIResponse(responseCode = "400",
                    description = "Neveljaven ID igralca"
            ),
            @APIResponse(responseCode = "404",
                    description = "Igralec ne obstaja"
            )
    })
    @PUT
    @Path("posodobi/{id}")
    public Response posodobiIgralca(@PathParam("id") Integer id, IgralecDto igralecDto){
        Igralec i = igralciZrno.getIgralecById(id);
        if (i == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        igralecDto.setIgralec_id(id);
        upravljanjeFilmovZrno.posodobiIgralca(igralecDto);
        return Response.status(Response.Status.OK).entity(igralecDto).build();
    }



}
