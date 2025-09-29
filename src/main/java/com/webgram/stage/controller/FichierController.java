package com.webgram.stage.controller;

import com.webgram.stage.model.FichierDTO;
import com.webgram.stage.model.Response;
import com.webgram.stage.services.FichierService;
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
@RequestMapping("fichiers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FichierController {

    private final FichierService fichierService;

    @Operation(summary = "Create fichier", description = "This endpoint uploads a file and saves its information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createFichier(@RequestBody FichierDTO fichierDTO) {
        try {
            var dto = fichierService.createFichier(fichierDTO);
            return Response.ok().setPayload(dto).setMessage("Fichier créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateFichier(@Parameter(name = "id", description = "The fichier ID to update") @PathVariable("id") Long id, @RequestBody FichierDTO fichierDTO) {
        fichierDTO.setId(id);
        try {
            var dto = fichierService.updateFichier(fichierDTO);
            return Response.ok().setPayload(dto).setMessage("Fichier modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getFichier(@Parameter(name = "id", description = "The fichier ID") @PathVariable Long id) {
        try {
            var dto = fichierService.getFichier(id);
            return Response.ok().setPayload(dto).setMessage("Fichier trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllFichiers(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = fichierService.getAllFichiers(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFichier(@PathVariable("id") Long id) {
        try {
            fichierService.deleteFichier(id);
        } catch (Exception e) {
            throw new RuntimeException(e);}
    }
}