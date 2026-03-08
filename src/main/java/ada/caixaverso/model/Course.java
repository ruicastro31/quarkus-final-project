package ada.caixaverso.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "course")
public class Course{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany
    private final List<Lesson> lessons = new ArrayList<>();

    protected Course(){
    }

    public Course(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
    }

    public void addLesson(Lesson lesson) {
        Lesson validLesson = Objects.requireNonNull(lesson, "lesson must no be null");
        this.lessons.add(validLesson);
    }

    public List<Lesson> getLessons() {
        return Collections.unmodifiableList(this.lessons); //Lista não pode ser modificada.
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
