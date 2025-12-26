package com.agri.sen.controller;


import com.agri.sen.model.NotificationDTO;
import com.agri.sen.model.Response;
import com.agri.sen.services.NotificationService;
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
@RequestMapping("notifications")
@RequiredArgsConstructor
@CrossOrigin("*")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "Create notification", description = "This endpoint takes input notification and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createNotification(@RequestBody NotificationDTO notificationDTO) {
        try {
            var dto = notificationService.createNotification(notificationDTO);
            return Response.created().setPayload(dto).setMessage("Notification créée avec succès");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Get current user notifications", description = "Returns notifications of the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/mes-notifications")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getCurrentUserNotifications(
            @RequestParam(required = false) Map<String, String> searchParams,
            Pageable pageable) {
        var page = notificationService.getCurrentUserNotifications(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Get unread notifications", description = "Returns unread notifications of the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/non-lues")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getUnreadNotifications(Pageable pageable) {
        var page = notificationService.getUnreadNotifications(pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Count unread notifications", description = "Returns count of unread notifications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/non-lues/count")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> countUnreadNotifications() {
        try {
            Long count = notificationService.countUnreadNotifications();
            return Response.ok().setPayload(count).setMessage("Nombre de notifications non lues");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Mark notification as read", description = "Marks a notification as read")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PatchMapping("/{id}/marquer-lue")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> markAsRead(@PathVariable Long id) {
        try {
            notificationService.markAsRead(id);
            return Response.ok().setMessage("Notification marquée comme lue");
        } catch (RuntimeException ex) {
            if (ex.getMessage().contains("autorisé")) {
                return Response.accessDenied().setMessage(ex.getMessage());
            }
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Mark all notifications as read", description = "Marks all user notifications as read")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PatchMapping("/marquer-toutes-lues")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> markAllAsRead() {
        try {
            notificationService.markAllAsRead();
            return Response.ok().setMessage("Toutes les notifications marquées comme lues");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the notification", description = "This endpoint is used to read notification, it takes input id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getNotification(
            @Parameter(name = "id", description = "The notification id to retrieve")
            @PathVariable Long id) {
        try {
            var dto = notificationService.getNotification(id);
            return Response.ok().setPayload(dto).setMessage("Notification trouvée");
        } catch (Exception ex) {
            return Response.notFound().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read all notifications", description = "It takes input params and returns paginated list of notifications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllNotifications(
            @RequestParam(required = false) Map<String, String> searchParams,
            Pageable pageable) {
        var page = notificationService.getAllNotifications(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Delete the notification", description = "Delete notification, it takes input id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Resource does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotification(@PathVariable("id") Long id) {
        try {
            notificationService.deleteNotification(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
