package org.jboss.seam.rest.test.validation;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

public class Person {

    @Size(min = 2, max = 20, groups = {PartialValidation.class, Default.class})
    private String firstName;
    @Size(min = 2, max = 20, groups = {PartialValidation.class, Default.class})
    private String surname;
    @Min(value = 18, groups = {PartialValidation.class, Default.class})
    private int age;
    @AssertFalse(groups = {PartialValidation.class, Default.class})
    private boolean zombie;
    @NotNull
    // not validated during partial validation
    private String bio;

    public Person(String firstName, String surname, int age, boolean zombie) {
        this.firstName = firstName;
        this.surname = surname;
        this.age = age;
        this.zombie = zombie;
    }

    public Person(String firstName, String surname, int age, boolean zombie, String bio) {
        this(firstName, surname, age, zombie);
        this.bio = bio;
    }

    public Person() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isZombie() {
        return zombie;
    }

    public void setZombie(boolean zombie) {
        this.zombie = zombie;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
