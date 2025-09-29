package com.webgram.stage.controller;
import com.webgram.stage.model.FilieresSuiviDTO;
import com.webgram.stage.model.Response;
import com.webgram.stage.services.FilieresSuiviService;
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
@RequestMapping("filieres-suivi")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FilieresSuiviController {

    private final FilieresSuiviService filieresSuiviService;

    @Operation(summary = "Create filière suivi", description = "This endpoint takes input filiereSuivi and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createFilieresSuivi(@RequestBody FilieresSuiviDTO dto) {
        try {
            var saved = filieresSuiviService.createFilieresSuivi(dto);
            return Response.ok().setPayload(saved).setMessage("Filière suivi créée");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateFilieresSuivi(
            @Parameter(name = "id", description = "the filiereSuivi id to update")
            @PathVariable("id") Long id,
            @RequestBody FilieresSuiviDTO dto) {
        dto.setId(id);
        try {
            var updated = filieresSuiviService.updateFilieresSuivi(dto);
            return Response.ok().setPayload(updated).setMessage("Filière suivi modifiée");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read a filière suivi", description = "This endpoint is used to read filiereSuivi by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getFilieresSuivi(
            @Parameter(name = "id", description = "the filiereSuivi id to fetch")
            @PathVariable Long id) {
        try {
            var dto = filieresSuiviService.getFilieresSuivi(id);
            return Response.ok().setPayload(dto).setMessage("Filière suivi trouvée");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read all filières suivi", description = "Get paginated list of filieresSuivi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllFilieresSuivi(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = filieresSuiviService.getAllFilieresSuivi(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Delete filière suivi", description = "Delete a filiereSuivi by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilieresSuivi(@PathVariable("id") Long id) {
        filieresSuiviService.deleteFilieresSuivi(id);
    }
}
