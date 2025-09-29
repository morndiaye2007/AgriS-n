package com.webgram.stage.controller;

import com.webgram.stage.model.DepartementDTO;
import com.webgram.stage.model.Response;
import com.webgram.stage.services.DepartementService;
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
@RequestMapping("departements")
@RequiredArgsConstructor
@CrossOrigin("*")

public class DepartementController {

    private final DepartementService departementService;

    @Operation(summary = "Create departement", description = "this endpoint takes input departement and saves it")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createDepartement(@RequestBody DepartementDTO departementDTO) {
        try {
            var dto = departementService.createDepartement(departementDTO);
            return Response.ok().setPayload(dto).setMessage("Departement créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateDepartement(@Parameter(name = "id", description = "the departement id to updated") @PathVariable("id") Long id, @RequestBody DepartementDTO departementDTO) {
        departementDTO.setId(id);
        try {
            var dto = departementService.updateDepartement(departementDTO);
            return Response.ok().setPayload(dto).setMessage("departement modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }

    @Operation(summary = "Read the departement", description = "This endpoint is used to read departement, it takes input id departement")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getDepartement(@Parameter(name = "id", description = "the type departement id to valid") @PathVariable Long id) {
        try {
            var dto = departementService.getDepartement(id);
            return Response.ok().setPayload(dto).setMessage("departement trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read all Budget", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllDepartements(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = departementService.getAllDepartement(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder().number(page.getNumber()).totalElements(page.getTotalElements()).size(page.getSize()).totalPages(page.getTotalPages()).build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }


    @Operation(summary = "delete the departement", description = "Delete departement, it takes input id departement")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartement(@PathVariable("id") Long id) {
        try {
            departementService.deleteDepartement(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
