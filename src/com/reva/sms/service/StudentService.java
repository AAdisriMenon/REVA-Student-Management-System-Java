// FEATURE: Packages - Defining a package
package com.reva.sms.service;

// FEATURE: Packages - Importing packages
import com.reva.sms.model.Department;
import com.reva.sms.model.Faculty;
import com.reva.sms.model.Payable;
import com.reva.sms.model.Person;
import com.reva.sms.model.Student;

/**
 * FEATURE: Classes and objects - Central service class managing application state
 * FEATURE: Encapsulation - Private member arrays with public operations
 * 
 * StudentService manages student record storage and faculty records using in-memory
 * fixed-size arrays, satisfying all syllabus requirements for Unit I and Unit II.
 * 
 * Course Context: REVA University, B.Sc. (BSTCs), Semester V
 * Java Programming (Units I & II Mini Project)
 */
public class StudentService {
    // FEATURE: Variables and constants - Maximum storage capacity constants
    public static final int MAX_STUDENTS = 100;
    public static final int MAX_FACULTY = 50;

    // FEATURE: Arrays - Fixed-size reference arrays for in-memory record storage
    // FEATURE: Encapsulation - Private internal arrays
    private final Student[] students;
    private int count;

    private final Faculty[] facultyList;
    private int facultyCount;

    // FEATURE: Static fields and methods - Static helper generating unique student ID
    public static String generateNextIdSafe() {
        return Student.generateNextId();
    }

    /**
     * FEATURE: Constructors - Default constructor initializing internal storage
     */
    public StudentService() {
        this.students = new Student[MAX_STUDENTS];
        this.count = 0;
        this.facultyList = new Faculty[MAX_FACULTY];
        this.facultyCount = 0;
    }

    /**
     * FEATURE: Methods - Add student to the storage array
     * FEATURE: Control flow statements - Boundary condition check
     * FEATURE: Jump statements - return
     */
    public boolean addStudent(Student student) {
        if (student == null) {
            System.out.println("[ERROR] Cannot register null student reference.");
            return false;
        }

        // FEATURE: Control flow statements - Capacity boundary check
        if (this.count >= MAX_STUDENTS) {
            System.out.println("[ERROR] Database capacity reached (" + MAX_STUDENTS + " records). Cannot add student.");
            return false;
        }

        // FEATURE: Exploring String class - equalsIgnoreCase() for duplicate check
        for (int i = 0; i < this.count; i++) {
            if (this.students[i].getSrn().equalsIgnoreCase(student.getSrn())) {
                System.out.println("[ERROR] Duplicate SRN detected: " + student.getSrn() + ". Record rejected.");
                return false;
            }
        }

        // FEATURE: Arrays - Inserting into reference array at next available index
        this.students[this.count] = student;
        this.count++;
        return true;
    }

    /**
     * FEATURE: Methods - Add faculty member to the storage array
     */
    public boolean addFaculty(Faculty faculty) {
        if (faculty == null) {
            return false;
        }
        if (this.facultyCount >= MAX_FACULTY) {
            return false;
        }
        this.facultyList[this.facultyCount++] = faculty;
        return true;
    }

    /**
     * FEATURE: Methods - Find student by exact SRN
     * FEATURE: Control flow statements - for loop iteration
     * FEATURE: Exploring String class - equalsIgnoreCase() and trim()
     */
    public Student findStudentBySrn(String srn) {
        if (srn == null || srn.trim().isEmpty()) {
            return null;
        }
        String searchKey = srn.trim();
        for (int i = 0; i < this.count; i++) {
            if (this.students[i].getSrn().equalsIgnoreCase(searchKey)) {
                return this.students[i];
            }
        }
        return null; // Not found
    }

    /**
     * FEATURE: Methods - Search students by partial name (case-insensitive)
     * FEATURE: Arrays - Dynamically allocated result array sized to matches
     * FEATURE: Exploring String class - toLowerCase() and contains()
     * FEATURE: Control flow statements - Jump statement (continue)
     */
    public Student[] searchStudentsByName(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new Student[0];
        }

        String lowerQuery = query.trim().toLowerCase();
        int matchCount = 0;

        for (int i = 0; i < this.count; i++) {
            if (this.students[i].getName().toLowerCase().contains(lowerQuery)) {
                matchCount++;
            }
        }

        Student[] results = new Student[matchCount];
        int resultIndex = 0;

        for (int i = 0; i < this.count; i++) {
            // FEATURE: Jump statements - continue to skip non-matching records
            if (!this.students[i].getName().toLowerCase().contains(lowerQuery)) {
                continue;
            }
            results[resultIndex++] = this.students[i];
        }
        return results;
    }

    /**
     * FEATURE: Methods - Method overloading (first signature: filters by Department enum)
     * FEATURE: Polymorphism - Static/compile-time polymorphism via overload resolution
     */
    public Student[] searchStudents(Department department) {
        if (department == null) {
            return new Student[0];
        }
        int matchCount = 0;
        for (int i = 0; i < this.count; i++) {
            if (this.students[i].getDepartment() == department) {
                matchCount++;
            }
        }
        Student[] results = new Student[matchCount];
        int resultIndex = 0;
        for (int i = 0; i < this.count; i++) {
            if (this.students[i].getDepartment() == department) {
                results[resultIndex++] = this.students[i];
            }
        }
        return results;
    }

    /**
     * FEATURE: Methods - Method overloading (second signature: filters by partial name)
     */
    public Student[] searchStudents(String name) {
        return searchStudentsByName(name);
    }

    /**
     * FEATURE: Arrays - Array element removal with left-shift repositioning
     * FEATURE: Control flow statements - for loop
     * FEATURE: Jump statements - break and return
     */
    public boolean removeStudentBySrn(String srn) {
        if (srn == null) return false;
        int targetIndex = -1;

        for (int i = 0; i < this.count; i++) {
            if (this.students[i].getSrn().equalsIgnoreCase(srn.trim())) {
                targetIndex = i;
                break; // FEATURE: Jump statements - break out of loop once found
            }
        }

        if (targetIndex == -1) {
            return false;
        }

        // Shift remaining elements one position to the left to maintain contiguous array
        for (int i = targetIndex; i < this.count - 1; i++) {
            this.students[i] = this.students[i + 1];
        }
        this.students[this.count - 1] = null; // Clear dangling reference
        this.count--;
        return true;
    }

    /**
     * FEATURE: Arrays - In-place sorting of array
     * FEATURE: Control flow statements - Nested for loops (Bubble Sort algorithm)
     * FEATURE: Exploring String class - compareToIgnoreCase()
     */
    public void sortStudentsByName() {
        if (this.count <= 1) return;
        for (int i = 0; i < this.count - 1; i++) {
            for (int j = 0; j < this.count - i - 1; j++) {
                if (this.students[j].getName().compareToIgnoreCase(this.students[j + 1].getName()) > 0) {
                    Student temp = this.students[j];
                    this.students[j] = this.students[j + 1];
                    this.students[j + 1] = temp;
                }
            }
        }
    }

    /**
     * FEATURE: Arrays - Sorting by SRN
     */
    public void sortStudentsBySrn() {
        if (this.count <= 1) return;
        for (int i = 0; i < this.count - 1; i++) {
            for (int j = 0; j < this.count - i - 1; j++) {
                if (this.students[j].getSrn().compareToIgnoreCase(this.students[j + 1].getSrn()) > 0) {
                    Student temp = this.students[j];
                    this.students[j] = this.students[j + 1];
                    this.students[j + 1] = temp;
                }
            }
        }
    }

    /**
     * FEATURE: Arrays - Sorting by Semester
     */
    public void sortStudentsBySemester() {
        if (this.count <= 1) return;
        for (int i = 0; i < this.count - 1; i++) {
            for (int j = 0; j < this.count - i - 1; j++) {
                if (this.students[j].getSemester() > this.students[j + 1].getSemester()) {
                    Student temp = this.students[j];
                    this.students[j] = this.students[j + 1];
                    this.students[j + 1] = temp;
                }
            }
        }
    }

    /**
     * FEATURE: Arrays - Sorting by Fee Due (Ascending)
     */
    public void sortStudentsByFeeDue() {
        if (this.count <= 1) return;
        for (int i = 0; i < this.count - 1; i++) {
            for (int j = 0; j < this.count - i - 1; j++) {
                if (this.students[j].getFeeDue() > this.students[j + 1].getFeeDue()) {
                    Student temp = this.students[j];
                    this.students[j] = this.students[j + 1];
                    this.students[j + 1] = temp;
                }
            }
        }
    }

    /**
     * FEATURE: Interface - Accessing implementations through interface references
     * FEATURE: Polymorphism - Polymorphic reference to Payable
     */
    public boolean processFeePayment(String srn, double amount) {
        Student student = findStudentBySrn(srn);
        if (student == null) {
            System.out.println("[ERROR] Student with SRN '" + srn + "' not found.");
            return false;
        }

        // FEATURE: Accessing implementations through interface references
        Payable billableEntity = student;
        billableEntity.payFee(amount);
        return true;
    }

    /**
     * FEATURE: Operators, operator hierarchy, expressions - Arithmetic accumulation and division
     * FEATURE: Type conversion and casting - double division
     * FEATURE: Control flow statements - while loop demonstration
     */
    public double calculateAverageFeeDue() {
        if (this.count == 0) {
            return 0.0;
        }

        double totalDue = 0.0;
        int i = 0;
        while (i < this.count) {
            totalDue += this.students[i].getFeeDue();
            i++;
        }

        double average = totalDue / this.count;
        return Math.round(average * 100.0) / 100.0;
    }

    /**
     * FEATURE: Formatting output - Tabular display of all records using System.out.printf
     */
    public void displayAllStudentsTable() {
        if (this.count == 0) {
            System.out.println("\n[INFO] No student records currently registered.");
            return;
        }

        System.out.println("\n" + "=".repeat(105));
        System.out.printf("%-14s | %-12s | %-22s | %-6s | %-4s | %-6s | %-12s | %-10s\n",
                          "STUDENT ID", "SRN", "NAME", "DEPT", "SEM", "GRADE", "FEE DUE (INR)", "HOSTELLER");
        System.out.println("-".repeat(105));

        for (int i = 0; i < this.count; i++) {
            Student s = this.students[i];
            System.out.printf("%-14s | %-12s | %-22s | %-6s | %-4d | %-6c | %13.2f | %-10s\n",
                              s.getId(),
                              s.getSrn(),
                              s.getName(),
                              s.getDepartment().getCode(),
                              s.getSemester(),
                              s.getGrade(),
                              s.getFeeDue(),
                              (s.isHosteller() ? "YES" : "NO"));
        }
        System.out.println("=".repeat(105));
        System.out.printf("Total Enrolled Students: %d | Total Database Capacity: %d\n", this.count, MAX_STUDENTS);
    }

    /**
     * FEATURE: Formatting output - Department-wise fee analytics and revenue summary
     */
    public void displayDepartmentFeeAnalytics() {
        System.out.println("\n==========================================================================================");
        System.out.println("                        DEPARTMENT-WISE FEE ANALYTICS & REVENUE SUMMARY                   ");
        System.out.println("==========================================================================================");
        System.out.printf("%-6s | %-38s | %-8s | %-16s | %-16s\n", 
                          "CODE", "DEPARTMENT", "STUDENTS", "TOTAL DUE (INR)", "AVERAGE DUE (INR)");
        System.out.println("-".repeat(90));

        double overallTotalDue = 0.0;
        int totalStudentsWithDues = 0;
        int totalStudentsCleared = 0;

        for (Department dept : Department.values()) {
            int deptStudentCount = 0;
            double deptTotalDue = 0.0;

            for (int i = 0; i < this.count; i++) {
                if (this.students[i].getDepartment() == dept) {
                    deptStudentCount++;
                    deptTotalDue += this.students[i].getFeeDue();
                }
            }

            double deptAvg = (deptStudentCount > 0) ? (deptTotalDue / deptStudentCount) : 0.0;
            System.out.printf("%-6s | %-38s | %8d | %16.2f | %16.2f\n",
                              dept.getCode(), dept.getFullName(), deptStudentCount, deptTotalDue, deptAvg);
            overallTotalDue += deptTotalDue;
        }

        for (int i = 0; i < this.count; i++) {
            if (this.students[i].getFeeDue() == 0.0) {
                totalStudentsCleared++;
            } else {
                totalStudentsWithDues++;
            }
        }

        double overallAvg = (this.count > 0) ? (overallTotalDue / this.count) : 0.0;
        System.out.println("=".repeat(90));
        System.out.printf("  Total Enrolled Students Across University : %d\n", this.count);
        System.out.printf("  Students with Cleared Dues               : %d\n", totalStudentsCleared);
        System.out.printf("  Students with Outstanding Dues           : %d\n", totalStudentsWithDues);
        System.out.printf("  Gross Revenue Outstanding Across Depts   : INR %.2f\n", overallTotalDue);
        System.out.printf("  University Average Fee Due Per Student    : INR %.2f\n", overallAvg);
        System.out.println("==========================================================================================\n");
    }

    /**
     * FEATURE: Formatting output - Clean University Personnel Directory
     */
    public void displayPersonnelDirectory() {
        System.out.println("\n==========================================================================================");
        System.out.println("                               UNIVERSITY PERSONNEL DIRECTORY                              ");
        System.out.println("==========================================================================================");
        System.out.println("\nSTUDENTS (" + this.count + " Registered)");
        System.out.println("-".repeat(90));
        for (int i = 0; i < this.count; i++) {
            Student s = this.students[i];
            System.out.printf("  [%-4s] %-22s | SRN: %-10s | Sem: %d | Grade: %c\n",
                              s.getDepartment().getCode(), s.getName(), s.getSrn(), s.getSemester(), s.getGrade());
        }

        System.out.println("\nFACULTY (" + this.facultyCount + " Appointed)");
        System.out.println("-".repeat(90));
        for (int i = 0; i < this.facultyCount; i++) {
            Faculty f = this.facultyList[i];
            System.out.printf("  [%-4s] %-24s | ID: %-10s | %-22s | Spec: %s\n",
                              f.getDepartment().getCode(), f.getName(), f.getId(), f.getDesignation(), f.getSpecialization());
        }
        System.out.println("==========================================================================================\n");
    }

    /**
     * FEATURE: Polymorphism: dynamic binding - Runtime polymorphism with heterogeneous Person array
     * FEATURE: Abstract classes and methods - Calling displayDetails() on abstract Person reference
     * FEATURE: Inheritance hierarchies - Processing both Student and Faculty instances seamlessly
     */
    public void demonstratePolymorphicDispatch() {
        System.out.println("\n============================================================");
        System.out.println(" DEMONSTRATION: Dynamic Method Binding (Runtime Polymorphism)");
        System.out.println("============================================================");
        System.out.println("Array of abstract base type: Person[] people = new Person[3];");
        System.out.println("Holding both Student and Faculty instances seamlessly.\n");

        Person[] universityCommunity = new Person[3];
        universityCommunity[0] = new Student("Arjun Nair", 20, "R24CS001", Department.CSE, (byte) 5, false);
        universityCommunity[1] = new Faculty("FAC-CSE-01", "Dr. Vikram Shah", 48, "Professor & HOD", 
                                             Department.CSE, "Artificial Intelligence & Machine Learning");
        universityCommunity[2] = new Faculty("FAC-ECE-01", "Dr. Rajiv Nair", 45, "Professor & Dean", 
                                             Department.ECE, "VLSI Design & Embedded Systems");

        for (Person person : universityCommunity) {
            System.out.println("Executing: person.displayDetails() [Reference Type: Person, Actual: " 
                               + person.getClass().getSimpleName() + "]");
            person.displayDetails();

            if (person instanceof Student student) {
                System.out.printf("  -> [instanceof Student: true] SRN: %s | Grade: %c | Fee Due: INR %.2f%n",
                                  student.getSrn(), student.getGrade(), student.getFeeDue());
            } else if (person instanceof Faculty faculty) {
                System.out.printf("  -> [instanceof Faculty: true] Specialization: %s | Designation: %s%n",
                                  faculty.getSpecialization(), faculty.getDesignation());
            }
            System.out.println();
        }
    }

    /**
     * FEATURE: Methods and parameter passing - Demonstrating Pass-by-Value in Java
     */
    public void demonstrateParameterPassing(int primitiveAge, Student studentRef) {
        System.out.println("\n============================================================");
        System.out.println(" DEMONSTRATION: Java Parameter Passing Mechanism (Pass-by-Value)");
        System.out.println("============================================================");

        System.out.println("1. Primitive Value Demonstration:");
        System.out.println("   Original primitiveAge inside method before modification: " + primitiveAge);
        primitiveAge = primitiveAge + 10;
        System.out.println("   primitiveAge inside method after adding 10: " + primitiveAge);
        System.out.println("   (Caller's original variable will REMAIN UNCHANGED!)\n");

        System.out.println("2. Object Reference Demonstration:");
        if (studentRef != null) {
            String originalName = studentRef.getName();
            System.out.println("   Student name before mutation: " + originalName);
            studentRef.setName(originalName + " (Ref Mutated)");
            System.out.println("   Student name mutated through reference: " + studentRef.getName());
            System.out.println("   (Caller observes this modification because reference points to same object in heap)");
            studentRef.setName(originalName); // Restored state to keep data clean
        }
        System.out.println("============================================================\n");
    }

    /**
     * FEATURE: Exploring String class - Demonstrates multiple String methods in action
     */
    public void demonstrateStringExploration(String sampleText) {
        System.out.println("\n============================================================");
        System.out.println(" DEMONSTRATION: Exploring java.lang.String Class Methods");
        System.out.println("============================================================");
        System.out.println("Original String: \"" + sampleText + "\"");
        System.out.println("1. length()          : " + sampleText.length());
        System.out.println("2. toUpperCase()     : " + sampleText.toUpperCase());
        System.out.println("3. toLowerCase()     : " + sampleText.toLowerCase());
        System.out.println("4. trim()            : \"" + sampleText.trim() + "\"");
        System.out.println("5. contains(\"REVA\")  : " + sampleText.contains("REVA"));
        System.out.println("6. substring(0, 4)   : " + (sampleText.length() >= 4 ? sampleText.substring(0, 4) : "N/A"));
        System.out.println("7. equalsIgnoreCase(\"reva\"): " + sampleText.trim().equalsIgnoreCase("reva"));
        System.out.println("============================================================\n");
    }

    public int getCount() {
        return this.count;
    }

    public Student[] getAllStudents() {
        Student[] copy = new Student[this.count];
        System.arraycopy(this.students, 0, copy, 0, this.count);
        return copy;
    }

    public int getFacultyCount() {
        return this.facultyCount;
    }

    public Faculty[] getAllFaculty() {
        Faculty[] copy = new Faculty[this.facultyCount];
        System.arraycopy(this.facultyList, 0, copy, 0, this.facultyCount);
        return copy;
    }

    /**
     * Helper to add preloaded student with calculated grade and fee.
     */
    private void addPreloadedStudent(String name, int age, String srn, Department dept, byte sem, 
                                     int[] marks, short credits, boolean hosteller, float scholarship) {
        String id = generateNextIdSafe();
        Student s = new Student(id, name, age, srn, dept, sem, marks, credits, hosteller, scholarship);
        s.setGrade(GradeCalculator.calculateGrade(GradeCalculator.calculatePercentage(marks)));
        s.setFeeDue(FeeCalculator.calculateSemesterFee(credits, dept, hosteller, scholarship));
        addStudent(s);
    }

    /**
     * Pre-populates the service with a comprehensive, realistic university dataset:
     * - 33 students (at least 3 per department across all 11 departments)
     * - 22 faculty members (2 per department across all 11 departments)
     */
    public void loadSampleData() {
        // --- 1. Computer Science & Engineering (CSE) ---
        addPreloadedStudent("Arjun Nair", 20, "R24CS001", Department.CSE, (byte) 5, new int[] {95, 92, 98, 89, 94}, (short) 24, false, 25.0f);
        addPreloadedStudent("Priya Sharma", 20, "R24CS002", Department.CSE, (byte) 5, new int[] {92, 88, 95, 91, 89}, (short) 22, true, 15.0f);
        addPreloadedStudent("Rahul Menon", 21, "R24CS003", Department.CSE, (byte) 6, new int[] {85, 80, 88, 82, 84}, (short) 20, false, 0.0f);

        // --- 2. Electronics & Communication Engineering (ECE) ---
        addPreloadedStudent("Sneha Iyer", 20, "R24EC001", Department.ECE, (byte) 5, new int[] {88, 85, 90, 84, 87}, (short) 22, true, 10.0f);
        addPreloadedStudent("Karthik Rao", 21, "R24EC002", Department.ECE, (byte) 6, new int[] {78, 82, 75, 80, 79}, (short) 20, false, 0.0f);
        addPreloadedStudent("Ananya Krishnan", 19, "R24EC003", Department.ECE, (byte) 3, new int[] {94, 91, 96, 92, 95}, (short) 24, true, 20.0f);

        // --- 3. Mechanical Engineering (MECH) ---
        addPreloadedStudent("Rohan Mehta", 22, "R24ME001", Department.MECH, (byte) 7, new int[] {72, 68, 75, 70, 74}, (short) 20, true, 5.0f);
        addPreloadedStudent("Meera Nair", 20, "R24ME002", Department.MECH, (byte) 4, new int[] {82, 80, 85, 78, 84}, (short) 22, false, 10.0f);
        addPreloadedStudent("Vivek Kumar", 21, "R24ME003", Department.MECH, (byte) 5, new int[] {65, 62, 70, 64, 68}, (short) 18, true, 0.0f);

        // --- 4. Civil Engineering (CIVIL) ---
        addPreloadedStudent("Aditi Shah", 20, "R24CV001", Department.CIVIL, (byte) 4, new int[] {80, 78, 82, 79, 81}, (short) 20, false, 0.0f);
        addPreloadedStudent("Nikhil Varma", 21, "R24CV002", Department.CIVIL, (byte) 6, new int[] {75, 70, 78, 72, 74}, (short) 22, true, 10.0f);
        addPreloadedStudent("Diya Reddy", 19, "R24CV003", Department.CIVIL, (byte) 3, new int[] {88, 84, 90, 86, 85}, (short) 20, false, 15.0f);

        // --- 5. Information Science & Engineering (ISE) ---
        addPreloadedStudent("Harish Bhat", 20, "R24IS001", Department.ISE, (byte) 5, new int[] {91, 89, 93, 87, 90}, (short) 22, false, 20.0f);
        addPreloadedStudent("Kavya Rao", 21, "R24IS002", Department.ISE, (byte) 5, new int[] {84, 82, 86, 80, 85}, (short) 20, true, 10.0f);
        addPreloadedStudent("Akash Jain", 20, "R24IS003", Department.ISE, (byte) 4, new int[] {58, 62, 55, 60, 59}, (short) 20, false, 0.0f);

        // --- 6. Electrical & Electronics Engineering (EEE) ---
        addPreloadedStudent("Neha Pillai", 21, "R24EE001", Department.EEE, (byte) 6, new int[] {86, 82, 89, 85, 88}, (short) 22, true, 15.0f);
        addPreloadedStudent("Rohit Das", 20, "R24EE002", Department.EEE, (byte) 4, new int[] {74, 70, 76, 72, 75}, (short) 20, false, 0.0f);
        addPreloadedStudent("Ishita Menon", 19, "R24EE003", Department.EEE, (byte) 3, new int[] {92, 90, 94, 88, 91}, (short) 24, true, 25.0f);

        // --- 7. Biotechnology (BT) ---
        addPreloadedStudent("Varun Shetty", 20, "R24BT001", Department.BIOTECHNOLOGY, (byte) 4, new int[] {88, 85, 90, 86, 87}, (short) 22, false, 10.0f);
        addPreloadedStudent("Pooja Nair", 21, "R24BT002", Department.BIOTECHNOLOGY, (byte) 5, new int[] {95, 92, 96, 90, 94}, (short) 24, true, 20.0f);
        addPreloadedStudent("Manoj Kulkarni", 20, "R24BT003", Department.BIOTECHNOLOGY, (byte) 4, new int[] {68, 64, 70, 66, 67}, (short) 18, false, 0.0f);

        // --- 8. Allied Health Sciences (AHS) ---
        addPreloadedStudent("Shreya Joshi", 20, "R24AH001", Department.ALLIED_HEALTH, (byte) 4, new int[] {85, 82, 88, 84, 86}, (short) 22, true, 10.0f);
        addPreloadedStudent("Deepa Hegde", 21, "R24AH002", Department.ALLIED_HEALTH, (byte) 5, new int[] {90, 88, 92, 87, 89}, (short) 20, false, 15.0f);
        addPreloadedStudent("Tanvi Deshmukh", 19, "R24AH003", Department.ALLIED_HEALTH, (byte) 2, new int[] {72, 70, 75, 68, 71}, (short) 18, true, 0.0f);

        // --- 9. School of Commerce (COM) ---
        addPreloadedStudent("Aditya Verma", 20, "R24CM001", Department.COMMERCE, (byte) 4, new int[] {82, 78, 85, 80, 84}, (short) 20, false, 0.0f);
        addPreloadedStudent("Bhavana Murthy", 21, "R24CM002", Department.COMMERCE, (byte) 6, new int[] {94, 90, 95, 92, 91}, (short) 22, true, 20.0f);
        addPreloadedStudent("Chetan Gowda", 20, "R24CM003", Department.COMMERCE, (byte) 4, new int[] {64, 60, 68, 62, 65}, (short) 18, false, 0.0f);

        // --- 10. School of Management Studies (MGT) ---
        addPreloadedStudent("Divya Swaminathan", 21, "R24MG001", Department.MANAGEMENT, (byte) 5, new int[] {91, 88, 93, 89, 90}, (short) 24, true, 15.0f);
        addPreloadedStudent("Eashan Prabhu", 20, "R24MG002", Department.MANAGEMENT, (byte) 4, new int[] {76, 72, 80, 74, 77}, (short) 20, false, 0.0f);
        addPreloadedStudent("Fatima Zohra", 22, "R24MG003", Department.MANAGEMENT, (byte) 6, new int[] {86, 84, 88, 85, 87}, (short) 22, true, 10.0f);

        // --- 11. School of Performing Arts (PA) ---
        addPreloadedStudent("Gayatri Nambiar", 20, "R24PA001", Department.PERFORMING_ARTS, (byte) 4, new int[] {96, 94, 98, 92, 95}, (short) 22, false, 25.0f);
        addPreloadedStudent("Hemant Shastri", 21, "R24PA002", Department.PERFORMING_ARTS, (byte) 5, new int[] {80, 76, 82, 78, 81}, (short) 20, true, 10.0f);
        addPreloadedStudent("Indu Ramaswamy", 19, "R24PA003", Department.PERFORMING_ARTS, (byte) 3, new int[] {85, 82, 88, 80, 84}, (short) 18, false, 0.0f);

        // =========================================================================
        // FACULTY MEMBERS (2 per department = 22 faculty records)
        // =========================================================================
        // CSE
        addFaculty(new Faculty("FAC-CSE-01", "Dr. Vikram Shah", 48, "Professor & HOD", Department.CSE, "Artificial Intelligence & Machine Learning"));
        addFaculty(new Faculty("FAC-CSE-02", "Prof. Ananya Rao", 36, "Assistant Professor", Department.CSE, "Cloud Computing & Distributed Systems"));

        // ECE
        addFaculty(new Faculty("FAC-ECE-01", "Dr. Rajiv Nair", 45, "Professor & Dean", Department.ECE, "VLSI Design & Embedded Systems"));
        addFaculty(new Faculty("FAC-ECE-02", "Prof. Meera Iyer", 34, "Associate Professor", Department.ECE, "Wireless Communication & Signal Processing"));

        // MECH
        addFaculty(new Faculty("FAC-MEC-01", "Dr. Suresh Bhat", 52, "Professor", Department.MECH, "Thermal Engineering & Fluid Dynamics"));
        addFaculty(new Faculty("FAC-MEC-02", "Prof. Kavya Menon", 38, "Associate Professor", Department.MECH, "Robotics & Computer Integrated Manufacturing"));

        // CIVIL
        addFaculty(new Faculty("FAC-CIV-01", "Dr. Arvind Kumar", 50, "Professor & HOD", Department.CIVIL, "Structural Engineering & Earthquake Dynamics"));
        addFaculty(new Faculty("FAC-CIV-02", "Prof. Neha Sharma", 35, "Assistant Professor", Department.CIVIL, "Environmental Geotechnics"));

        // ISE
        addFaculty(new Faculty("FAC-ISE-01", "Dr. Harish Kulkarni", 44, "Professor", Department.ISE, "Cybersecurity & Cryptography"));
        addFaculty(new Faculty("FAC-ISE-02", "Prof. Sunita Reddy", 37, "Associate Professor", Department.ISE, "Big Data Analytics & Data Science"));

        // EEE
        addFaculty(new Faculty("FAC-EEE-01", "Dr. Ramesh Murthy", 49, "Professor & HOD", Department.EEE, "Renewable Energy Systems & Smart Grids"));
        addFaculty(new Faculty("FAC-EEE-02", "Prof. Deepa Joseph", 36, "Assistant Professor", Department.EEE, "Power Electronics & Electric Drives"));

        // BT
        addFaculty(new Faculty("FAC-BIO-01", "Dr. Sandeep Hegde", 46, "Professor", Department.BIOTECHNOLOGY, "Genetic Engineering & Molecular Biology"));
        addFaculty(new Faculty("FAC-BIO-02", "Prof. Rashmi Prabhu", 35, "Associate Professor", Department.BIOTECHNOLOGY, "Bioinformatics & Bioprocess Technology"));

        // AHS
        addFaculty(new Faculty("FAC-AHS-01", "Dr. Anand Swamy", 51, "Professor & Dean", Department.ALLIED_HEALTH, "Clinical Pathology & Diagnostic Medicine"));
        addFaculty(new Faculty("FAC-AHS-02", "Prof. Shilpa Shenoy", 39, "Associate Professor", Department.ALLIED_HEALTH, "Medical Imaging & Healthcare Informatics"));

        // COM
        addFaculty(new Faculty("FAC-COM-01", "Dr. Bhaskar Shenoy", 53, "Professor & Director", Department.COMMERCE, "Corporate Finance & Taxation"));
        addFaculty(new Faculty("FAC-COM-02", "Prof. Lakshmi Narayanan", 40, "Associate Professor", Department.COMMERCE, "Financial Accounting & Auditing"));

        // MGT
        addFaculty(new Faculty("FAC-MGT-01", "Dr. Keshava Prasad", 47, "Professor & Dean", Department.MANAGEMENT, "Strategic Management & International Business"));
        addFaculty(new Faculty("FAC-MGT-02", "Prof. Monica D'Souza", 38, "Associate Professor", Department.MANAGEMENT, "Organizational Behavior & Human Resources"));

        // PA
        addFaculty(new Faculty("FAC-ART-01", "Dr. Vasundhara Das", 50, "Professor & HOD", Department.PERFORMING_ARTS, "Classical Indian Music & Ethnomusicology"));
        addFaculty(new Faculty("FAC-ART-02", "Prof. Shankar Mahadevan Nair", 42, "Associate Professor", Department.PERFORMING_ARTS, "Theatrical Arts & Choreography"));
    }
}
