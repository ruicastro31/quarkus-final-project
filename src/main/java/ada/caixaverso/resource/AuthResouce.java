package ada.caixaverso.resource;

import ada.caixaverso.dto.AuthRequest;
import ada.caixaverso.dto.AuthResponse;
import ada.caixaverso.model.User;
import ada.caixaverso.repository.UserRepository;
import ada.caixaverso.service.UserService;
import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

@Path("/auth")
public class AuthResouce {

    @Inject
    UserService userService;

    @Inject
    UserRepository userRepository;

    @ConfigProperty(name = "jwt.expires-in")
    Long expiresIn;

    @POST
    @Path("/token")
    public Response token(@Valid AuthRequest authRequest) {

        User user = userRepository.findbyEmail(authRequest.email());

        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String token = Jwt.issuer("https://quarkus.io/issuer").subject(user.email).groups(Set.of(user.role)).expiresIn(Duration.ofSeconds(expiresIn)).sign();

        return Response.ok(new AuthResponse(token, expiresIn)).build();
    }
}
