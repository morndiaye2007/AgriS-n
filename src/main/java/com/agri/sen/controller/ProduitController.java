package com.agri.sen.controller;

import com.agri.sen.entity.enums.StatutType;
import com.agri.sen.model.ProduitDTO;
import com.agri.sen.model.Response;
import com.agri.sen.services.ProduitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("agents")
@RequiredArgsConstructor
@CrossOrigin("*")

public class ProduitController {

    private final ProduitService agentService;

    @Operation(summary = "Create agent", description = "this endpoint takes input agent and saves it")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createProduit(@RequestBody ProduitDTO produitDTO) {
        try {
            var dto = agentService.createProduit(produitDTO);
            return Response.ok().setPayload(dto).setMessage("Produit créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateProduit(@Parameter(name = "id", description = "the agent id to updated") @PathVariable("id") Long id, @RequestBody ProduitDTO produitDTO) {
        produitDTO.setId(id);
        try {
            var dto = agentService.updateProduit(produitDTO);
            return Response.ok().setPayload(dto).setMessage("agent modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }

    @Operation(summary = "Read the agent", description = "This endpoint is used to read agent, it takes input id agent")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getProduit(@Parameter(name = "id", description = "the type agent id to valid") @PathVariable Long id) {
        try {
            var dto = agentService.getProduit(id);
            return Response.ok().setPayload(dto).setMessage("agent trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read all Budget", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllProduits(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = agentService.getAllProduits(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder().number(page.getNumber()).totalElements(page.getTotalElements()).size(page.getSize()).totalPages(page.getTotalPages()).build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }


    @Operation(summary = "delete the agent", description = "Delete agent, it takes input id agent")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduit(@PathVariable("id") Long id) {
        try {
            agentService.deleteProduit(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
