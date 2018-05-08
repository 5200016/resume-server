package com.resume.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A BWorkProject.
 */
@Entity
@Table(name = "b_work_project")
public class BWorkProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "responsible")
    private String responsible;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "extra")
    private String extra;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @ManyToOne
    private BWork bWork;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BWorkProject name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponsible() {
        return responsible;
    }

    public BWorkProject responsible(String responsible) {
        this.responsible = responsible;
        return this;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getStartTime() {
        return startTime;
    }

    public BWorkProject startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public BWorkProject endTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getExtra() {
        return extra;
    }

    public BWorkProject extra(String extra) {
        this.extra = extra;
        return this;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public BWorkProject isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public BWorkProject createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public BWorkProject updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public BWork getBWork() {
        return bWork;
    }

    public BWorkProject bWork(BWork bWork) {
        this.bWork = bWork;
        return this;
    }

    public void setBWork(BWork bWork) {
        this.bWork = bWork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BWorkProject bWorkProject = (BWorkProject) o;
        if (bWorkProject.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bWorkProject.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BWorkProject{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", responsible='" + getResponsible() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", extra='" + getExtra() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
