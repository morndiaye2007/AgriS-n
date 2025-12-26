package com.agri.sen.controller;

import com.agri.sen.model.JournalEntryDTO;
import com.agri.sen.model.Response;
import com.agri.sen.services.JournalEntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("journal")
@RequiredArgsConstructor
@CrossOrigin("*")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;

    @Operation(summary = "Create journal entry", description = "This endpoint takes input journal entry and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createEntry(@RequestBody JournalEntryDTO journalEntryDTO) {
        try {
            var dto = journalEntryService.createEntry(journalEntryDTO);
            return Response.created().setPayload(dto).setMessage("Activité enregistrée avec succès");
        } catch (RuntimeException ex) {
            if (ex.getMessage().contains("autorisé")) {
                return Response.accessDenied().setMessage(ex.getMessage());
            }
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Update journal entry", description = "This endpoint updates an existing journal entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Resource does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateEntry(
            @Parameter(name = "id", description = "The journal entry id to update")
            @PathVariable("id") Long id,
            @RequestBody JournalEntryDTO journalEntryDTO) {
        journalEntryDTO.setId(id);
        try {
            var dto = journalEntryService.updateEntry(journalEntryDTO);
            return Response.ok().setPayload(dto).setMessage("Activité modifiée avec succès");
        } catch (RuntimeException ex) {
            if (ex.getMessage().contains("autorisé")) {
                return Response.accessDenied().setMessage(ex.getMessage());
            }
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the journal entry", description = "This endpoint is used to read journal entry, it takes input id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getEntry(
            @Parameter(name = "id", description = "The journal entry id to retrieve")
            @PathVariable Long id) {
        try {
            var dto = journalEntryService.getEntry(id);
            return Response.ok().setPayload(dto).setMessage("Activité trouvée");
        } catch (Exception ex) {
            return Response.notFound().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Get current user entries", description = "Returns journal entries of the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/mes-activites")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getCurrentUserEntries(
            @RequestParam(required = false) Map<String, String> searchParams,
            Pageable pageable) {
        var page = journalEntryService.getCurrentUserEntries(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Get entries by parcelle", description = "Returns journal entries for a specific parcelle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/parcelle/{parcelleId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getEntriesByParcelle(
            @PathVariable Long parcelleId,
            @RequestParam(required = false) Map<String, String> searchParams,
            Pageable pageable) {
        var page = journalEntryService.getEntriesByParcelle(parcelleId, searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Get total cost", description = "Returns total cost for a parcelle in a date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/parcelle/{parcelleId}/cout-total")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getTotalCost(
            @PathVariable Long parcelleId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            Double total = journalEntryService.getTotalCost(parcelleId, startDate, endDate);
            return Response.ok().setPayload(total).setMessage("Coût total calculé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read all journal entries", description = "It takes input params and returns paginated list of journal entries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllEntries(
            @RequestParam(required = false) Map<String, String> searchParams,
            Pageable pageable) {
        var page = journalEntryService.getAllEntries(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Delete the journal entry", description = "Delete journal entry, it takes input id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Resource does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEntry(@PathVariable("id") Long id) {
        try {
            journalEntryService.deleteEntry(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
