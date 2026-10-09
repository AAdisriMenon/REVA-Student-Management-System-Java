package com.reva.sms.model;

import java.util.Arrays;

public class Student extends Person implements Payable {
    private String srn;

    private Department department;

    private byte semester;

    private int[] marks;

    private double feeDue;

    private char grade;

    private boolean isHosteller;

    private long admissionNumber;

    private float scholarshipPercent;

    private short totalCredits;

    private static int studentCounter = 1000;
    private static long admissionSequence = 2026000000L;

    public static final int FLAG_TUITION_CLEARED = 1 << 0;
    public static final int FLAG_HOSTELLER        = 1 << 1;
    public static final int FLAG_SCHOLARSHIP      = 1 << 2;
    public static final int FLAG_DEAN_LIST        = 1 << 3;

    public Student(String id, String name, int age, String srn, Department department,
                   byte semester, int[] marks, short totalCredits, boolean isHosteller, float scholarshipPercent) {
        super(id, name, age);

        this.srn = (srn != null) ? srn.trim().toUpperCase() : "R24UNKNOWN";
        this.department = (department != null) ? department : Department.CSE;
        this.semester = semester;

        if (marks != null) {
            this.marks = new int[marks.length];
            for (int i = 0; i < marks.length; i++) {
                this.marks[i] = marks[i];
            }
        } else {
            this.marks = new int[] {0, 0, 0, 0, 0};
        }

        this.totalCredits = totalCredits;
        this.isHosteller = isHosteller;
        this.scholarshipPercent = scholarshipPercent;
        this.grade = 'U';
        this.feeDue = 0.0;

        this.admissionNumber = ++admissionSequence;
    }

    public Student(String name, int age, String srn, Department department, byte semester, boolean isHosteller) {
        this("REVA-STU-" + (++studentCounter), name, age, srn, department, semester,
             new int[] {75, 75, 75, 75, 75}, (short) 20, isHosteller, 0.0f);
    }

    public Student() {
        this("Student-" + (++studentCounter), 19, "R24DEF000", Department.CSE, (byte) 1, false);
    }

    public static String generateNextId() {
        return "REVA-STU-" + (++studentCounter);
    }

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

    public int[] getMarks() {
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

    public int getStatusBitmask() {
        int mask = 0;
        if (this.feeDue == 0.0) {
            mask |= FLAG_TUITION_CLEARED;
        }
        if (this.isHosteller) {
            mask |= FLAG_HOSTELLER;
        }
        if (this.scholarshipPercent > 0.0f) {
            mask |= FLAG_SCHOLARSHIP;
        }
        if (this.grade == 'S' || this.grade == 'A') {
            mask |= FLAG_DEAN_LIST;
        }
        return mask;
    }

    public boolean hasStatusFlag(int flag) {
        return (getStatusBitmask() & flag) != 0;
    }

    @Override
    public void displayDetails() {
        System.out.println("------------------------------------------------------------");
        System.out.println(" [STUDENT RECORD] " + this.getName() + " (SRN: " + this.srn + ")");
        System.out.println("------------------------------------------------------------");
        System.out.printf("  %-20s: %s\n", "Student ID", getId());
        System.out.printf("  %-20s: %s\n", "Full Name", this.name);
        System.out.printf("  %-20s: %d\n", "Age", this.age);
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

    @Override
    public void payFee(double amount) {
        if (amount <= 0) {
            System.out.println("[ERROR] Payment amount must be strictly greater than 0.");
            return;
        }
        if (amount > this.feeDue) {
            System.out.printf("[INFO] Amount exceeds balance (INR %.2f). Extra INR %.2f refunded.\n",
                              this.feeDue, (amount - this.feeDue));
            this.feeDue = 0.0;
        } else {
            this.feeDue -= amount;
            System.out.printf("[SUCCESS] Payment of INR %.2f recorded for %s (%s). New Balance: INR %.2f\n",
                              amount, this.name, this.srn, this.feeDue);
        }
    }

    @Override
    public double getDueAmount() {
        return this.feeDue;
    }

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

    @Override
    public String toString() {
        return String.format("Student[id=%s, srn=%s, name=%s, dept=%s, sem=%d, grade=%c, due=%.2f]",
                             getId(), srn, name, department.getCode(), semester, grade, feeDue);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return this.srn.equalsIgnoreCase(student.srn);
    }

    @Override
    public int hashCode() {
        return this.srn.toLowerCase().hashCode();
    }
}
