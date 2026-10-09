package com.reva.sms.model;

public class Faculty extends Person {
    private String designation;
    private Department department;
    private String specialization;

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

    @Override
    public String toString() {
        return "Faculty [" + super.toString() + ", Designation=" + designation +
               ", Dept=" + department.getCode() + ", Spec=" + specialization + "]";
    }
}
