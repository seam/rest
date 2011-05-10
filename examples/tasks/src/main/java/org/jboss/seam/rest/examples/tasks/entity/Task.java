package org.jboss.seam.rest.examples.tasks.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.jboss.seam.rest.examples.tasks.json.JsonDateSerializer;

/**
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@Entity
@GroupSequence({Task.class, TaskValidationGroup.class})
@NamedQueries({
        @NamedQuery(name = "taskById", query = "select task from Task task where task.id = :tid"),
        @NamedQuery(name = "taskByCategoryAndId", query = "select task from Task task where task.id = :tid and task.category.name = :category"),
        @NamedQuery(name = "tasks", query = "select task from Task task where task.resolved in (:r1, :r2) order by task.id"),
        @NamedQuery(name = "tasksByCategory", query = "select task from Task task where task.category.name = :category and task.resolved in (:r1, :r2) order by task.id")})
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Size(min = 1, max = 100, groups = TaskValidationGroup.class)
    private String name;
    @NotNull
    private Boolean resolved;
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date updated;
    @ManyToOne
    @NotNull
    private Category category;

    public Task() {
    }

    public Task(String name, Boolean resolved, Date created, Date updated, Category category) {
        this.name = name;
        this.resolved = resolved;
        this.created = created;
        this.updated = updated;
        this.category = category;
    }

    public Task(Long id, String name, Boolean resolved, Date created, Date updated, Category category) {
        this(name, resolved, created, updated, category);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isResolved() {
        return resolved;
    }

    public void setResolved(Boolean resolved) {
        this.resolved = resolved;
    }

    @XmlElement(name = "created")
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @XmlElement(name = "updated")
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @XmlTransient
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Transient
    @XmlElement(name = "category")
    @JsonProperty("category")
    public String getCategoryName() {
        return category.getName();
    }
}
