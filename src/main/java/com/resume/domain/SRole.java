package com.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SRole.
 */
@Entity
@Table(name = "s_role")
public class SRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<SLogin> logins = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public SRole roleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public SRole isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public SRole createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public SRole updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Set<SLogin> getLogins() {
        return logins;
    }

    public SRole logins(Set<SLogin> sLogins) {
        this.logins = sLogins;
        return this;
    }

    public SRole addLogin(SLogin sLogin) {
        this.logins.add(sLogin);
        sLogin.getRoles().add(this);
        return this;
    }

    public SRole removeLogin(SLogin sLogin) {
        this.logins.remove(sLogin);
        sLogin.getRoles().remove(this);
        return this;
    }

    public void setLogins(Set<SLogin> sLogins) {
        this.logins = sLogins;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SRole sRole = (SRole) o;
        if (sRole.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sRole.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SRole{" +
            "id=" + getId() +
            ", roleName='" + getRoleName() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
