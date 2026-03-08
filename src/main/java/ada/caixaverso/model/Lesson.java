package ada.caixaverso.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lesson")
public class Lesson{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    protected  Lesson() {
    }
    public Lesson(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

}
