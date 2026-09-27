// FEATURE: Packages - Defining a package
package com.reva.sms.model;

/**
 * FEATURE: Inheritance hierarchies - Second branch of inheritance under Person
 * FEATURE: Super and sub classes - Subclass of Person
 * FEATURE: Polymorphism: dynamic binding - Demonstrates polymorphic dispatch alongside Student
 * 
 * Faculty represents teaching staff members at REVA University.
 * It is included in this project specifically to demonstrate hierarchical inheritance
 * and dynamic method dispatch across disparate Person subtypes.
 * 
 * Course Context: REVA University, B.Sc. (BSTCs), Semester V
 * Java Programming (Units I & II Mini Project)
 */
public class Faculty extends Person {
    // FEATURE: Member access rules - Private field
    private String designation;
    private Department department;
    private String specialization;

    /**
     * FEATURE: Constructors - Parameterized constructor
     * FEATURE: super keyword - super(id, name, age)
     * FEATURE: this reference - Assigning parameters to fields
     */
    public Faculty(String id, String name, int age, String designation, Department department, String specialization) {
        super(id, name, age);
        this.designation = designation;
        this.department = department;
        this.specialization = specialization;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    /**
     * FEATURE: Abstract classes and methods - Concrete implementation of abstract method
     * FEATURE: Method overriding - Custom faculty implementation
     * FEATURE: Member access rules - Accessing protected name and age from Person
     * FEATURE: Formatting output - System.out.printf
     */
    @Override
    public void displayDetails() {
        System.out.println("------------------------------------------------------------");
        System.out.println(" [FACULTY RECORD] Prof. " + this.name);
        System.out.println("------------------------------------------------------------");
        System.out.printf("  %-20s: %s\n", "Faculty ID", getId());
        System.out.printf("  %-20s: %s\n", "Name", this.name);
        System.out.printf("  %-20s: %d years\n", "Age", this.age);
        System.out.printf("  %-20s: %s\n", "Designation", this.designation);
        System.out.printf("  %-20s: %s\n", "Department", this.department.getFullName());
        System.out.printf("  %-20s: %s\n", "Specialization", this.specialization);
        System.out.println("------------------------------------------------------------");
    }

    /**
     * FEATURE: The Object class and its methods - Overriding toString()
     * FEATURE: super keyword - Invoking superclass toString()
     */
    @Override
    public String toString() {
        return "Faculty [" + super.toString() + ", Designation=" + designation + 
               ", Dept=" + department.getCode() + ", Spec=" + specialization + "]";
    }
}
