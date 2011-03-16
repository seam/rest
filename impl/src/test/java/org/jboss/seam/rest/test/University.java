package org.jboss.seam.rest.test;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named
public class University {
    private List<Student> students = Arrays.asList(new Student("A"), new Student("B"), new Student("C"));
    private String name = "Masaryk University";

    public List<Student> getStudents() {
        return students;
    }

    public String getName() {
        return name;
    }
}
