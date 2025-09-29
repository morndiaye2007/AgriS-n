package com.webgram.stage.controller;
import com.webgram.stage.entity.enums.StatutType;
import com.webgram.stage.model.AgentDTO;
import com.webgram.stage.model.Response;
import com.webgram.stage.services.AgentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("agents")
@RequiredArgsConstructor
@CrossOrigin("*")

public class AgentController {

    private final AgentService agentService;

    @Operation(summary = "Create agent", description = "this endpoint takes input agent and saves it")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createAgent(@RequestBody AgentDTO agentDTO) {
        try {
            var dto = agentService.createAgent(agentDTO);
            dto.setStatutType(StatutType.TRAITEMENT_ENCOUR);
            return Response.ok().setPayload(dto).setMessage("Agent créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateAgent(@Parameter(name = "id", description = "the agent id to updated") @PathVariable("id") Long id, @RequestBody AgentDTO agentDTO) {
        agentDTO.setId(id);
        try {
            var dto = agentService.updateAgent(agentDTO);
            return Response.ok().setPayload(dto).setMessage("agent modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }

    @Operation(summary = "Read the agent", description = "This endpoint is used to read agent, it takes input id agent")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAgent(@Parameter(name = "id", description = "the type agent id to valid") @PathVariable Long id) {
        try {
            var dto = agentService.getAgent(id);
            return Response.ok().setPayload(dto).setMessage("agent trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read all Budget", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllAgents(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = agentService.getAllAgents(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder().number(page.getNumber()).totalElements(page.getTotalElements()).size(page.getSize()).totalPages(page.getTotalPages()).build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }


    @Operation(summary = "delete the agent", description = "Delete agent, it takes input id agent")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAgent(@PathVariable("id") Long id) {
        try {
            agentService.deleteAgent(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Import agents from list", description = "This endpoint imports a list of agents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })


    @PostMapping("/import")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> importAgents(@RequestBody @Valid List<AgentDTO> agents) {
        if (agents == null || agents.isEmpty()) {
            return Response.badRequest().setMessage("La liste des agents est vide ou nulle");
        }
        try {
            List<AgentDTO> importedAgents = agentService.importAgents(agents);
            return Response.ok()
                    .setPayload(importedAgents)
                    .setMessage("Agents importés avec succès");
        } catch (Exception ex) {
            String detailedMessage = ex.getMessage() != null ? ex.getMessage() : "Erreur lors de la désérialisation ou validation des données";
            if (ex instanceof org.springframework.validation.BindException) {
                detailedMessage = ((org.springframework.validation.BindException) ex).getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error -> error.getField() + ": " + error.getDefaultMessage())
                        .collect(Collectors.joining("; "));
            }
            return Response.badRequest().setMessage("Erreur lors de l'importation: " + detailedMessage);
        }
    }
    @GetMapping("/export")
    @ResponseStatus(HttpStatus.OK)
    public void exportAgents(HttpServletResponse response) {
        try {
            response.setContentType("text/csv; charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"agents.csv\"");
            agentService.exportAgent(response.getWriter());
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'exportation du fichier CSV: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            try {
                response.getWriter().write("{\"message\": \"" + e.getMessage() + "\"}");
            } catch (IOException ex) {
                throw new RuntimeException("Erreur lors de l'envoi de la réponse d'erreur");
            }
        }
    }

    @PutMapping("/{id}/statut")
    @Operation(summary = "Mettre à jour le statut")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statut mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "404", description = "non trouvé")
    })
    public ResponseEntity<AgentDTO> updateStatut(
            @PathVariable Long id,
            @RequestParam StatutType statutType) {
        return ResponseEntity.ok(agentService.updateStatut(id, statutType));
    }

}
