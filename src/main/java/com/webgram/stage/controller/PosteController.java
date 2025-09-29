package com.webgram.stage.controller;

import com.webgram.stage.model.PosteDTO;
import com.webgram.stage.model.Response;
import com.webgram.stage.services.PosteService;
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
@RequestMapping("postes")
@RequiredArgsConstructor
@CrossOrigin("*")

public class PosteController {

    private final PosteService posteService;

    @Operation(summary = "Create poste", description = "this endpoint takes input poste and saves it")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createPoste(@RequestBody PosteDTO posteDTO) {
        try {
            var dto = posteService.createPoste(posteDTO);
            return Response.ok().setPayload(dto).setMessage("Poste créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updatePoste(@Parameter(name = "id", description = "the poste id to updated") @PathVariable("id") Long id, @RequestBody PosteDTO posteDTO) {
        posteDTO.setId(id);
        try {
            var dto = posteService.updatePoste(posteDTO);
            return Response.ok().setPayload(dto).setMessage("poste modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }

    @Operation(summary = "Read the poste", description = "This endpoint is used to read poste, it takes input id poste")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getPoste(@Parameter(name = "id", description = "the type poste id to valid") @PathVariable Long id) {
        try {
            var dto = posteService.getPoste(id);
            return Response.ok().setPayload(dto).setMessage("poste trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read all Budget", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllPoste(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = posteService.getAllPoste(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder().number(page.getNumber()).totalElements(page.getTotalElements()).size(page.getSize()).totalPages(page.getTotalPages()).build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }


    @Operation(summary = "delete the poste", description = "Delete poste, it takes input id poste")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePoste(@PathVariable("id") Long id) {
        try {
            posteService.deletePoste(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
