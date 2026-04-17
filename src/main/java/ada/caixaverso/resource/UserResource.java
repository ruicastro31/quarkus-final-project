package ada.caixaverso.resource;

import ada.caixaverso.dto.UserRequest;
import ada.caixaverso.dto.UserResponse;
import ada.caixaverso.model.User;
import ada.caixaverso.repository.UserRepository;
import ada.caixaverso.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Map;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserRepository userRepository;

    @Inject
    UserService userService;

    @Inject
    JsonWebToken jwt;

    @POST
    public Response create(@Valid UserRequest userRequest) {
        try {
            User create = userService.createUser(userRequest);

            UserResponse userResponse = new UserResponse(create.id,  create.name, create.email, create.password, create.role);

            return Response.status(Response.Status.CREATED)
                    .entity(userResponse)
                    .build();
        } catch (IllegalStateException e) {
                return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @GET
    @Path("/me")
    @PermitAll
    public Response me() {
        User user = userRepository.findbyEmail(jwt.getSubject());

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        UserResponse userResponse = new UserResponse(user.id,  user.name, user.email, user.password, user.role);


        return Response.ok(userResponse).build();
    }
}

