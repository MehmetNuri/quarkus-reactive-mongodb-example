package com.mehmetnuri.resource;

import com.mehmetnuri.common.ErrorResponse;
import com.mehmetnuri.common.PageResult;
import com.mehmetnuri.dto.UserDTO;
import com.mehmetnuri.service.UserService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.Collections;
import java.util.UUID;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger LOGGER = Logger.getLogger(UserResource.class);

    @Inject
    UserService userService;

    @GET
    public Uni<PageResult<UserDTO>> getAllUsers(@QueryParam("page") @DefaultValue("0") int page,
                                                @QueryParam("size") @DefaultValue("10") int size) {
        return userService.getAllUsers(page, size);
    }

    @GET
    @Path("/find/{id}")
    public Uni<Response> getUserById(@PathParam("id") String id) {
        return userService.getUserById(id)
                .map(userDTO -> {
                    if (userDTO != null) {
                        return Response.ok(userDTO).build();
                    } else {
                        ErrorResponse errorResponse = new ErrorResponse(
                                UUID.randomUUID().toString(),
                                "User not found",
                                Response.Status.NOT_FOUND.getStatusCode(),
                                Collections.singletonList("User with id " + id + " not found")
                        );
                        return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
                    }
                });
    }

    @GET
    @Path("/name/{userName}")
    public Uni<Response> getUsersByName(@PathParam("userName") String name,
                                        @QueryParam("page") @DefaultValue("0") int page,
                                        @QueryParam("size") @DefaultValue("10") int size) {
        LOGGER.info("Received request to get users by name: " + name);
        return userService.getUsersByName(name, page, size)
                .map(pageResult -> {
                    if (pageResult.getItems().isEmpty()) {
                        ErrorResponse errorResponse = new ErrorResponse(
                                UUID.randomUUID().toString(),
                                "Users not found",
                                Response.Status.NOT_FOUND.getStatusCode(),
                                Collections.singletonList("Users with name " + name + " not found")
                        );
                        return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
                    } else {
                        return Response.ok(pageResult).build();
                    }
                });
    }


    @GET
    @Path("/age/{age}")
    public Uni<Response> getUsersByAge(@PathParam("age") int age,
                                       @QueryParam("page") @DefaultValue("0") int page,
                                       @QueryParam("size") @DefaultValue("10") int size) {
        return userService.getUsersByAge(age, page, size)
                .map(pageResult -> {
                    if (pageResult.getItems().isEmpty()) {
                        ErrorResponse errorResponse = new ErrorResponse(
                                UUID.randomUUID().toString(),
                                "Users not found",
                                Response.Status.NOT_FOUND.getStatusCode(),
                                Collections.singletonList("Users with age " + age + " not found")
                        );
                        return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
                    } else {
                        return Response.ok(pageResult).build();
                    }
                });
    }

    @GET
    @Path("/email-domain/{domain}")
    public Uni<Response> getUsersByEmailDomain(@PathParam("domain") String domain,
                                               @QueryParam("page") @DefaultValue("0") int page,
                                               @QueryParam("size") @DefaultValue("10") int size) {
        return userService.getUsersByEmailDomain(domain, page, size)
                .map(pageResult -> {
                    if (pageResult.getItems().isEmpty()) {
                        ErrorResponse errorResponse = new ErrorResponse(
                                UUID.randomUUID().toString(),
                                "Users not found",
                                Response.Status.NOT_FOUND.getStatusCode(),
                                Collections.singletonList("Users with email domain " + domain + " not found")
                        );
                        return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
                    } else {
                        return Response.ok(pageResult).build();
                    }
                });
    }

    @POST
    @Path("/create")
    public Uni<UserDTO> createUser(UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PUT
    @Path("/update/{id}")
    public Uni<UserDTO> updateUser(@PathParam("id") String id, UserDTO updatedUserDTO) {
        return userService.updateUser(id, updatedUserDTO);
    }

    @DELETE
    @Path("/delete/{id}")
    public Uni<Response> deleteUser(@PathParam("id") String id) {
        return userService.deleteUser(id)
                .map(deleted -> {
                    if (deleted) {
                        return Response.ok().build();
                    } else {
                        ErrorResponse errorResponse = new ErrorResponse(
                                UUID.randomUUID().toString(),
                                "User not found",
                                Response.Status.NOT_FOUND.getStatusCode(),
                                Collections.singletonList("User with id " + id + " not found")
                        );
                        return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
                    }
                });
    }
}
