package com.agri.sen.controller;

import com.agri.sen.model.CultureDTO;
import com.agri.sen.model.Response;
import com.agri.sen.services.CultureService;
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
@RequestMapping("cultures")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CultureController {

    private final CultureService cultureService;

    @Operation(summary = "Create culture", description = "This endpoint takes input culture and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createCulture(@RequestBody CultureDTO cultureDTO) {
        try {
            var dto = cultureService.createCulture(cultureDTO);
            return Response.ok().setPayload(dto).setMessage("Culture créée avec succès");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Update culture", description = "This endpoint updates an existing culture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateCulture(
            @Parameter(name = "id", description = "The culture id to update")
            @PathVariable("id") Long id,
            @RequestBody CultureDTO cultureDTO) {
        cultureDTO.setId(id);
        try {
            var dto = cultureService.updateCulture(cultureDTO);
            return Response.ok().setPayload(dto).setMessage("Culture modifiée avec succès");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the culture", description = "This endpoint is used to read culture, it takes input id culture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getCulture(
            @Parameter(name = "id", description = "The culture id to retrieve")
            @PathVariable Long id) {
        try {
            var dto = cultureService.getCulture(id);
            return Response.ok().setPayload(dto).setMessage("Culture trouvée");
        } catch (Exception ex) {
            return Response.notFound().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read all cultures", description = "It takes input params and returns paginated list of cultures")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllCultures(
            @RequestParam(required = false) Map<String, String> searchParams,
            Pageable pageable) {
        var page = cultureService.getAllCultures(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Delete the culture", description = "Delete culture, it takes input id culture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCulture(@PathVariable("id") Long id) {
        try {
            cultureService.deleteCulture(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}