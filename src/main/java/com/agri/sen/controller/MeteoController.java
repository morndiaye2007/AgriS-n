package com.agri.sen.controller;

import com.agri.sen.model.Response;
import com.agri.sen.services.MeteoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("meteo")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MeteoController {

    private final MeteoService meteoService;

    @Operation(summary = "Obtenir la météo actuelle", description = "Ce point de terminaison permet d'obtenir les conditions météorologiques actuelles pour les coordonnées données.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description =  "La requête envoyée par le client était syntaxiquement incorrecte."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur lors du traitement de la requête")
    })
    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getCurrentMeteo(
            @Parameter(name = "latitude", description = "Coordonnée de latitude", required = true)
            @RequestParam Double latitude,
            @Parameter(name = "longitude", description = "Coordonnée Longitude", required = true)
            @RequestParam Double longitude) {
        try {
            var dto = meteoService.getCurrentMeteo(latitude, longitude);
            return Response.ok().setPayload(dto).setMessage("Météo actuelle récupérée");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Obtenir les prévisions météo\n", description = "Ce point de terminaison permet d'obtenir les prévisions météorologiques pour des coordonnées et un nombre de jours donnés.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "La requête envoyée par le client était syntaxiquement incorrecte."),
            @ApiResponse(responseCode = "500", description = "La requête envoyée par le client était syntaxiquement incorrecte.")
    })
    @GetMapping("/forecast")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getMeteoForecast(
            @Parameter(name = "latitude", description = "Latitude coordinate", required = true)
            @RequestParam Double latitude,
            @Parameter(name = "longitude", description = "Longitude coordinate", required = true)
            @RequestParam Double longitude,
            @Parameter(name = "days", description = "Number of days for forecast (default: 5)")
            @RequestParam(defaultValue = "5") Integer days) {
        try {
            var forecasts = meteoService.getMeteoForecast(latitude, longitude, days);
            return Response.ok().setPayload(forecasts)
                    .setMessage("Prévisions météo récupérées pour " + days + " jours");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Get weather by city", description = "This endpoint gets weather data for a specific city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/ville/{ville}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getMeteoByVille(
            @Parameter(name = "ville", description = "City name")
            @PathVariable String ville) {
        try {
            var meteoList = meteoService.getMeteoByVille(ville);
            return Response.ok().setPayload(meteoList).setMessage("Météo trouvée pour " + ville);
        } catch (Exception ex) {
            return Response.notFound().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the weather data", description = "This endpoint is used to read weather data by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getMeteo(
            @Parameter(name = "id", description = "The weather data id to retrieve")
            @PathVariable Long id) {
        try {
            var dto = meteoService.getMeteo(id);
            return Response.ok().setPayload(dto).setMessage("Données météo trouvées");
        } catch (Exception ex) {
            return Response.notFound().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read all weather data", description = "It takes input params and returns paginated list of weather data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllMeteo(
            @RequestParam(required = false) Map<String, String> searchParams,
            Pageable pageable) {
        var page = meteoService.getAllMeteo(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}