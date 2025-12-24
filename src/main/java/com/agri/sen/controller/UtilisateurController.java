package com.agri.sen.controller;

import com.agri.sen.model.Response;
import com.agri.sen.model.UtilisateurDTO;
import com.agri.sen.services.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


import java.util.Map;

@RestController
@RequestMapping("utilisateurs")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @Operation(summary = "Get current user", description = "This endpoint returns the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getCurrentUtilisateur() {
        try {
            var dto = utilisateurService.getCurrentUtilisateur();
            return Response.ok().setPayload(dto).setMessage("Utilisateur connecté");
        } catch (Exception ex) {
            return Response.unauthorized().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Create utilisateur", description = "This endpoint takes input utilisateur and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "409", description = "Email or phone already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createUtilisateur(@RequestBody UtilisateurDTO utilisateurDTO) {
        try {
            var dto = utilisateurService.createUtilisateur(utilisateurDTO);
            return Response.created().setPayload(dto).setMessage("Utilisateur créé avec succès");
        } catch (RuntimeException ex) {
            if (ex.getMessage().contains("Email")) {
                return Response.duplicateEmail().setMessage(ex.getMessage());
            }
            if (ex.getMessage().contains("téléphone")) {
                return Response.duplicateTelephone().setMessage(ex.getMessage());
            }
            return Response.badRequest().setMessage(ex.getMessage());
        } catch (Exception ex) {
            return Response.exception().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Update utilisateur", description = "This endpoint updates an existing utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateUtilisateur(
            @Parameter(name = "id", description = "The utilisateur id to update")
            @PathVariable("id") Long id,
            @RequestBody UtilisateurDTO utilisateurDTO) {
        utilisateurDTO.setId(id);
        try {
            var dto = utilisateurService.updateUtilisateur(utilisateurDTO);
            return Response.ok().setPayload(dto).setMessage("Utilisateur modifié avec succès");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the utilisateur", description = "This endpoint is used to read utilisateur, it takes input id utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getUtilisateur(
            @Parameter(name = "id", description = "The utilisateur id to retrieve")
            @PathVariable Long id) {
        try {
            var dto = utilisateurService.getUtilisateur(id);
            return Response.ok().setPayload(dto).setMessage("Utilisateur trouvé");
        } catch (Exception ex) {
            return Response.notFound().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read all utilisateurs", description = "It takes input params and returns paginated list of utilisateurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Response<Object> getAllUtilisateurs(
            @RequestParam(required = false) Map<String, String> searchParams,
            Pageable pageable) {
        var page = utilisateurService.getAllUtilisateurs(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Delete the utilisateur", description = "Delete utilisateur, it takes input id utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUtilisateur(@PathVariable("id") Long id) {
        try {
            utilisateurService.deleteUtilisateur(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}