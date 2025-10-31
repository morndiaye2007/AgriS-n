package com.agri.sen.controller;

import com.agri.sen.model.RessourceDTO;
import com.agri.sen.model.Response;
import com.agri.sen.services.RessourceService;
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
@RequestMapping("ressources")
@RequiredArgsConstructor
@CrossOrigin("*")

public class RessourceController {

    private final RessourceService ressourceService;

    @Operation(summary = "Create ressource", description = "this endpoint takes input ressource and saves it")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createRessource(@RequestBody RessourceDTO ressourceDTO) {
        try {
            var dto = ressourceService.createRessource(ressourceDTO);
            return Response.ok().setPayload(dto).setMessage("Ressource créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateRessource(@Parameter(name = "id", description = "the ressource id to updated") @PathVariable("id") Long id, @RequestBody RessourceDTO ressourceDTO) {
        ressourceDTO.setId(id);
        try {
            var dto = ressourceService.updateRessource(ressourceDTO);
            return Response.ok().setPayload(dto).setMessage("ressource modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }
    @Operation(summary = "Read the ressource", description = "This endpoint is used to read ressource, it takes input id ressource")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getRessource(@Parameter(name = "id", description = "the type ressource id to valid") @PathVariable Long id) {
        try {
            var dto = ressourceService.getRessource(id);
            return Response.ok().setPayload(dto).setMessage("ressource trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }
    @Operation(summary = "Read all Budget", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllRessource(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = ressourceService.getAllRessources(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder().number(page.getNumber()).totalElements(page.getTotalElements()).size(page.getSize()).totalPages(page.getTotalPages()).build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
    @Operation(summary = "delete the ressource", description = "Delete ressource, it takes input id ressource")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRessource(@PathVariable("id") Long id) {
        try {
            ressourceService.deleteRessource(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
