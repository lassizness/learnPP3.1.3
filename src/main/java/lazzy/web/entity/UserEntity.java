package lazzy.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class UserEntity implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 255)
    private String name;

    @Column(nullable = true)
    private Integer age;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    @Transient
    private Set<RoleEntity> userRoles = new HashSet<>();

    public UserEntity() {
    }
    public UserEntity(String name, int age, String username, String password) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    @Override
    public boolean isAccountNonExpired() {
        //учетная запись пользователя всегда действительна
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // учетная запись пользователя всегда не заблокирована
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // учетные данные пользователя всегда действительны
        return true;
    }

    @Override
    public boolean isEnabled() {
        // учетная запись пользователя всегда включена
        return true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }
 /*   public Set<RoleEntity> getUserRoles() {
        return userRoles.;
    }*/

    public String getUserRoles() {
        return userRoles.stream()
                .map(RoleEntity::getName)
                .collect(Collectors.joining(", "));
    }
    public void setUserRoles(Set<RoleEntity> roles) {
        this.userRoles = roles;
    }

    public void addRole(RoleEntity role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(RoleEntity role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity user = (UserEntity) o;
        return Objects.equals(name, user.getName()) && Objects.equals(age, user.getAge());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

    }



}
