package com.me.authserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role implements GrantedAuthority {

  @Id
  @Column(name = "role_id")
  private Integer id;

  @Column(name = "name")
  private String name;

  private String description;

  @Override
  public String getAuthority() {
    return name;
  }

      @Override
      public boolean equals(Object o) {
          if (this == o) return true;
          if (!(o instanceof Role role)) {
              return false;
          }
          return Objects.equals(getId(), role.getId());
      }

    @Override
    public int hashCode() {
      return Objects.hash(getId());
    }
}
