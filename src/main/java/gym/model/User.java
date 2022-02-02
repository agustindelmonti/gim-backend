package gym.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
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

	@ManyToMany(fetch = FetchType.EAGER)
	public Collection<Role> roles = new ArrayList<>();
}
