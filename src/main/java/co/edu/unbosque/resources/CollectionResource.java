package co.edu.unbosque.resources;

import co.edu.unbosque.dto.Collections;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import javax.imageio.spi.ServiceRegistry;
import javax.print.attribute.standard.Media;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static co.edu.unbosque.services.ColectionService.createColection;
import static co.edu.unbosque.services.ColectionService.getCollections;

@Path("users/{name}/collections")
public class CollectionResource {
    @Context
    ServletContext context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(){
        try{
            List<Collections> collectionsList = getCollections();
            return Response.ok().entity(collectionsList).build();
        } catch (IOException e){
            return Response.serverError().build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Collections collections){
        String contextPath = context.getRealPath("") + File.separator;

        try{
            collections = createColection(collections.getName(), collections.getCategoria(), collections.getCantidad(), contextPath);

            return Response.created(UriBuilder.fromResource(CollectionResource.class).path(collections.getName()).build()).entity(collections).build();
        } catch (IOException e){
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("name") String name){
        try{
            List<Collections> collections = getCollections();

            Collections collection = collections.stream().filter(u -> u.getName().equals(name)).findFirst().orElse(null);
            if(collection != null){
                return Response.ok().entity(collection).build();
            } else {
                return Response.status(404).entity("Collection not found").build();
            }
        } catch (IOException e){
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/form")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createForm(@FormParam("name") String name, @FormParam("categoria") String categoria, @FormParam("cantidad") String cantidad){
        String contextpath = context.getRealPath("") + File.separator;

        try{
            Collections collections = createColection(name,categoria,cantidad,contextpath);
            return Response.created(UriBuilder.fromResource(CollectionResource.class).path(name).build()).entity(collections).build();
        } catch (IOException e){
            return Response.serverError().build();
        }

    }


}
