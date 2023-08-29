package com.me.authserver.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.me.authserver.entity.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@JsonAutoDetect
@Getter
@Setter
public class JwtUser implements UserDetails {

  private String username;

  private String password;

  private String firstName;

  private String lastName;

  private String email;

  private Collection<Role> authorities;

  private Set<String> roles = new HashSet<>();

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return AuthorityUtils.commaSeparatedStringToAuthorityList(
        getRoles().stream().map(r -> "ROLE_" + r).collect(Collectors.joining()));
  }

  public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    this.authorities = (Collection<Role>) authorities;
  }

  @Override
  public String toString() {
    return "JwtUser{"
        + "username='"
        + username
        + '\''
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", email='"
        + email
        + '\''
        + '}';
  }
}
