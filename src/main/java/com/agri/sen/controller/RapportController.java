package com.agri.sen.controller;

import com.agri.sen.model.RapportDTO;
import com.agri.sen.model.Response;
import com.agri.sen.services.RapportService;
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
@RequestMapping("rapports")
@RequiredArgsConstructor
@CrossOrigin("*")

public class RapportController {

    private final RapportService rapportService;

    @Operation(summary = "Create rapport", description = "this endpoint takes input rapport and saves it")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createRapport(@RequestBody RapportDTO produitDTO) {
        try {
            var dto = rapportService.createRapport(produitDTO);
            return Response.ok().setPayload(dto).setMessage("Rapport créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateRapport(@Parameter(name = "id", description = "the rapport id to updated") @PathVariable("id") Long id, @RequestBody RapportDTO produitDTO) {
        produitDTO.setId(id);
        try {
            var dto = rapportService.updateRapport(produitDTO);
            return Response.ok().setPayload(dto).setMessage("rapport modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }
    @Operation(summary = "Read the rapport", description = "This endpoint is used to read rapport, it takes input id rapport")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getRapport(@Parameter(name = "id", description = "the type rapport id to valid") @PathVariable Long id) {
        try {
            var dto = rapportService.getRapport(id);
            return Response.ok().setPayload(dto).setMessage("rapport trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read all Budget", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllRapport(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = rapportService.getAllRapport(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder().number(page.getNumber()).totalElements(page.getTotalElements()).size(page.getSize()).totalPages(page.getTotalPages()).build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
    @Operation(summary = "delete the rapport", description = "Delete rapport, it takes input id rapport")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRapport(@PathVariable("id") Long id) {
        try {
            rapportService.deleteRapport(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
