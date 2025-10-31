package com.agri.sen.controller;

import com.agri.sen.model.AnalyseDTO;
import com.agri.sen.model.Response;
import com.agri.sen.services.AnalyseService;
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
@RequestMapping("analyses")
@RequiredArgsConstructor
@CrossOrigin("*")

public class AnalyseController {

    private final AnalyseService analyseService;

    @Operation(summary = "Create analyse", description = "this endpoint takes input analyse and saves it")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createAnalyse(@RequestBody AnalyseDTO analyseDTO) {
        try {
            var dto = analyseService.createAnalyse(analyseDTO);
            return Response.ok().setPayload(dto).setMessage("Analyse créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateAnalyse(@Parameter(name = "id", description = "the analyse id to updated") @PathVariable("id") Long id, @RequestBody AnalyseDTO analyseDTO) {
        analyseDTO.setId(id);
        try {
            var dto = analyseService.updateAnalyse(analyseDTO);
            return Response.ok().setPayload(dto).setMessage("analyse modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }

    @Operation(summary = "Read the analyse", description = "This endpoint is used to read analyse, it takes input id analyse")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAnalyse(@Parameter(name = "id", description = "the type analyse id to valid") @PathVariable Long id) {
        try {
            var dto = analyseService.getAnalyse(id);
            return Response.ok().setPayload(dto).setMessage("analyse trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }
//
//    @Operation(summary = "Read all Budget", description = "It takes input param of the page and returns this list related")
//    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @GetMapping("/all")
//    @ResponseStatus(HttpStatus.OK)
//    public Response<Object> getAllAnalyses(@RequestParam Map<String, String> searchParams, Pageable pageable) {
//        var page = analyseService.getAllAnalyses(searchParams, pageable);
//        Response.PageMetadata metadata = Response.PageMetadata.builder().number(page.getNumber()).totalElements(page.getTotalElements()).size(page.getSize()).totalPages(page.getTotalPages()).build();
//        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
//    }
//

    @Operation(summary = "delete the analyse", description = "Delete analyse, it takes input id analyse")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnalyse(@PathVariable("id") Long id) {
        try {
            analyseService.deleteAnalyse(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
