package com.webgram.stage.controller;

import com.webgram.stage.model.LangueDTO;
import com.webgram.stage.model.Response;
import com.webgram.stage.services.LangueService;
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
@RequestMapping("langue")
@RequiredArgsConstructor
@CrossOrigin("*")

public class LangueController {

    private final LangueService langueService;

    @Operation(summary = "Create langue", description = "this endpoint takes input langue and saves it")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createLangue(@RequestBody LangueDTO langueDTO) {
        try {
            var dto = langueService.createLangue(langueDTO);
            return Response.ok().setPayload(dto).setMessage("Langue créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateLangue(@Parameter(name = "id", description = "the langue id to updated") @PathVariable("id") Long id, @RequestBody LangueDTO langueDTO) {
        langueDTO.setId(id);
        try {
            var dto = langueService.updateLangue(langueDTO);
            return Response.ok().setPayload(dto).setMessage("langue modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }

    @Operation(summary = "Read the langue", description = "This endpoint is used to read langue, it takes input id langue")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getLangue(@Parameter(name = "id", description = "the type langue id to valid") @PathVariable Long id) {
        try {
            var dto = langueService.getLangue(id);
            return Response.ok().setPayload(dto).setMessage("langue trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read all Budget", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllLangue(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = langueService.getAllLangue(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder().number(page.getNumber()).totalElements(page.getTotalElements()).size(page.getSize()).totalPages(page.getTotalPages()).build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }


    @Operation(summary = "delete the langue", description = "Delete langue, it takes input id langue")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLangue(@PathVariable("id") Long id) {
        try {
            langueService.deleteLangue(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
