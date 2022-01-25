package gym.model;

import javax.persistence.*;

@Entity
public class Rol {
    public static final Long ADMINISTRADOR_ID = 1L;
    public static final Long CLIENTE_ID = 2L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(nullable = false)
    public String nombre;
}
