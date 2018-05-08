package com.resume.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A JobObjective.
 */
@Entity
@Table(name = "job_objective")
public class JobObjective implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "enter_time")
    private String enterTime;

    @Column(name = "salary_start")
    private String salaryStart;

    @Column(name = "salary_end")
    private String salaryEnd;

    @Column(name = "expect_city")
    private String expectCity;

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

    public JobObjective username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public JobObjective jobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobType() {
        return jobType;
    }

    public JobObjective jobType(String jobType) {
        this.jobType = jobType;
        return this;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public JobObjective enterTime(String enterTime) {
        this.enterTime = enterTime;
        return this;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public String getSalaryStart() {
        return salaryStart;
    }

    public JobObjective salaryStart(String salaryStart) {
        this.salaryStart = salaryStart;
        return this;
    }

    public void setSalaryStart(String salaryStart) {
        this.salaryStart = salaryStart;
    }

    public String getSalaryEnd() {
        return salaryEnd;
    }

    public JobObjective salaryEnd(String salaryEnd) {
        this.salaryEnd = salaryEnd;
        return this;
    }

    public void setSalaryEnd(String salaryEnd) {
        this.salaryEnd = salaryEnd;
    }

    public String getExpectCity() {
        return expectCity;
    }

    public JobObjective expectCity(String expectCity) {
        this.expectCity = expectCity;
        return this;
    }

    public void setExpectCity(String expectCity) {
        this.expectCity = expectCity;
    }

    public String getExtra() {
        return extra;
    }

    public JobObjective extra(String extra) {
        this.extra = extra;
        return this;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public JobObjective isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public JobObjective createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public JobObjective updateTime(ZonedDateTime updateTime) {
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
        JobObjective jobObjective = (JobObjective) o;
        if (jobObjective.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobObjective.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobObjective{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", jobTitle='" + getJobTitle() + "'" +
            ", jobType='" + getJobType() + "'" +
            ", enterTime='" + getEnterTime() + "'" +
            ", salaryStart='" + getSalaryStart() + "'" +
            ", salaryEnd='" + getSalaryEnd() + "'" +
            ", expectCity='" + getExpectCity() + "'" +
            ", extra='" + getExtra() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
