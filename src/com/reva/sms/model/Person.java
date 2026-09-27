// FEATURE: Packages - Defining a package
package com.reva.sms.model;

import java.util.Objects;

/**
 * FEATURE: Abstract classes and methods - Cannot be instantiated directly
 * FEATURE: Inheritance hierarchies - Super class of Student and Faculty
 * FEATURE: Super and sub classes
 * FEATURE: Data abstraction - High-level common representation of a University member
 * FEATURE: Encapsulation - Controlled access via access modifiers
 * 
 * Course Context: REVA University, B.Sc. (BSTCs), Semester V
 * Java Programming (Units I & II Mini Project)
 */
public abstract class Person {
    // FEATURE: Member access rules - private member accessible only inside this class
    // FEATURE: Variables and constants - final variable: must be initialized and cannot be changed
    // FEATURE: Scope and lifetime of variables - instance variable: persists for object lifetime
    private final String id;

    // FEATURE: Member access rules - protected members accessible by derived subclasses in any package
    // FEATURE: Data types - String (reference type) and int (primitive 32-bit signed integer)
    protected String name;
    protected int age;

    // FEATURE: Variables and constants - Public static final age boundary constants
    public static final int MIN_AGE = 16;
    public static final int MAX_AGE = 100;
    public static final int DEFAULT_AGE = 18;

    // FEATURE: Static fields and methods - Static counter shared across all Person instances
    private static int totalPersonInstances = 0;

    /**
     * FEATURE: Constructors - Parameterized constructor
     * FEATURE: this reference - Resolving variable shadowing between parameter and field
     */
    public Person(String id, String name, int age) {
        // FEATURE: Operators, operator hierarchy, expressions - Ternary operator and null-safety
        this.id = (id != null && !id.trim().isEmpty()) ? id.trim() : "PERS-000";
        this.name = (name != null) ? name.trim() : "Unknown";
        // FEATURE: Control flow statements - Consistent age validation across constructor and setter
        if (age >= MIN_AGE && age <= MAX_AGE) {
            this.age = age;
        } else {
            this.age = DEFAULT_AGE; // Default fallback age
        }
        totalPersonInstances++;
    }

    /**
     * FEATURE: Constructors - Default / overloaded constructor
     * FEATURE: this reference - Constructor chaining using this(...)
     */
    public Person() {
        this("PERS-DEF", "Anonymous", DEFAULT_AGE);
    }

    // FEATURE: Preventing inheritance: final classes and methods - final method cannot be overridden by subclasses
    public final String getId() {
        return this.id;
    }

    // FEATURE: Methods - Getters and setters (Encapsulation)
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
        // Enforces identical age validation rule as constructor
        if (age >= MIN_AGE && age <= MAX_AGE) {
            this.age = age;
        }
    }

    // FEATURE: Static fields and methods - Static method returning total count
    public static int getTotalPersonInstances() {
        return totalPersonInstances;
    }

    /**
     * FEATURE: Abstract classes and methods - Abstract method
     * Subclasses MUST provide concrete implementation (Dynamic Binding).
     */
    public abstract void displayDetails();

    /**
     * FEATURE: The Object class and its methods - Overriding toString()
     * FEATURE: Method overriding - Providing subclass-specific behavior
     */
    @Override
    public String toString() {
        return "Person [ID=" + id + ", Name=" + name + ", Age=" + age + "]";
    }

    /**
     * FEATURE: The Object class and its methods - Overriding equals(Object obj)
     * FEATURE: Type conversion and casting - Downcasting Object to Person
     * Note: Uses getClass() != obj.getClass() for symmetric type equality across class hierarchies.
     */
    @Override
    public boolean equals(Object obj) {
        // FEATURE: Jump statements - return
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj; // Type casting
        // FEATURE: Exploring String class - equals()
        return id.equals(person.id);
    }

    /**
     * FEATURE: The Object class and its methods - Overriding hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
