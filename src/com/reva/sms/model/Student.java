// FEATURE: Packages - Defining a package
package com.reva.sms.model;

import java.util.Arrays;

/**
 * FEATURE: Super and sub classes - Subclass extending abstract superclass Person
 * FEATURE: Implement interfaces - Implementing Payable (which in turn extends Reportable)
 * FEATURE: Multiple inheritance through interfaces
 * FEATURE: Encapsulation - Private fields with controlled public getters/setters
 * 
 * Course Context: REVA University, B.Sc. (BSTCs), Semester V
 * Java Programming (Units I & II Mini Project)
 */
public class Student extends Person implements Payable {
    // FEATURE: Member access rules - Private fields strictly encapsulated within Student
    // FEATURE: Data types - String reference type
    private String srn;
    
    // FEATURE: Enumerated types - Department enum field
    private Department department;

    // FEATURE: Data types - byte (8-bit integer, ideal for academic semester 1-8)
    private byte semester;

    // FEATURE: Arrays - Single-dimensional primitive integer array storing marks for 5 subjects
    private int[] marks;

    // FEATURE: Data types - double (64-bit precision for currency / fee balance)
    private double feeDue;

    // FEATURE: Data types - char (16-bit Unicode character for letter grade 'S', 'A', 'B', 'C', 'F')
    private char grade;

    // FEATURE: Data types - boolean (true/false flag for hostel accommodation)
    private boolean isHosteller;

    // FEATURE: Data types - long (64-bit integer for unique admission sequence number)
    private long admissionNumber;

    // FEATURE: Data types - float (32-bit floating point for scholarship discount percentage)
    private float scholarshipPercent;

    // FEATURE: Data types - short (16-bit integer for enrolled credit hours)
    private short totalCredits;

    // FEATURE: Static fields and methods - Static counter for auto-generating unique student IDs & admission numbers
    private static int studentCounter = 1000;
    private static long admissionSequence = 2026000000L;

    // FEATURE: Operators - Bitwise flag constants using bit-shift operator (<<)
    public static final int FLAG_TUITION_CLEARED = 1 << 0; // 1 (0001)
    public static final int FLAG_HOSTELLER        = 1 << 1; // 2 (0010)
    public static final int FLAG_SCHOLARSHIP      = 1 << 2; // 4 (0100)
    public static final int FLAG_DEAN_LIST        = 1 << 3; // 8 (1000)

    /**
     * FEATURE: Constructors - Full parameterized constructor
     * FEATURE: super keyword - Calling superclass (Person) constructor: super(id, name, age)
     * FEATURE: this reference - Assigning fields and resolving shadowing
     * FEATURE: Parameter passing - Primitive values and object references passed by value
     */
    public Student(String id, String name, int age, String srn, Department department, 
                   byte semester, int[] marks, short totalCredits, boolean isHosteller, float scholarshipPercent) {
        // FEATURE: super keyword - Explicit invocation of superclass constructor
        super(id, name, age);
        
        // FEATURE: Exploring String class - toUpperCase(), trim()
        this.srn = (srn != null) ? srn.trim().toUpperCase() : "R24UNKNOWN";
        this.department = (department != null) ? department : Department.CSE;
        this.semester = semester;
        
        // FEATURE: Arrays - Defensive copy of marks array
        if (marks != null) {
            this.marks = new int[marks.length];
            // FEATURE: Control flow statements - for loop for array copying
            for (int i = 0; i < marks.length; i++) {
                this.marks[i] = marks[i];
            }
        } else {
            this.marks = new int[] {0, 0, 0, 0, 0};
        }

        this.totalCredits = totalCredits;
        this.isHosteller = isHosteller;
        this.scholarshipPercent = scholarshipPercent;
        this.grade = 'U'; // Unassigned initially
        this.feeDue = 0.0;
        
        // FEATURE: Operators, operator hierarchy, expressions - Incrementing static sequence
        this.admissionNumber = ++admissionSequence;
    }

    /**
     * FEATURE: Constructors - Overloaded constructor providing default credits and auto-generated ID
     * FEATURE: this reference - Constructor chaining via this(...)
     * FEATURE: Static fields and methods - Utilizing static counter
     */
    public Student(String name, int age, String srn, Department department, byte semester, boolean isHosteller) {
        this("REVA-STU-" + (++studentCounter), name, age, srn, department, semester, 
             new int[] {75, 75, 75, 75, 75}, (short) 20, isHosteller, 0.0f);
    }

    /**
     * FEATURE: Constructors - Default constructor
     * FEATURE: this reference - Constructor chaining
     */
    public Student() {
        this("Student-" + (++studentCounter), 19, "R24DEF000", Department.CSE, (byte) 1, false);
    }

    // FEATURE: Static fields and methods - Next ID generator helper
    public static String generateNextId() {
        return "REVA-STU-" + (++studentCounter);
    }

    // FEATURE: Encapsulation - Public Getter and Setter methods
    public String getSrn() {
        return srn;
    }

    public void setSrn(String srn) {
        if (srn != null && !srn.trim().isEmpty()) {
            this.srn = srn.trim().toUpperCase();
        }
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        if (department != null) {
            this.department = department;
        }
    }

    public byte getSemester() {
        return semester;
    }

    public void setSemester(byte semester) {
        if (semester >= 1 && semester <= 8) {
            this.semester = semester;
        }
    }

    // FEATURE: Arrays - Array getter
    public int[] getMarks() {
        // Return defensive copy of array
        return Arrays.copyOf(marks, marks.length);
    }

    public void setMarks(int[] marks) {
        if (marks != null) {
            this.marks = Arrays.copyOf(marks, marks.length);
        }
    }

    public double getFeeDue() {
        return feeDue;
    }

    public void setFeeDue(double feeDue) {
        // FEATURE: Operators - ternary operator
        this.feeDue = (feeDue >= 0.0) ? feeDue : 0.0;
    }

    public char getGrade() {
        return grade;
    }

    public void setGrade(char grade) {
        this.grade = grade;
    }

    public boolean isHosteller() {
        return isHosteller;
    }

    public void setHosteller(boolean hosteller) {
        isHosteller = hosteller;
    }

    public long getAdmissionNumber() {
        return admissionNumber;
    }

    public float getScholarshipPercent() {
        return scholarshipPercent;
    }

    public void setScholarshipPercent(float scholarshipPercent) {
        if (scholarshipPercent >= 0.0f && scholarshipPercent <= 100.0f) {
            this.scholarshipPercent = scholarshipPercent;
        }
    }

    public short getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(short totalCredits) {
        if (totalCredits > 0) {
            this.totalCredits = totalCredits;
        }
    }

    /**
     * FEATURE: Operators - Bitwise operators (<<, |, &, ~, ^)
     * Encodes student academic status into a compact integer bitmask.
     */
    public int getStatusBitmask() {
        int mask = 0;
        if (this.feeDue == 0.0) {
            mask |= FLAG_TUITION_CLEARED; // Bitwise OR
        }
        if (this.isHosteller) {
            mask |= FLAG_HOSTELLER;       // Bitwise OR
        }
        if (this.scholarshipPercent > 0.0f) {
            mask |= FLAG_SCHOLARSHIP;     // Bitwise OR
        }
        if (this.grade == 'S' || this.grade == 'A') {
            mask |= FLAG_DEAN_LIST;       // Bitwise OR
        }
        return mask;
    }

    /**
     * FEATURE: Operators - Bitwise AND (&) to test flag presence
     */
    public boolean hasStatusFlag(int flag) {
        return (getStatusBitmask() & flag) != 0;
    }

    /**
     * FEATURE: Abstract classes and methods - Implementing abstract method from Person
     * FEATURE: Method overriding - @Override annotation
     * FEATURE: Member access rules - Directly accessing protected fields 'name' and 'age' from Person
     * FEATURE: Formatting output - Formatted console output using System.out.printf
     */
    @Override
    public void displayDetails() {
        System.out.println("------------------------------------------------------------");
        System.out.println(" [STUDENT RECORD] " + this.getName() + " (SRN: " + this.srn + ")");
        System.out.println("------------------------------------------------------------");
        // FEATURE: Preventing inheritance - Calling final getId() from superclass Person
        System.out.printf("  %-20s: %s\n", "Student ID", getId());
        System.out.printf("  %-20s: %s\n", "Full Name", this.name); // Accessing protected superclass field
        System.out.printf("  %-20s: %d\n", "Age", this.age);         // Accessing protected superclass field
        System.out.printf("  %-20s: %s\n", "SRN", this.srn);
        System.out.printf("  %-20s: %s\n", "Department", this.department.getFullName());
        System.out.printf("  %-20s: %d\n", "Semester", this.semester);
        System.out.printf("  %-20s: %d\n", "Admission Number", this.admissionNumber);
        System.out.printf("  %-20s: %s\n", "Hosteller", (this.isHosteller ? "YES" : "NO"));
        System.out.printf("  %-20s: %.1f%%\n", "Scholarship", this.scholarshipPercent);
        System.out.printf("  %-20s: %s\n", "Marks (5 Subjects)", Arrays.toString(this.marks));
        System.out.printf("  %-20s: %c\n", "Calculated Grade", this.grade);
        System.out.printf("  %-20s: INR %.2f\n", "Outstanding Fee", this.feeDue);
        System.out.println("------------------------------------------------------------");
    }

    /**
     * FEATURE: Implement interfaces - Implementing Payable.payFee()
     * FEATURE: Operators, operator hierarchy, expressions - Compound assignment operator (-=)
     * FEATURE: Control flow statements - if-else
     */
    @Override
    public void payFee(double amount) {
        if (amount <= 0) {
            System.out.println("[ERROR] Payment amount must be strictly greater than 0.");
            return;
        }
        if (amount > this.feeDue) {
            // FEATURE: Formatting output
            System.out.printf("[INFO] Amount exceeds balance (INR %.2f). Extra INR %.2f refunded.\n", 
                              this.feeDue, (amount - this.feeDue));
            this.feeDue = 0.0;
        } else {
            this.feeDue -= amount; // Compound subtraction assignment
            System.out.printf("[SUCCESS] Payment of INR %.2f recorded for %s (%s). New Balance: INR %.2f\n",
                              amount, this.name, this.srn, this.feeDue);
        }
    }

    /**
     * FEATURE: Implement interfaces - Implementing Payable.getDueAmount()
     */
    @Override
    public double getDueAmount() {
        return this.feeDue;
    }

    /**
     * FEATURE: Implement interfaces - Implementing Reportable.generateReport() (inherited via Payable)
     * FEATURE: Exploring String class - String concatenation and formatting
     */
    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append(getReportHeader()).append("\n");
        sb.append(String.format("Student: %-20s | SRN: %-12s | Dept: %-6s | Sem: %d\n", 
                                this.name, this.srn, this.department.getCode(), this.semester));
        sb.append(String.format("Grade: %-3c | Credits: %-3d | Fee Balance: INR %-10.2f\n", 
                                this.grade, this.totalCredits, this.feeDue));
        return sb.toString();
    }

    /**
     * FEATURE: The Object class and its methods - Overriding toString()
     */
    @Override
    public String toString() {
        return String.format("Student[id=%s, srn=%s, name=%s, dept=%s, sem=%d, grade=%c, due=%.2f]",
                             getId(), srn, name, department.getCode(), semester, grade, feeDue);
    }

    /**
     * FEATURE: The Object class and its methods - Overriding equals(Object obj)
     * Two student records are equal if their SRNs match.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return this.srn.equalsIgnoreCase(student.srn);
    }

    /**
     * FEATURE: The Object class and its methods - Overriding hashCode()
     */
    @Override
    public int hashCode() {
        return this.srn.toLowerCase().hashCode();
    }
}
