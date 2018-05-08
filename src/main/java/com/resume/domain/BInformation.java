package com.resume.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A BInformation.
 */
@Entity
@Table(name = "b_information")
public class BInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "sex")
    private String sex;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "education")
    private String education;

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "work_time")
    private String workTime;

    @Column(name = "politics_status")
    private String politicsStatus;

    @Column(name = "marriage")
    private String marriage;

    @Lob
    @Column(name = "skill")
    private String skill;

    @Column(name = "address")
    private String address;

    @Lob
    @Column(name = "introduction")
    private String introduction;

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

    public BInformation username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public BInformation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public BInformation sex(String sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public BInformation birthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEducation() {
        return education;
    }

    public BInformation education(String education) {
        this.education = education;
        return this;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSpeciality() {
        return speciality;
    }

    public BInformation speciality(String speciality) {
        this.speciality = speciality;
        return this;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getWorkTime() {
        return workTime;
    }

    public BInformation workTime(String workTime) {
        this.workTime = workTime;
        return this;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getPoliticsStatus() {
        return politicsStatus;
    }

    public BInformation politicsStatus(String politicsStatus) {
        this.politicsStatus = politicsStatus;
        return this;
    }

    public void setPoliticsStatus(String politicsStatus) {
        this.politicsStatus = politicsStatus;
    }

    public String getMarriage() {
        return marriage;
    }

    public BInformation marriage(String marriage) {
        this.marriage = marriage;
        return this;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getSkill() {
        return skill;
    }

    public BInformation skill(String skill) {
        this.skill = skill;
        return this;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getAddress() {
        return address;
    }

    public BInformation address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntroduction() {
        return introduction;
    }

    public BInformation introduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getExtra() {
        return extra;
    }

    public BInformation extra(String extra) {
        this.extra = extra;
        return this;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public BInformation isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public BInformation createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public BInformation updateTime(ZonedDateTime updateTime) {
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
        BInformation bInformation = (BInformation) o;
        if (bInformation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bInformation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BInformation{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", name='" + getName() + "'" +
            ", sex='" + getSex() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", education='" + getEducation() + "'" +
            ", speciality='" + getSpeciality() + "'" +
            ", workTime='" + getWorkTime() + "'" +
            ", politicsStatus='" + getPoliticsStatus() + "'" +
            ", marriage='" + getMarriage() + "'" +
            ", skill='" + getSkill() + "'" +
            ", address='" + getAddress() + "'" +
            ", introduction='" + getIntroduction() + "'" +
            ", extra='" + getExtra() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
