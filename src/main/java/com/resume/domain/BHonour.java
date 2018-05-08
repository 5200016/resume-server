package com.resume.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A BHonour.
 */
@Entity
@Table(name = "b_honour")
public class BHonour implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "extra")
    private String extra;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public BHonour username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public BHonour description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtra() {
        return extra;
    }

    public BHonour extra(String extra) {
        this.extra = extra;
        return this;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public BHonour isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public BHonour createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public BHonour updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BHonour bHonour = (BHonour) o;
        if (bHonour.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bHonour.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BHonour{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", description='" + getDescription() + "'" +
            ", extra='" + getExtra() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
