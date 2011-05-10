package org.jboss.seam.rest.examples.tasks.resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.rest.examples.tasks.entity.Category;

/**
 * Common methods for entity resources
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public abstract class AbstractEntityResource {
    @PersistenceContext
    protected EntityManager em;

    /**
     * Return category based on its name.
     */
    protected Category loadCategory(String name) {
        return (Category) em.createNamedQuery("categoryByName").setParameter("category", name).getSingleResult();
    }
}
