package com.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BTemplateClassify.
 */
@Entity
@Table(name = "b_template_classify")
public class BTemplateClassify implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_type")
    private Integer type;

    @Column(name = "extra")
    private String extra;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @ManyToMany(mappedBy = "classifies")
    @JsonIgnore
    private Set<BTemplate> templates = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BTemplateClassify name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public BTemplateClassify type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExtra() {
        return extra;
    }

    public BTemplateClassify extra(String extra) {
        this.extra = extra;
        return this;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public BTemplateClassify isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public BTemplateClassify createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public BTemplateClassify updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Set<BTemplate> getTemplates() {
        return templates;
    }

    public BTemplateClassify templates(Set<BTemplate> bTemplates) {
        this.templates = bTemplates;
        return this;
    }

    public BTemplateClassify addTemplate(BTemplate bTemplate) {
        this.templates.add(bTemplate);
        bTemplate.getClassifies().add(this);
        return this;
    }

    public BTemplateClassify removeTemplate(BTemplate bTemplate) {
        this.templates.remove(bTemplate);
        bTemplate.getClassifies().remove(this);
        return this;
    }

    public void setTemplates(Set<BTemplate> bTemplates) {
        this.templates = bTemplates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BTemplateClassify bTemplateClassify = (BTemplateClassify) o;
        if (bTemplateClassify.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bTemplateClassify.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BTemplateClassify{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", extra='" + getExtra() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
