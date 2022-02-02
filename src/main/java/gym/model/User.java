package gym.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@Column(nullable = false, unique = true)
	public String email;

	@Column(nullable = false)
	public String password;

	@Column(nullable = false, unique = true)
	public String nroDoc;

	@ManyToOne(optional = false)
	public Role rol;
}
