package ada.caixaverso.repository;

import ada.caixaverso.model.Course;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CourseRepository implements PanacheRepository<Course> {
}
