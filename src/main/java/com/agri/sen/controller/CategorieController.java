package com.agri.sen.controller;

import com.agri.sen.model.CategorieDTO;
import com.agri.sen.model.Response;
import com.agri.sen.services.CategorieService;
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
@RequestMapping("categories")
@RequiredArgsConstructor
@CrossOrigin("*")

public class CategorieController {

    private final CategorieService categorieService;

    @Operation(summary = "Create categorie", description = "this endpoint takes input categorie and saves it")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createCategorie(@RequestBody CategorieDTO categorieDTO) {
        try {
            var dto = categorieService.createCategorie(categorieDTO);
            return Response.ok().setPayload(dto).setMessage("Categorie créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateCategorie(@Parameter(name = "id", description = "the categorie id to updated") @PathVariable("id") Long id, @RequestBody CategorieDTO categorieDTO) {
        categorieDTO.setId(id);
        try {
            var dto = categorieService.updateCategorie(categorieDTO);
            return Response.ok().setPayload(dto).setMessage("categorie modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }
    @Operation(summary = "Read the categorie", description = "This endpoint is used to read categorie, it takes input id categorie")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getCategorie(@Parameter(name = "id", description = "the type categorie id to valid") @PathVariable Long id) {
        try {
            var dto = categorieService.getCategorie(id);
            return Response.ok().setPayload(dto).setMessage("categorie trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }
    @Operation(summary = "Read all Budget", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllCategorie(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = categorieService.getAllCategorie(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder().number(page.getNumber()).totalElements(page.getTotalElements()).size(page.getSize()).totalPages(page.getTotalPages()).build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
    @Operation(summary = "delete the categorie", description = "Delete categorie, it takes input id categorie")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategorie(@PathVariable("id") Long id) {
        try {
            categorieService.deleteCategorie(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
