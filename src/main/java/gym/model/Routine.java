package gym.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity(name="routines")
@Getter
@Setter
public class Routine {
    @Id
    public Long id;

    @Column(nullable = false)
    public Date created_at;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="exercises_routines")
    public Collection<Exercise> excercises;
}
