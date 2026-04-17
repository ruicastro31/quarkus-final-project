package ada.caixaverso.service;

import ada.caixaverso.dto.UserRequest;
import ada.caixaverso.model.User;
import ada.caixaverso.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Transactional
    public User createUser(UserRequest userRequest) {
        if (userRepository.findbyEmail(userRequest.email()) != null) {
            throw new IllegalStateException((userRequest.email() + " already exists"));
        }
        User user = new User();
        user.name = userRequest.name();
        user.email = userRequest.email();
        user.password = userRequest.password();
        user.role = "USER";

        userRepository.persist(user);

        return user;
    }

    @Transactional
    public void persistir(User user) {
        userRepository.persist(user);
    }

}
