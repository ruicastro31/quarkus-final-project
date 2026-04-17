package ada.caixaverso.runtime;

import ada.caixaverso.model.User;
import ada.caixaverso.service.UserService;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Startup
@ApplicationScoped
public class AdminStartup {

    @Inject
    UserService userService;

    @PostConstruct
    @Transactional
    public void start(){
        User admin = new User();
        admin.name = "admin";
        admin.email = "admin";
        admin.password = "admin";
        admin.role = "ADMIN";

        userService.persistir(admin);
    }
}
