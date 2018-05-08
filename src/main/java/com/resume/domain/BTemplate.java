package com.resume.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BTemplate.
 */
@Entity
@Table(name = "b_template")
public class BTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "description")
    private String description;

    @Column(name = "click_count")
    private Long clickCount;

    @Column(name = "price")
    private String price;

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

    @OneToOne
    @JoinColumn(unique = true)
    private BResume resume;

    @ManyToMany
    @JoinTable(name = "btemplate_classify",
               joinColumns = @JoinColumn(name="btemplates_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="classifies_id", referencedColumnName="id"))
    private Set<BTemplateClassify> classifies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public BTemplate url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public BTemplate description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public BTemplate clickCount(Long clickCount) {
        this.clickCount = clickCount;
        return this;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public String getPrice() {
        return price;
    }

    public BTemplate price(String price) {
        this.price = price;
        return this;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getType() {
        return type;
    }

    public BTemplate type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExtra() {
        return extra;
    }

    public BTemplate extra(String extra) {
        this.extra = extra;
        return this;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public BTemplate isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public BTemplate createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public BTemplate updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public BResume getResume() {
        return resume;
    }

    public BTemplate resume(BResume bResume) {
        this.resume = bResume;
        return this;
    }

    public void setResume(BResume bResume) {
        this.resume = bResume;
    }

    public Set<BTemplateClassify> getClassifies() {
        return classifies;
    }

    public BTemplate classifies(Set<BTemplateClassify> bTemplateClassifies) {
        this.classifies = bTemplateClassifies;
        return this;
    }

    public BTemplate addClassify(BTemplateClassify bTemplateClassify) {
        this.classifies.add(bTemplateClassify);
        bTemplateClassify.getTemplates().add(this);
        return this;
    }

    public BTemplate removeClassify(BTemplateClassify bTemplateClassify) {
        this.classifies.remove(bTemplateClassify);
        bTemplateClassify.getTemplates().remove(this);
        return this;
    }

    public void setClassifies(Set<BTemplateClassify> bTemplateClassifies) {
        this.classifies = bTemplateClassifies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BTemplate bTemplate = (BTemplate) o;
        if (bTemplate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bTemplate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BTemplate{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            ", description='" + getDescription() + "'" +
            ", clickCount='" + getClickCount() + "'" +
            ", price='" + getPrice() + "'" +
            ", type='" + getType() + "'" +
            ", extra='" + getExtra() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
