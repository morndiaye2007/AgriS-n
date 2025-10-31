package com.agri.sen.controller;

import com.agri.sen.model.CommandeDTO;
import com.agri.sen.model.Response;
import com.agri.sen.services.CommandeService;
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
@RequestMapping("commandes")
@RequiredArgsConstructor
@CrossOrigin("*")

public class CommandeController {

    private final CommandeService commandeService;

    @Operation(summary = "Create commande", description = "this endpoint takes input commande and saves it")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createCommande(@RequestBody CommandeDTO produitDTO) {
        try {
            var dto = commandeService.createCommande(produitDTO);
            return Response.ok().setPayload(dto).setMessage("Commande créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateCommande(@Parameter(name = "id", description = "the commande id to updated") @PathVariable("id") Long id, @RequestBody CommandeDTO produitDTO) {
        produitDTO.setId(id);
        try {
            var dto = commandeService.updateCommande(produitDTO);
            return Response.ok().setPayload(dto).setMessage("commande modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }

    @Operation(summary = "Read the commande", description = "This endpoint is used to read commande, it takes input id commande")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getCommande(@Parameter(name = "id", description = "the type commande id to valid") @PathVariable Long id) {
        try {
            var dto = commandeService.getCommande(id);
            return Response.ok().setPayload(dto).setMessage("commande trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read all Budget", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllCommande(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = commandeService.getAllCommandes(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder().number(page.getNumber()).totalElements(page.getTotalElements()).size(page.getSize()).totalPages(page.getTotalPages()).build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }


    @Operation(summary = "delete the commande", description = "Delete commande, it takes input id commande")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommande(@PathVariable("id") Long id) {
        try {
            commandeService.deleteCommande(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
