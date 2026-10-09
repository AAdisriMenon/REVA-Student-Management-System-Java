package com.reva.sms.model;

import java.util.Objects;

public abstract class Person {
    private final String id;

    protected String name;
    protected int age;

    public static final int MIN_AGE = 16;
    public static final int MAX_AGE = 100;
    public static final int DEFAULT_AGE = 18;

    private static int totalPersonInstances = 0;

    public Person(String id, String name, int age) {
        this.id = (id != null && !id.trim().isEmpty()) ? id.trim() : "PERS-000";
        this.name = (name != null) ? name.trim() : "Unknown";
        if (age >= MIN_AGE && age <= MAX_AGE) {
            this.age = age;
        } else {
            this.age = DEFAULT_AGE;
        }
        totalPersonInstances++;
    }

    public Person() {
        this("PERS-DEF", "Anonymous", DEFAULT_AGE);
    }

    public final String getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= MIN_AGE && age <= MAX_AGE) {
            this.age = age;
        }
    }

    public static int getTotalPersonInstances() {
        return totalPersonInstances;
    }

    public abstract void displayDetails();

    @Override
    public String toString() {
        return "Person [ID=" + id + ", Name=" + name + ", Age=" + age + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return id.equals(person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
