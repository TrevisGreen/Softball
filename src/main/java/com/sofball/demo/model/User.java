package com.sofball.demo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})})
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = -8115342892842006915L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "first_name", nullable = false, length = 255)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "last_name", nullable = false, length = 255)
    private String lastName;

    @Size(min = 1, max = 255)
    @Column(name = "phone", length = 255)
    private String phone;

    @NotNull
    @Size(min = 1, max = 255)
    @Email
    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @NotNull
    @Column(name = "account_expired", nullable = false)
    private Boolean accountExpired = false;

    @NotNull
    @Column(name = "account_locked", nullable = false)
    private Boolean accountLocked = false;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @NotNull
    @Column(name = "password_expired", nullable = false)
    private Boolean passwordExpired = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "authority", nullable = false)})
    private List<Role> roles = new ArrayList<>();

    @ManyToOne
    private Team team;

    @Transient
    private Boolean admin = Boolean.FALSE;

    @Transient
    private Boolean coach = Boolean.FALSE;

    @Transient
    private Boolean user = Boolean.TRUE;

    // Default constructor
    public User() {
    }

    // Param constructor
    public User(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        authorities.addAll(getRoles());
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !getAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !getAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isPasswordExpired();
    }

    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(Boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public Boolean getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(Boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        sb.append(firstName);
        sb.append(" ");
        sb.append(lastName);
        return sb.toString();
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getCoach() {
        return coach;
    }

    public void setCoach(Boolean coach) {
        this.coach = coach;
    }

    public Boolean getUser() {
        return user;
    }

    public void setUser(Boolean user) {
        this.user = user;
    }
}
