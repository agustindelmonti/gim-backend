package gym.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    public static final Long ADMIN_ID = 1L;
    public static final Long USER_ID = 2L;

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;
}
