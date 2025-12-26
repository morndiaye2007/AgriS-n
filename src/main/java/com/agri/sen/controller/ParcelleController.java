package com.agri.sen.controller;

import com.agri.sen.model.ParcelleDTO;
import com.agri.sen.model.Response;
import com.agri.sen.services.ParcelleService;
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
@RequestMapping("parcelles")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ParcelleController {

    private final ParcelleService parcelleService;

    @Operation(summary = "Create parcelle", description = "This endpoint takes input parcelle and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createParcelle(@RequestBody ParcelleDTO parcelleDTO) {
        try {
            var dto = parcelleService.createParcelle(parcelleDTO);
            return Response.created().setPayload(dto).setMessage("Parcelle créée avec succès");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Update parcelle", description = "This endpoint updates an existing parcelle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateParcelle(
            @Parameter(name = "id", description = "The parcelle id to update")
            @PathVariable("id") Long id,
            @RequestBody ParcelleDTO parcelleDTO) {
        parcelleDTO.setId(id);
        try {
            var dto = parcelleService.updateParcelle(parcelleDTO);
            return Response.ok().setPayload(dto).setMessage("Parcelle modifiée avec succès");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }
    @Operation(summary = "Read the parcelle", description = "This endpoint is used to read parcelle, it takes input id parcelle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getParcelle(
            @Parameter(name = "id", description = "The parcelle id to retrieve")
            @PathVariable Long id) {
        try {
            var dto = parcelleService.getParcelle(id);
            return Response.ok().setPayload(dto).setMessage("Parcelle trouvée");
        } catch (Exception ex) {
            return Response.notFound().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Get current user parcelles", description = "Returns parcelles of the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/mes-parcelles")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getCurrentUserParcelles(
            @RequestParam(required = false) Map<String, String> searchParams,
            Pageable pageable) {
        var page = parcelleService.getCurrentUserParcelles(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Read all parcelles", description = "It takes input params and returns paginated list of parcelles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllParcelles(
            @RequestParam(required = false) Map<String, String> searchParams,
            Pageable pageable) {
        var page = parcelleService.getAllParcelles(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Get total superficie", description = "Returns total superficie for an agriculteur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/agriculteur/{agriculteurId}/superficie-total")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getTotalSuperficie(@PathVariable Long agriculteurId) {
        try {
            Double total = parcelleService.getTotalSuperficie(agriculteurId);
            return Response.ok().setPayload(total).setMessage("Superficie totale calculée");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Delete the parcelle", description = "Delete parcelle, it takes input id parcelle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParcelle(@PathVariable("id") Long id) {
        try {
            parcelleService.deleteParcelle(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}