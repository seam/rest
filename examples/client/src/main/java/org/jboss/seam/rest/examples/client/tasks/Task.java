package org.jboss.seam.rest.examples.client.tasks;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * JAXB class for Seam Task's task.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@XmlRootElement
public class Task {
    private Long id;
    private String name;
    private Boolean resolved;
    private Date created;
    private Date updated;
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
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @XmlElement(name = "updated")
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

    @XmlElement(name = "category")
    public String getCategoryName() {
        return category.getName();
    }
}
