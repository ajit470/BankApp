package com.example.bankapp.auth.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, unique=true, length=120)
  private String username;

  @Column(name="password_hash", nullable=false, length=255)
  private String passwordHash;

  @Column(nullable=false)
  private boolean enabled = false;

  @Column(nullable=false)
  private boolean locked = false;

  private LocalDateTime createdAt = LocalDateTime.now();

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name="user_roles",
    joinColumns=@JoinColumn(name="user_id"),
    inverseJoinColumns=@JoinColumn(name="role_id")
  )
  private Set<Role> roles = new HashSet<>();

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }

  public String getPasswordHash() { return passwordHash; }
  public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

  public boolean isEnabled() { return enabled; }
  public void setEnabled(boolean enabled) { this.enabled = enabled; }

  public boolean isLocked() { return locked; }
  public void setLocked(boolean locked) { this.locked = locked; }

  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

  public Set<Role> getRoles() { return roles; }
  public void setRoles(Set<Role> roles) { this.roles = roles; }
}
