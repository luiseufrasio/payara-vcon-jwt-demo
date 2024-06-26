package fish.payara.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("secured")
public class SecuredResource {

    @GET
    @Path("/hi-with-token")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUnsecuredHello() {
        return "Hey! This is an unsecured endpoint.";
    }
}