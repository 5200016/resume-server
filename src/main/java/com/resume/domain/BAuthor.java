package com.resume.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A BAuthor.
 */
@Entity
@Table(name = "b_author")
public class BAuthor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "write_count")
    private Integer writeCount;

    @Lob
    @Column(name = "introduction")
    private String introduction;

    @Lob
    @Column(name = "qualifications")
    private String qualifications;

    @Lob
    @Column(name = "protection")
    private String protection;

    @Column(name = "phone")
    private String phone;

    @Column(name = "response_time")
    private String responseTime;

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

    public BAuthor username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public BAuthor likeCount(Integer likeCount) {
        this.likeCount = likeCount;
        return this;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getWriteCount() {
        return writeCount;
    }

    public BAuthor writeCount(Integer writeCount) {
        this.writeCount = writeCount;
        return this;
    }

    public void setWriteCount(Integer writeCount) {
        this.writeCount = writeCount;
    }

    public String getIntroduction() {
        return introduction;
    }

    public BAuthor introduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getQualifications() {
        return qualifications;
    }

    public BAuthor qualifications(String qualifications) {
        this.qualifications = qualifications;
        return this;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getProtection() {
        return protection;
    }

    public BAuthor protection(String protection) {
        this.protection = protection;
        return this;
    }

    public void setProtection(String protection) {
        this.protection = protection;
    }

    public String getPhone() {
        return phone;
    }

    public BAuthor phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public BAuthor responseTime(String responseTime) {
        this.responseTime = responseTime;
        return this;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getExtra() {
        return extra;
    }

    public BAuthor extra(String extra) {
        this.extra = extra;
        return this;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public BAuthor isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public BAuthor createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public BAuthor updateTime(ZonedDateTime updateTime) {
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
        BAuthor bAuthor = (BAuthor) o;
        if (bAuthor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bAuthor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BAuthor{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", likeCount='" + getLikeCount() + "'" +
            ", writeCount='" + getWriteCount() + "'" +
            ", introduction='" + getIntroduction() + "'" +
            ", qualifications='" + getQualifications() + "'" +
            ", protection='" + getProtection() + "'" +
            ", phone='" + getPhone() + "'" +
            ", responseTime='" + getResponseTime() + "'" +
            ", extra='" + getExtra() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
