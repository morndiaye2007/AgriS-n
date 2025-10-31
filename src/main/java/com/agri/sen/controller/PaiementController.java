package com.agri.sen.controller;

import com.agri.sen.model.PaiementDTO;
import com.agri.sen.model.Response;
import com.agri.sen.services.PaiementService;
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
@RequestMapping("paiements")
@RequiredArgsConstructor
@CrossOrigin("*")

public class PaiementController {

    private final PaiementService paiementService;

    @Operation(summary = "Create paiement", description = "this endpoint takes input paiement and saves it")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createPaiement(@RequestBody PaiementDTO paiementDTO) {
        try {
            var dto = paiementService.createPaiement(paiementDTO);
            return Response.ok().setPayload(dto).setMessage("Paiement créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updatePaiement(@Parameter(name = "id", description = "the paiement id to updated") @PathVariable("id") Long id, @RequestBody PaiementDTO paiementDTO) {
        paiementDTO.setId(id);
        try {
            var dto = paiementService.updatePaiement(paiementDTO);
            return Response.ok().setPayload(dto).setMessage("paiement modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }

    @Operation(summary = "Read the paiement", description = "This endpoint is used to read paiement, it takes input id paiement")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getPaiement(@Parameter(name = "id", description = "the type paiement id to valid") @PathVariable Long id) {
        try {
            var dto = paiementService.getPaiement(id);
            return Response.ok().setPayload(dto).setMessage("paiement trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read all Budget", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllPaiement(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = paiementService.getAllPaiements(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder().number(page.getNumber()).totalElements(page.getTotalElements()).size(page.getSize()).totalPages(page.getTotalPages()).build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }


    @Operation(summary = "delete the paiement", description = "Delete paiement, it takes input id paiement")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePaiement(@PathVariable("id") Long id) {
        try {
            paiementService.deletePaiement(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
