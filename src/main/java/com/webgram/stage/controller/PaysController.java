package com.webgram.stage.controller;

import com.webgram.stage.model.PaysDTO;
import com.webgram.stage.model.Response;
import com.webgram.stage.services.PaysService;
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
@RequestMapping("pays")
@RequiredArgsConstructor
@CrossOrigin("*")

public class PaysController {

    private final PaysService paysService;

    @Operation(summary = "Create pays", description = "this endpoint takes input pays and saves it")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createPays(@RequestBody PaysDTO paysDTO) {
        try {
            var dto = paysService.createPays(paysDTO);
            return Response.ok().setPayload(dto).setMessage("Pays créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updatePays(@Parameter(name = "id", description = "the pays id to updated") @PathVariable("id") Long id, @RequestBody PaysDTO paysDTO) {
        paysDTO.setId(id);
        try {
            var dto = paysService.updatePays(paysDTO);
            return Response.ok().setPayload(dto).setMessage("pays modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }

    @Operation(summary = "Read the pays", description = "This endpoint is used to read pays, it takes input id pays")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getPays(@Parameter(name = "id", description = "the type pays id to valid") @PathVariable Long id) {
        try {
            var dto = paysService.getPays(id);
            return Response.ok().setPayload(dto).setMessage("pays trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read all Budget", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllPays(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = paysService.getAllPays(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder().number(page.getNumber()).totalElements(page.getTotalElements()).size(page.getSize()).totalPages(page.getTotalPages()).build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }


    @Operation(summary = "delete the pays", description = "Delete pays, it takes input id pays")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePays(@PathVariable("id") Long id) {
        try {
            paysService.deletePays(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
