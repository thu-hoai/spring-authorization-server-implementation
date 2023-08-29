package com.me.authserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "t_user")
public class UserEntity extends AbstractAuditingEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  @Column(name = "password_hash")
  private String password;

  @Column(name = "is_enabled")
  private boolean enabled;

  /** Last Password Reset Date */
  @Column(name = "pwd_reset_date")
  private LocalDateTime lastPasswordResetDate;

  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @Column(name = "avatar")
  private byte[] avatar;

  @ManyToMany(
      cascade = {CascadeType.MERGE},
      fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_role",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id")})
  private Set<Role> authorities = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserEntity that)) return false;
    return Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }
}
