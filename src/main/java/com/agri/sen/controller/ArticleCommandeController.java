package com.agri.sen.controller;

import com.agri.sen.model.ArticleCommandeDTO;
import com.agri.sen.model.Response;
import com.agri.sen.services.ArticleCommandeService;
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
@RequestMapping("articleCommandes")
@RequiredArgsConstructor
@CrossOrigin("*")

public class ArticleCommandeController {

    private final ArticleCommandeService articleCommandeService;

    @Operation(summary = "Create articleCommande", description = "this endpoint takes input articleCommande and saves it")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createArticleCommande(@RequestBody ArticleCommandeDTO articleCommandeDTO) {
        try {
            var dto = articleCommandeService.createArticleCommande(articleCommandeDTO);
            return Response.ok().setPayload(dto).setMessage("ArticleCommande créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateArticleCommande(@Parameter(name = "id", description = "the articleCommande id to updated") @PathVariable("id") Long id, @RequestBody ArticleCommandeDTO articleCommandeDTO) {
        articleCommandeDTO.setId(id);
        try {
            var dto = articleCommandeService.updateArticleCommande(articleCommandeDTO);
            return Response.ok().setPayload(dto).setMessage("articleCommande modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }
    @Operation(summary = "Read the articleCommande", description = "This endpoint is used to read articleCommande, it takes input id articleCommande")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getArticleCommande(@Parameter(name = "id", description = "the type articleCommande id to valid") @PathVariable Long id) {
        try {
            var dto = articleCommandeService.getArticleCommande(id);
            return Response.ok().setPayload(dto).setMessage("articleCommande trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }
    @Operation(summary = "Read all Budget", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllArticleCommande(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = articleCommandeService.getAllArticleCommande(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder().number(page.getNumber()).totalElements(page.getTotalElements()).size(page.getSize()).totalPages(page.getTotalPages()).build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
    @Operation(summary = "delete the articleCommande", description = "Delete articleCommande, it takes input id articleCommande")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticleCommande(@PathVariable("id") Long id) {
        try {
            articleCommandeService.deleteArticleCommande(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
