package fish.payara.api;

import fish.payara.util.JWTUtils;
import io.jsonwebtoken.Claims;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("secured")
public class SecuredResource {

    @GET
    @Path("/hi-with-token")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getSecuredHello(@HeaderParam("Authorization") String authHeader) {
        if (authHeader == null || authHeader.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Include a token into the Header")
                    .build();
        }

        String token = authHeader.substring("Bearer".length()).trim();
        Claims claims = JWTUtils.decodeToken(token);
        String username = claims.getSubject();
        return Response.ok("Hey, " + username + "! You are connected to a secured endpoint.")
                .build();
    }
}