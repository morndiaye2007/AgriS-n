package com.agri.sen.controller;

import com.agri.sen.model.TransactionDTO;
import com.agri.sen.model.Response;
import com.agri.sen.services.TransactionService;
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
@RequestMapping("transactions")
@RequiredArgsConstructor
@CrossOrigin("*")

public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Create transaction", description = "this endpoint takes input transaction and saves it")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        try {
            var dto = transactionService.createTransaction(transactionDTO);
            return Response.ok().setPayload(dto).setMessage("Transaction créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateTransaction(@Parameter(name = "id", description = "the transaction id to updated") @PathVariable("id") Long id, @RequestBody TransactionDTO transactionDTO) {
        transactionDTO.setId(id);
        try {
            var dto = transactionService.updateTransaction(transactionDTO);
            return Response.ok().setPayload(dto).setMessage("transaction modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }

    @Operation(summary = "Read the transaction", description = "This endpoint is used to read transaction, it takes input id transaction")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getTransaction(@Parameter(name = "id", description = "the type transaction id to valid") @PathVariable Long id) {
        try {
            var dto = transactionService.getTransaction(id);
            return Response.ok().setPayload(dto).setMessage("transaction trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read all Budget", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllTransaction(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = transactionService.getAllTransactions(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder().number(page.getNumber()).totalElements(page.getTotalElements()).size(page.getSize()).totalPages(page.getTotalPages()).build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }


    @Operation(summary = "delete the transaction", description = "Delete transaction, it takes input id transaction")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransaction(@PathVariable("id") Long id) {
        try {
            transactionService.deleteTransaction(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
