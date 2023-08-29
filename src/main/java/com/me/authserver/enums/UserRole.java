package com.me.authserver.enums;

public enum UserRole {
  ADMIN(1),
  USER(2);

  private final Integer roleId;

  private UserRole(Integer roleId) {
    this.roleId = roleId;
  }

  public Integer getRoleId() {
    return roleId;
  }
}
