package com.reva.sms.main;

import com.reva.sms.model.*;
import com.reva.sms.service.FeeCalculator;
import com.reva.sms.service.GradeCalculator;
import com.reva.sms.service.StudentService;
import java.util.Scanner;

public class Main {

    public static final String APP_TITLE = "REVA UNIVERSITY - STUDENT MANAGEMENT SYSTEM";
    public static final String COURSE_INFO = "B.Sc. (BSTCs) Sem V - Java Programming (Unit I & II Project)";

    public static void main(String[] args) {
        StudentService service = new StudentService();
        Scanner scanner = new Scanner(System.in);

        service.loadSampleData();

        printBanner();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt(scanner, "Enter your choice: ", 0, 10);

            switch (choice) {
                case 1:
                    handleAddStudent(scanner, service);
                    break;
                case 2:
                    handleDisplayAll(service);
                    break;
                case 3:
                    handleSearchBySrn(scanner, service);
                    break;
                case 4:
                    handleSearchByName(scanner, service);
                    break;
                case 5:
                    handleFeePayment(scanner, service);
                    break;
                case 6:
                    handleRemoveStudent(scanner, service);
                    break;
                case 7:
                    handleSortStudents(scanner, service);
                    break;
                case 8:
                    handleFeeAnalytics(service);
                    break;
                case 9:
                    handlePersonnelDirectory(service);
                    break;
                case 10:
                    handleOOPDemonstrations(scanner, service);
                    break;
                case 0:
                    System.out.println("\nExiting " + APP_TITLE + ". Thank you!");
                    running = false;
                    break;
                default:
                    System.out.println("[WARNING] Invalid selection. Please choose an option from the menu.");
                    break;
            }
        }

        scanner.close();
    }

    private static void printBanner() {
        System.out.println("================================================================================");
        System.out.println("   " + APP_TITLE);
        System.out.println("   " + COURSE_INFO);
        System.out.println("================================================================================");
    }

    private static void printMenu() {
        System.out.println("\n============================================================");
        System.out.println("        REVA UNIVERSITY STUDENT MANAGEMENT SYSTEM");
        System.out.println("============================================================\n");
        System.out.println(" 1.  Add New Student");
        System.out.println(" 2.  Display All Student Records");
        System.out.println(" 3.  Search Student by SRN");
        System.out.println(" 4.  Search Student by Partial Name");
        System.out.println(" 5.  Calculate / Record Semester Fee");
        System.out.println(" 6.  Remove Student Record");
        System.out.println(" 7.  Sort Students");
        System.out.println(" 8.  View Department Fee Analytics");
        System.out.println(" 9.  View University Personnel Directory");
        System.out.println("10.  OOP & Java Concept Demonstrations");
        System.out.println(" 0.  Exit\n");
        System.out.println("============================================================");
    }

    private static void handleAddStudent(Scanner scanner, StudentService service) {
        System.out.println("\n--- [ADD NEW STUDENT] ---");

        String name = readString(scanner, "Enter Student Full Name: ");
        int age = readInt(scanner, "Enter Age (16 - 60): ", 16, 60);
        String srn = readString(scanner, "Enter SRN (e.g., R24CS010): ");

        if (service.findStudentBySrn(srn) != null) {
            System.out.println("[ERROR] Student with SRN '" + srn + "' already exists! Aborting addition.");
            return;
        }

        System.out.println("\nSelect Academic Department:");
        Department[] departments = Department.values();
        for (int i = 0; i < departments.length; i++) {
            System.out.printf("  %2d. %-6s - %s\n", (i + 1), departments[i].getCode(), departments[i].getFullName());
        }
        int deptChoice = readInt(scanner, "Select Department (1-" + departments.length + "): ", 1, departments.length);
        Department selectedDept = departments[deptChoice - 1];

        byte semester = (byte) readInt(scanner, "Enter Current Semester (1-8): ", 1, 8);
        boolean isHosteller = readBoolean(scanner, "Is the student residing in University Hostel? (yes/no): ");
        short credits = (short) readInt(scanner, "Enter Registered Course Credits (10-30): ", 10, 30);
        float scholarship = (float) readDouble(scanner, "Enter Scholarship Discount Percentage (0 - 100): ", 0.0, 100.0);

        System.out.println("\nEnter Marks for 5 Core Subjects (0 - 100 each):");
        int[] marks = new int[GradeCalculator.SUBJECT_COUNT];
        for (int i = 0; i < GradeCalculator.SUBJECT_COUNT; i++) {
            marks[i] = readInt(scanner, "  Subject " + (i + 1) + " Marks: ", 0, GradeCalculator.MAX_MARKS_PER_SUBJECT);
        }

        int totalMarks = GradeCalculator.calculateTotal(marks);
        double percentage = GradeCalculator.calculatePercentage(marks);
        char grade = GradeCalculator.calculateGrade(percentage);
        double feeDue = FeeCalculator.calculateSemesterFee(credits, selectedDept, isHosteller, scholarship);
        String autoId = StudentService.generateNextIdSafe();

        Student newStudent = new Student(autoId, name, age, srn, selectedDept, semester, marks, credits, isHosteller, scholarship);
        newStudent.setGrade(grade);
        newStudent.setFeeDue(feeDue);

        if (service.addStudent(newStudent)) {
            System.out.println("\n[SUCCESS] Student Record Successfully Created!");
            System.out.printf("  -> Assigned Student ID : %s\n", autoId);
            System.out.printf("  -> Total Marks         : %d / %d (%.2f%%)\n", totalMarks, GradeCalculator.MAX_TOTAL_MARKS, percentage);
            System.out.printf("  -> Computed Grade      : %c (%s)\n", grade, GradeCalculator.getGradeDescription(grade));
            System.out.printf("  -> Computed Semester Fee: INR %.2f\n", feeDue);

            FeeCalculator.printFeeBreakdown(credits, selectedDept, isHosteller, scholarship);
        }
    }

    private static void handleDisplayAll(StudentService service) {
        service.displayAllStudentsTable();
    }

    private static void handleSearchBySrn(Scanner scanner, StudentService service) {
        System.out.println("\n--- [SEARCH BY SRN] ---");
        String srn = readString(scanner, "Enter SRN to search (e.g. R24CS001): ");
        Student s = service.findStudentBySrn(srn);
        if (s != null) {
            System.out.println("\n[MATCH FOUND]:");
            s.displayDetails();
        } else {
            System.out.println("[INFO] No student record matches SRN: '" + srn + "'.");
        }
    }

    private static void handleSearchByName(Scanner scanner, StudentService service) {
        System.out.println("\n--- [SEARCH BY PARTIAL NAME] ---");
        String query = readString(scanner, "Enter name or partial letters: ");
        Student[] matches = service.searchStudentsByName(query);
        if (matches.length > 0) {
            System.out.println("\n[FOUND " + matches.length + " MATCHING RECORD(S)]:");
            for (Student s : matches) {
                s.displayDetails();
            }
        } else {
            System.out.println("[INFO] No students found matching search term: \"" + query + "\".");
        }
    }

    private static void handleFeePayment(Scanner scanner, StudentService service) {
        System.out.println("\n------------------------------------------------------------");
        System.out.println("            CALCULATE / RECORD SEMESTER FEE");
        System.out.println("------------------------------------------------------------");
        String srn = readString(scanner, "Enter Student SRN: ");
        Student s = service.findStudentBySrn(srn);
        if (s == null) {
            System.out.println("[ERROR] Student with SRN '" + srn + "' not found.");
            return;
        }

        double calculatedFee = FeeCalculator.calculateSemesterFee(s);
        System.out.println("\n----------------- Student Fee Assessment -------------------");
        System.out.printf("Student       : %s\n", s.getName());
        System.out.printf("SRN           : %s\n", s.getSrn());
        System.out.printf("Department    : %s\n", s.getDepartment().getFullName());
        System.out.printf("Semester      : %d\n", s.getSemester());
        System.out.printf("Credits       : %d (Base Rate: INR %.2f/credit)\n", s.getTotalCredits(), s.getDepartment().getBaseCreditRate());
        System.out.printf("Hosteller     : %s\n", (s.isHosteller() ? "YES" : "NO"));
        System.out.printf("Scholarship   : %.1f%%\n", s.getScholarshipPercent());
        System.out.printf("Semester Fee  : INR %.2f\n", calculatedFee);
        System.out.printf("Current Due   : INR %.2f\n", s.getFeeDue());
        System.out.println("------------------------------------------------------------");

        if (s.getFeeDue() <= 0.0) {
            System.out.println("[INFO] This student has zero outstanding fee balance.");
            return;
        }

        double paymentAmount = readDouble(scanner, "Enter Payment Amount (INR): ", 1.0, s.getFeeDue());
        double previousDue = s.getFeeDue();
        boolean success = service.processFeePayment(srn, paymentAmount);

        if (success) {
            System.out.println("\n------------------------------------------------------------");
            System.out.println("                        FEE PAYMENT RECEIPT");
            System.out.println("------------------------------------------------------------");
            System.out.printf("Student       : %s\n", s.getName());
            System.out.printf("SRN           : %s\n", s.getSrn());
            System.out.printf("Total Fee     : INR %.2f\n", calculatedFee);
            System.out.printf("Previous Due  : INR %.2f\n", previousDue);
            System.out.printf("Payment       : INR %.2f\n", paymentAmount);
            System.out.printf("Remaining Due : INR %.2f\n", s.getFeeDue());
            System.out.println("------------------------------------------------------------\n");
        }
    }

    private static void handleRemoveStudent(Scanner scanner, StudentService service) {
        System.out.println("\n--- [REMOVE STUDENT RECORD] ---");
        String srn = readString(scanner, "Enter SRN of student to remove: ");
        Student target = service.findStudentBySrn(srn);
        if (target == null) {
            System.out.println("[ERROR] Student with SRN '" + srn + "' not found.");
            return;
        }

        System.out.printf("Confirm removal of '%s' (SRN: %s)?\n", target.getName(), target.getSrn());
        boolean confirm = readBoolean(scanner, "Type 'yes' to confirm or 'no' to cancel: ");
        if (confirm) {
            boolean removed = service.removeStudentBySrn(srn);
            if (removed) {
                System.out.println("[SUCCESS] Student record '" + srn + "' removed from database.");
            } else {
                System.out.println("[ERROR] Failed to remove student record.");
            }
        } else {
            System.out.println("[INFO] Removal cancelled by user.");
        }
    }

    private static void handleSortStudents(Scanner scanner, StudentService service) {
        System.out.println("\n--- [SORT STUDENT RECORDS] ---");
        System.out.println("Select Sorting Criteria:");
        System.out.println("  1. Sort by Student Name (Alphabetical)");
        System.out.println("  2. Sort by Student SRN");
        System.out.println("  3. Sort by Semester");
        System.out.println("  4. Sort by Fee Due (Ascending)");
        System.out.println("  0. Return to Main Menu");
        int sortChoice = readInt(scanner, "Enter sorting choice (0-4): ", 0, 4);

        switch (sortChoice) {
            case 1:
                service.sortStudentsByName();
                System.out.println("[SUCCESS] Records sorted alphabetically by Student Name.");
                service.displayAllStudentsTable();
                break;
            case 2:
                service.sortStudentsBySrn();
                System.out.println("[SUCCESS] Records sorted by Student SRN.");
                service.displayAllStudentsTable();
                break;
            case 3:
                service.sortStudentsBySemester();
                System.out.println("[SUCCESS] Records sorted by Semester.");
                service.displayAllStudentsTable();
                break;
            case 4:
                service.sortStudentsByFeeDue();
                System.out.println("[SUCCESS] Records sorted by Fee Due (Ascending).");
                service.displayAllStudentsTable();
                break;
            case 0:
                break;
        }
    }

    private static void handleFeeAnalytics(StudentService service) {
        service.displayDepartmentFeeAnalytics();
    }

    private static void handlePersonnelDirectory(StudentService service) {
        service.displayPersonnelDirectory();
    }

    private static void handleOOPDemonstrations(Scanner scanner, StudentService service) {
        boolean inEvaluatorMenu = true;
        while (inEvaluatorMenu) {
            System.out.println("\n============================================================");
            System.out.println("             OOP & JAVA EVALUATOR MODE");
            System.out.println("============================================================\n");
            System.out.println(" 1.  Data Types, Variables & Scope");
            System.out.println(" 2.  Operators & Operator Precedence");
            System.out.println(" 3.  Type Conversion & Casting");
            System.out.println(" 4.  Control Flow & Jump Statements");
            System.out.println(" 5.  Arrays & String Methods");
            System.out.println(" 6.  Constructors & Method Overloading");
            System.out.println(" 7.  Static Members & Parameter Passing");
            System.out.println(" 8.  Inheritance, super & final");
            System.out.println(" 9.  Polymorphism & Dynamic Binding");
            System.out.println("10.  Abstract Classes & Methods");
            System.out.println("11.  Interfaces & Interface References");
            System.out.println("12.  Object Class Methods");
            System.out.println("13.  Packages & CLASSPATH");
            System.out.println("14.  Run All Syllabus Demonstrations");
            System.out.println(" 0.  Back to Main Menu\n");
            System.out.println("============================================================");

            int demoChoice = readInt(scanner, "Enter your choice: ", 0, 14);
            switch (demoChoice) {
                case 1:
                    demonstrateDataTypesAndScope();
                    break;
                case 2:
                    demonstrateOperatorsAndPrecedence(service);
                    break;
                case 3:
                    demonstrateTypeConversionAndCasting();
                    break;
                case 4:
                    demonstrateControlFlow();
                    break;
                case 5:
                    demonstrateArraysAndStrings(service);
                    break;
                case 6:
                    demonstrateConstructorsAndOverloading(service);
                    break;
                case 7:
                    demonstrateStaticAndParameterPassing(service);
                    break;
                case 8:
                    demonstrateInheritanceAndSuper();
                    break;
                case 9:
                    service.demonstratePolymorphicDispatch();
                    break;
                case 10:
                    demonstrateAbstractClasses();
                    break;
                case 11:
                    demonstrateInterfaces(service);
                    break;
                case 12:
                    demonstrateObjectMethods();
                    break;
                case 13:
                    demonstratePackagesAndClasspath();
                    break;
                case 14:
                    runAllSyllabusDemonstrations(service);
                    break;
                case 0:
                    inEvaluatorMenu = false;
                    break;
            }
        }
    }


    private static void demonstrateDataTypesAndScope() {
        System.out.println("\n============================================================");
        System.out.println(" 1. DATA TYPES, VARIABLES & SCOPE DEMONSTRATION");
        System.out.println("============================================================");
        System.out.println("Comprehensive coverage of Java 8 primitive data types:");
        byte sem = 5;
        short credits = 24;
        int age = 20;
        long admissionSeq = 2026000001L;
        float scholarship = 15.0f;
        double fee = 105315.00;
        char grade = 'S';
        boolean hosteller = true;

        System.out.printf("  1. byte    (8-bit)   : semester = %d\n", sem);
        System.out.printf("  2. short   (16-bit)  : credits = %d\n", credits);
        System.out.printf("  3. int     (32-bit)  : age = %d\n", age);
        System.out.printf("  4. long    (64-bit)  : admissionSequence = %d\n", admissionSeq);
        System.out.printf("  5. float   (32-bit)  : scholarship = %.1f%%\n", scholarship);
        System.out.printf("  6. double  (64-bit)  : fee = INR %.2f\n", fee);
        System.out.printf("  7. char    (16-bit)  : grade = '%c'\n", grade);
        System.out.printf("  8. boolean (1-bit)   : hosteller = %b\n", hosteller);

        System.out.println("\nVariable Scope Demonstration:");
        System.out.println("  - Static Scope   : Shared at class level (e.g., StudentService.MAX_STUDENTS = " + StudentService.MAX_STUDENTS + ")");
        System.out.println("  - Instance Scope : Tied to object lifecycle (e.g., this.name, this.feeDue)");
        System.out.println("  - Local Scope    : Confined to enclosing method/block (e.g., local method variables)");
        System.out.println("============================================================\n");
    }

    private static void demonstrateOperatorsAndPrecedence(StudentService service) {
        System.out.println("\n============================================================");
        System.out.println(" 2. OPERATORS & OPERATOR PRECEDENCE DEMONSTRATION");
        System.out.println("============================================================");
        FeeCalculator.demonstrateOperatorPrecedence();

        System.out.println("\nBitwise Operators Demonstration using Student Status Bitmask:");
        Student target = service.findStudentBySrn("R24CS001");
        if (target == null && service.getCount() > 0) {
            target = service.getAllStudents()[0];
        }

        if (target != null) {
            System.out.println("Target: " + target.getName() + " (" + target.getSrn() + ")");
            int mask = 0;
            if (target.getFeeDue() == 0.0) mask |= Student.FLAG_TUITION_CLEARED;
            if (target.isHosteller())      mask |= Student.FLAG_HOSTELLER;
            if (target.getScholarshipPercent() > 0.0f) mask |= Student.FLAG_SCHOLARSHIP;
            mask |= Student.FLAG_DEAN_LIST;

            System.out.println("1. Status Bitmask (Integer)  : " + mask + " (Binary: " + Integer.toBinaryString(mask) + ")");
            System.out.println("2. Left Shift (1 << 2)       : " + (1 << 2) + " (Scholarship Flag constant)");
            System.out.println("3. Bitwise AND (mask & 2)    : " + (mask & Student.FLAG_HOSTELLER)
                               + " (Hosteller Flag check: " + ((mask & Student.FLAG_HOSTELLER) != 0 ? "YES" : "NO") + ")");
            System.out.println("4. Bitwise OR (mask | 8)     : " + (mask | Student.FLAG_DEAN_LIST) + " (Setting Dean's List flag)");
            System.out.println("5. Bitwise XOR (mask ^ 1)    : " + (mask ^ Student.FLAG_TUITION_CLEARED) + " (Toggling Tuition flag)");
            System.out.println("6. Bitwise NOT (~mask)       : " + (~mask) + " (Inverted bitmask)");
            System.out.println("7. Right Shift (mask >> 1)   : " + (mask >> 1) + " (Shift right by 1 bit)");
        }
        System.out.println("============================================================\n");
    }

    private static void demonstrateTypeConversionAndCasting() {
        System.out.println("\n============================================================");
        System.out.println(" 3. TYPE CONVERSION & CASTING DEMONSTRATION");
        System.out.println("============================================================");
        short credits = 24;
        double creditRate = 3200.0;
        double tuition = credits * creditRate;
        System.out.println("1. Implicit Widening Conversion:");
        System.out.printf("   short credits (%d) * double rate (%.2f) = double tuition (%.2f)\n", credits, creditRate, tuition);

        int totalMarks = 455;
        int maxMarks = 500;
        double percentage = ((double) totalMarks / maxMarks) * 100.0;
        System.out.println("\n2. Explicit Widening Cast:");
        System.out.printf("   ((double) %d / %d) * 100.0 = %.2f%%\n", totalMarks, maxMarks, percentage);

        Person p = new Student("Demo Student", 20, "R24DEMO", Department.CSE, (byte) 5, false);
        System.out.println("\n3. Reference Downcasting with instanceof Check:");
        if (p instanceof Student s) {
            System.out.println("   Safe downcasting: Person reference successfully cast to Student (SRN: " + s.getSrn() + ")");
        }
        System.out.println("============================================================\n");
    }

    private static void demonstrateControlFlow() {
        System.out.println("\n============================================================");
        System.out.println(" 4. CONTROL FLOW & JUMP STATEMENTS DEMONSTRATION");
        System.out.println("============================================================");
        System.out.println("1. Selection Statements:");
        System.out.println("   - if-else-if  : Academic letter grading scale (S, A, B, C, D, F)");
        System.out.println("   - switch-case : Menu dispatcher and GradeCalculator.getGradeDescription()");

        System.out.println("\n2. Iteration Statements:");
        System.out.println("   - while       : Interactive menu driver and StudentService.calculateAverageFeeDue()");
        System.out.println("   - do-while    : Console input verification in readInt(), readDouble(), readString()");
        System.out.println("   - for loop    : In-place Bubble Sort and array left-shifting");
        System.out.println("   - for-each    : Enhanced traversal over Department.values() and Student marks");

        System.out.println("\n3. Jump Statements:");
        System.out.println("   - break       : Switch-case exits and early termination of SRN linear search");
        System.out.println("   - continue    : Skipping non-matching records during partial name search");
        System.out.println("   - return      : Method exit and returning calculated values");
        System.out.println("============================================================\n");
    }

    private static void demonstrateArraysAndStrings(StudentService service) {
        System.out.println("\n============================================================");
        System.out.println(" 5. ARRAYS & STRING METHODS DEMONSTRATION");
        System.out.println("============================================================");
        System.out.println("1. Arrays in SMS:");
        System.out.println("   - Primitive array : int[] marks = new int[5] (5 core academic subject marks)");
        System.out.println("   - Object array    : Student[] students = new Student[100] (in-memory storage)");
        System.out.println("   - Bubble Sort     : In-place lexicographical swapping on Student references");
        System.out.println("   - Array Shift     : Left-shifting elements on deletion to maintain contiguity");
        System.out.println("   - Defensive Copy  : Arrays.copyOf() ensuring encapsulation integrity");

        System.out.println("\n2. java.lang.String Class Exploration:");
        service.demonstrateStringExploration("  REVA University Student Management System  ");
    }

    private static void demonstrateConstructorsAndOverloading(StudentService service) {
        System.out.println("\n============================================================");
        System.out.println(" 6. CONSTRUCTORS & METHOD OVERLOADING DEMONSTRATION");
        System.out.println("============================================================");
        System.out.println("1. Constructor Overloading & Chaining (this):");
        Person pDefault = new Person() {
            @Override public void displayDetails() { System.out.println("Anonymous Person"); }
        };
        System.out.println("   - Default Constructor chained via this(...) : " + pDefault.getId() + " | " + pDefault.getName());
        System.out.println("   - Subclass Constructor chained via super(...) : Student(id, name, age, ...)");

        System.out.println("\n2. Method Overloading (Compile-Time Polymorphism):");
        System.out.println("   a) GradeCalculator.calculateGrade():");
        System.out.printf("      - By percentage (85.0)       : Grade %c\n", GradeCalculator.calculateGrade(85.0));
        System.out.printf("      - By marks array ([95..94])  : Grade %c\n", GradeCalculator.calculateGrade(new int[] {95, 92, 98, 89, 94}));
        System.out.printf("      - By total and max (65, 100) : Grade %c\n", GradeCalculator.calculateGrade(65, 100));

        System.out.println("   b) FeeCalculator.calculateSemesterFee():");
        System.out.printf("      - By (credits, dept)              : INR %.2f\n", FeeCalculator.calculateSemesterFee((short) 20, Department.CSE));
        System.out.printf("      - By (credits, dept, host, schol) : INR %.2f\n", FeeCalculator.calculateSemesterFee((short) 20, Department.CSE, true, 25.0f));

        System.out.println("   c) StudentService.searchStudents():");
        System.out.printf("      - By Department enum (CSE) : %d match(es)\n", service.searchStudents(Department.CSE).length);
        System.out.printf("      - By Name query (\"Arjun\")   : %d match(es)\n", service.searchStudents("Arjun").length);
        System.out.println("============================================================\n");
    }

    private static void demonstrateStaticAndParameterPassing(StudentService service) {
        System.out.println("\n============================================================");
        System.out.println(" 7. STATIC MEMBERS & PARAMETER PASSING DEMONSTRATION");
        System.out.println("============================================================");
        System.out.println("1. Static Fields and Methods:");
        System.out.println("   - Person.getTotalPersonInstances() : " + Person.getTotalPersonInstances());
        System.out.println("   - StudentService.generateNextIdSafe(): " + StudentService.generateNextIdSafe());
        System.out.println("   - FeeCalculator.BASE_HOSTEL_FEE    : INR " + FeeCalculator.BASE_HOSTEL_FEE);

        System.out.println("\n2. Parameter Passing Semantics (Pass-by-Value):");
        Student sampleStudent = service.findStudentBySrn("R24CS001");
        service.demonstrateParameterPassing(20, sampleStudent);
    }

    private static void demonstrateInheritanceAndSuper() {
        System.out.println("\n============================================================");
        System.out.println(" 8. INHERITANCE, SUPER & FINAL KEYWORD DEMONSTRATION");
        System.out.println("============================================================");
        System.out.println("1. Hierarchical Inheritance:");
        System.out.println("   Person (Abstract Superclass)");
        System.out.println("    ├── Student (Subclass extending Person, adds srn, marks, grade)");
        System.out.println("    └── Faculty (Subclass extending Person, adds designation, specialization)");

        System.out.println("\n2. 'super' Keyword:");
        System.out.println("   - Constructor invocation : super(id, name, age) passes common identity to Person");
        System.out.println("   - Method invocation      : super.toString() reuses superclass string representation");

        System.out.println("\n3. 'final' Keyword:");
        System.out.println("   - final class   : GradeCalculator cannot be extended (prevents tampering with grading rules)");
        System.out.println("   - final method  : Person.getId() cannot be overridden (secures unique entity identity)");
        System.out.println("   - final field   : Person.id is immutable once initialized");
        System.out.println("============================================================\n");
    }

    private static void demonstrateAbstractClasses() {
        System.out.println("\n============================================================");
        System.out.println(" 10. ABSTRACT CLASSES & METHODS DEMONSTRATION");
        System.out.println("============================================================");
        System.out.println("1. Abstract Class Definition:");
        System.out.println("   - 'public abstract class Person' establishes a high-level abstraction.");
        System.out.println("   - Direct instantiation 'new Person(...)' is strictly prohibited by compiler.");

        System.out.println("\n2. Abstract Method Contract:");
        System.out.println("   - 'public abstract void displayDetails();' enforces concrete implementation");
        System.out.println("     in every derived subclass (Student and Faculty).");
        System.out.println("============================================================\n");
    }

    private static void demonstrateInterfaces(StudentService service) {
        System.out.println("\n============================================================");
        System.out.println(" 11. INTERFACES & INTERFACE REFERENCES DEMONSTRATION");
        System.out.println("============================================================");
        System.out.println("1. Interface Contract & Inheritance:");
        System.out.println("   - Reportable defines getReportHeader() default method and UNIVERSITY_TAG.");
        System.out.println("   - Payable extends Reportable (Multiple inheritance of behavior).");
        System.out.println("   - Student implements Payable (thus also fulfilling Reportable).");

        System.out.println("\n2. Interface Reference Access:");
        Student s = service.findStudentBySrn("R24CS001");
        if (s == null && service.getCount() > 0) {
            s = service.getAllStudents()[0];
        }

        if (s != null) {
            Reportable rep = s;
            System.out.println("Invoking rep.generateReport() via Reportable reference:");
            System.out.println(rep.generateReport());

            Payable pay = s;
            System.out.println("Invoking pay.getDueAmount() via Payable reference: INR " + pay.getDueAmount());
        }
        System.out.println("============================================================\n");
    }

    private static void demonstrateObjectMethods() {
        System.out.println("\n============================================================");
        System.out.println(" 12. OBJECT CLASS METHODS OVERRIDES DEMONSTRATION");
        System.out.println("============================================================");
        System.out.println("1. Student Object Class Method Overrides (Keyed on SRN):");
        Student s1 = new Student("Arjun", 20, "R24CS001", Department.CSE, (byte) 5, false);
        Student s2 = new Student("Arjun Nair", 20, "R24CS001", Department.CSE, (byte) 5, false);
        Student s3 = new Student("Sneha", 20, "R24EC001", Department.ECE, (byte) 5, true);

        System.out.println("   s1.toString() : " + s1);
        System.out.println("   s2.toString() : " + s2);
        System.out.println("   s1.equals(s2) [Same SRN]       : " + s1.equals(s2) + " (Expected: true)");
        System.out.println("   s1.equals(s3) [Different SRN]  : " + s1.equals(s3) + " (Expected: false)");
        System.out.println("   s1.hashCode() == s2.hashCode() : " + (s1.hashCode() == s2.hashCode()));

        System.out.println("\n2. Faculty Object Class Method Overrides (Keyed on ID):");
        Faculty f1 = new Faculty("FAC-CSE-01", "Dr. Vikram Shah", 48, "Professor & HOD", Department.CSE, "AI & ML");
        Faculty f2 = new Faculty("FAC-CSE-01", "Prof. V. Shah", 48, "HOD", Department.CSE, "AI");
        Faculty f3 = new Faculty("FAC-ECE-01", "Dr. Rajiv Nair", 45, "Dean", Department.ECE, "VLSI");

        System.out.println("   f1.toString() : " + f1);
        System.out.println("   f2.toString() : " + f2);
        System.out.println("   f1.equals(f2) [Same Faculty ID]     : " + f1.equals(f2) + " (Expected: true)");
        System.out.println("   f1.equals(f3) [Different Faculty ID]: " + f1.equals(f3) + " (Expected: false)");
        System.out.println("   f1.hashCode() == f2.hashCode()      : " + (f1.hashCode() == f2.hashCode()));
        System.out.println("============================================================\n");
    }

    private static void demonstratePackagesAndClasspath() {
        System.out.println("\n============================================================");
        System.out.println(" 13. PACKAGES & CLASSPATH ARCHITECTURE DEMONSTRATION");
        System.out.println("============================================================");
        System.out.println("1. Package Modularity:");
        System.out.println("   - com.reva.sms.model   : Entity models (Person, Student, Faculty, Department, Payable, Reportable)");
        System.out.println("   - com.reva.sms.service : Business logic (StudentService, FeeCalculator, GradeCalculator)");
        System.out.println("   - com.reva.sms.main    : Terminal user interface (Main)");

        System.out.println("\n2. Java CLASSPATH Configuration:");
        System.out.println("   - Compilation : javac -d bin -sourcepath src src/com/reva/sms/**/*.java");
        System.out.println("   - Execution   : java -cp bin com.reva.sms.main.Main");
        System.out.println("   - Classloader resolves package-qualified classes relative to classpath root 'bin'.");
        System.out.println("============================================================\n");
    }

    private static void runAllSyllabusDemonstrations(StudentService service) {
        System.out.println("\n============================================================");
        System.out.println("    EXECUTING ALL SYLLABUS FEATURE DEMONSTRATIONS (1 - 13)   ");
        System.out.println("============================================================");
        demonstrateDataTypesAndScope();
        demonstrateOperatorsAndPrecedence(service);
        demonstrateTypeConversionAndCasting();
        demonstrateControlFlow();
        demonstrateArraysAndStrings(service);
        demonstrateConstructorsAndOverloading(service);
        demonstrateStaticAndParameterPassing(service);
        demonstrateInheritanceAndSuper();
        service.demonstratePolymorphicDispatch();
        demonstrateAbstractClasses();
        demonstrateInterfaces(service);
        demonstrateObjectMethods();
        demonstratePackagesAndClasspath();
        System.out.println("============================================================");
        System.out.println("    ALL SYLLABUS FEATURE DEMONSTRATIONS COMPLETED!          ");
        System.out.println("============================================================\n");
    }


    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        int value = 0;
        boolean valid;
        do {
            valid = false;
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    valid = true;
                } else {
                    System.out.printf("[ERROR] Input out of range! Please enter an integer between %d and %d.\n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid numeric input! Please enter a valid whole number.");
            }
        } while (!valid);
        return value;
    }

    public static double readDouble(Scanner scanner, String prompt, double min, double max) {
        double value = 0.0;
        boolean valid;
        do {
            valid = false;
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                value = Double.parseDouble(line);
                if (value >= min && value <= max) {
                    valid = true;
                } else {
                    System.out.printf("[ERROR] Input out of range! Please enter a number between %.2f and %.2f.\n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid floating-point input! Please enter a valid decimal number.");
            }
        } while (!valid);
        return value;
    }

    public static String readString(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("[ERROR] Field cannot be empty! Please enter a non-blank value.");
            }
        } while (input.isEmpty());
        return input;
    }

    public static boolean readBoolean(Scanner scanner, String prompt) {
        Boolean result;
        do {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes") || input.equals("y") || input.equals("true") || input.equals("1")) {
                result = Boolean.TRUE;
            } else if (input.equals("no") || input.equals("n") || input.equals("false") || input.equals("0")) {
                result = Boolean.FALSE;
            } else {
                result = null;
                System.out.println("[ERROR] Invalid input! Please enter 'yes' or 'no'.");
            }
        } while (result == null);
        return result;
    }
}
