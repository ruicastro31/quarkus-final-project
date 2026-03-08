package ada.caixaverso.repository;

import ada.caixaverso.model.Lesson;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LessonRepository implements PanacheRepository<Lesson> {
}
