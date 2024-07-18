package com.mehmetnuri.exception;

import com.mehmetnuri.common.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        ErrorResponse errorResponse;
        List<String> errors = Collections.singletonList(exception.getMessage());

        if (exception instanceof jakarta.ws.rs.NotFoundException) {
            errorResponse = new ErrorResponse(
                    UUID.randomUUID().toString(),
                    "Resource not found",
                    Response.Status.NOT_FOUND.getStatusCode(),
                    errors
            );
            return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
        } else if (exception instanceof jakarta.ws.rs.InternalServerErrorException) {
            errorResponse = new ErrorResponse(
                    UUID.randomUUID().toString(),
                    "Internal server error",
                    Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    errors
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        } else {
            errorResponse = new ErrorResponse(
                    UUID.randomUUID().toString(),
                    "Unknown error",
                    Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    errors
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }
}
