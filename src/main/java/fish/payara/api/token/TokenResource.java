package fish.payara.api.token;

import fish.payara.users.User;
import fish.payara.util.JWTUtils;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/token")
public class TokenResource {

    @POST
    @Path("/generate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateToken(TokenRequest request) {
        try {
            // Validate credentials (optional)
            if (request == null || !isValidUser(request.getUsername(), request.getPassword())) {
                throw new UnauthorizedException("Invalid credentials");
            }

            // Generate JWT token
            final User user = new User(request.getUsername(), request.getPassword());
            String token = JWTUtils.generateToken(request.getUsername());

            TokenResponse response = new TokenResponse();
            response.setToken(token);

            // Return success response with the token
            return Response.ok(response).build();
        } catch (UnauthorizedException e) {
            // Return 401 status for invalid credentials
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid credentials")
                    .build();
        }
    }

    private boolean isValidUser(String username, String password) {
        return username != null && password != null && !username.isEmpty() && !password.isEmpty();
    }

    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
    }
}

