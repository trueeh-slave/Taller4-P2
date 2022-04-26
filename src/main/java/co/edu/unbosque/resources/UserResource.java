package co.edu.unbosque.resources;

import co.edu.unbosque.dto.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static co.edu.unbosque.services.UserService.*;


@Path("/users")
public class UserResource {

    @Context
    ServletContext context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        try {
            List<User> users = getUsers();

            return Response.ok().entity(users).build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(User user) {
        String contextPath = context.getRealPath("") + File.separator;

        try {
            user = createUser(user.getUsername(), user.getPassword(), user.getRole(), contextPath);

            return Response.created(UriBuilder.fromResource(UserResource.class).path(user.getUsername()).build()).entity(user).build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("username") String username) {
        try {
            List<User> users = getUsers();

            User user = users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);

            if (user != null) {
                return Response.ok().entity(user).build();
            } else {
                return Response.status(404).entity("User not found").build();
            }
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/form")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createForm(@FormParam("username") String username, @FormParam("password") String password, @FormParam("role") String role) {
        String contextPath = context.getRealPath("") + File.separator;

        try {
            User user = createUser(username, password, role, contextPath);
            return Response.created(UriBuilder.fromResource(UserResource.class).path(username).build()).entity(user).build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }
}
