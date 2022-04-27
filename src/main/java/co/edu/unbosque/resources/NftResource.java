package co.edu.unbosque.resources;

import co.edu.unbosque.dto.Nft;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import javax.servlet.ServletContext;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static co.edu.unbosque.services.NftService.createPiece;
import static co.edu.unbosque.services.NftService.getPieces;


@Path("users/{username}/pieces")
public class NftResource {
    @Context
    ServletContext context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(){
        try{
            List<Nft> nfts = getPieces();
            return Response.ok().entity(nfts).build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Nft nft){
        String contextPath = context.getRealPath("")+ File.separator;

        try{
            nft = createPiece(nft.getTitle(), nft.getAuthor(), nft.getPath(), nft.getCoins(), contextPath);

            return Response.created(UriBuilder.fromResource(NftResource.class).path(nft.getPath()).build()).entity(nft).build();
        } catch (IOException e){
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("title") String title){
        try{
            List<Nft> nfts = getPieces();

            Nft nft = nfts.stream().filter(u -> u.getTitle().equals(title)).findFirst().orElse(null);

            if(nft!= null){
                return Response.ok().entity(nft).build();
            } else {
                return Response.status(404).entity("Piece nor found").build();
            }
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/pieces/form/post")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createForm(@FormParam("title") String title, @FormParam("author") String author, @FormParam("path") String path, @FormParam("fcoins") String fcoins){
        String contextPath = context.getRealPath("") + File.separator;

        try{
            Nft nft = createPiece(title,author,path,fcoins, contextPath);
            return Response.created(UriBuilder.fromResource(NftResource.class).path(title).build()).entity(nft).build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }
}
