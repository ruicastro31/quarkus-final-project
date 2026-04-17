package ada.caixaverso.repository;

import ada.caixaverso.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    public User findbyEmail(String email) {
        return find("email", email).firstResult();
    }
}
