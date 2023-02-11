package gym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private UserType type = UserType.MEMBER;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore()
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nroDoc;

    private String gender;
    private String phone;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @Column(nullable = false)
    private boolean enabled;
    private boolean credentialsNonExpired;
    private boolean accountNonExpired;
    private boolean accountNonLocked;

    /**
     * Routines assigned to user by other responsible user with admin or employee roles
     */
    @OneToMany(mappedBy = "member", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Routine> assignedRoutines = new LinkedHashSet<>();

    /**
     * Routines created by the user
     * only users with admin or employees roles should have elements
     */
    @OneToMany(mappedBy = "creator", orphanRemoval = true)
    private Set<Routine> createdRoutines = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @OrderBy("paymentDate DESC")
    private List<Payment> payments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<PackageUser> packages = new LinkedHashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
