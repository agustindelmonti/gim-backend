package gym.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name="routines")
@Getter @Setter
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("day")
    public Collection<RoutineExercise> routineExercises;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private User member;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;
}
