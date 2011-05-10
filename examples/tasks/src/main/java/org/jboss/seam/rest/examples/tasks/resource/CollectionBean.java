package org.jboss.seam.rest.examples.tasks.resource;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.seam.rest.examples.tasks.entity.Category;
import org.jboss.seam.rest.examples.tasks.entity.Task;

/**
 * Loads collections of Tasks and Categories from database.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@Stateless
public class CollectionBean {
    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    // we could do typesafe queries, but Resin (eclipselink) does not support that ATM
    public List<Category> getCategories(int start, int limit) {
        Query query = em.createNamedQuery("categories");
        applyPaginationParameters(query, start, limit);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    // we could do typesafe queries, but Resin (eclipselink) does not support that ATM
    public List<Task> getTasks(int start, int limit, String status, String categoryName) {
        Query query = null;

        if (categoryName == null) {
            query = em.createNamedQuery("tasks");
        } else {
            query = em.createNamedQuery("tasksByCategory").setParameter("category", categoryName);
        }

        query = applyResolutionParameter(query, status);
        applyPaginationParameters(query, start, limit);
        return query.getResultList();
    }

    /**
     * Modifies query passed as a parameter to filter out tasks based on their status (resolved/unresolved/all)
     */
    protected Query applyResolutionParameter(Query query, String status) {
        if ("resolved".equals(status)) {
            // double assignment as a workaround for HHH-4541
            return query.setParameter("r1", true).setParameter("r2", true);
        } else if ("unresolved".equals(status)) {
            return query.setParameter("r1", false).setParameter("r2", false);
        } else {
            return query.setParameter("r1", true).setParameter("r2", false);
        }
    }

    /**
     * Sets paginatation parameters
     *
     * @param query JPA query
     * @param start the first item
     * @param limit how many items to return (use 0 for unlimited result)
     * @throws IllegalArgumentException if any of the integer parameters is lesser than 0
     */
    protected void applyPaginationParameters(Query query, int start, int limit) {
        query.setFirstResult(start);
        if (limit != 0) {
            query.setMaxResults(limit);
        }
    }
}
