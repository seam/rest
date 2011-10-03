package org.jboss.seam.rest.util;

import org.jboss.solder.reflection.Reflections;

public class Utils {
    /**
     * Find out whether a given class is available on the classpath
     *
     * @param className
     * @return true if and only if the given class can be loaded
     */
    public static boolean isClassAvailable(String className) {
        try {
            Reflections.classForName(className);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
