// FEATURE: Packages - Defining a package
package com.reva.sms.service;

// FEATURE: Packages - Importing packages
import com.reva.sms.model.Department;
import com.reva.sms.model.Student;

/**
 * FEATURE: Classes and objects - Service class for academic fee computation
 * FEATURE: Encapsulation - Static service model
 * FEATURE: Operators, operator hierarchy, expressions - Complex fee formulas demonstrating operator precedence
 * 
 * Course Context: REVA University, B.Sc. (BSTCs), Semester V
 * Java Programming (Units I & II Mini Project)
 */
public class FeeCalculator {
    // FEATURE: Variables and constants - Public static final constants
    // FEATURE: Data types - double primitive constants
    public static final double BASE_HOSTEL_FEE = 45000.00;
    public static final double CAMPUS_AMENITIES_FEE = 8500.00;
    public static final double MIN_SCHOLARSHIP_PERCENT = 0.0;
    public static final double MAX_SCHOLARSHIP_PERCENT = 100.0;

    /**
     * FEATURE: Methods and parameter passing - Primitive data types passed by value
     * FEATURE: Data types - short, Department, boolean, float, double
     * FEATURE: Operators, operator hierarchy, expressions - Arithmetic, ternary, and relational operators
     * 
     * Computes the semester fee using the formula:
     *   Tuition = credits * Department.baseCreditRate
     *   Hostel = isHosteller ? BASE_HOSTEL_FEE : 0.0
     *   Gross Fee = (Tuition + Hostel + CAMPUS_AMENITIES_FEE)
     *   Discount = Gross Fee * (scholarshipPercent / 100.0f)
     *   Net Payable Fee = Gross Fee - Discount
     */
    public static double calculateSemesterFee(short credits, Department department, boolean isHosteller, float scholarshipPercent) {
        // FEATURE: Operators - Ternary operator for null fallback
        Department dept = (department != null) ? department : Department.CSE;
        
        // FEATURE: Type conversion and casting - Implicit widening from short credits to double in multiplication
        double tuitionFee = credits * dept.getBaseCreditRate();

        // FEATURE: Operators, operator hierarchy, expressions - Ternary conditional expression
        double hostelFee = isHosteller ? BASE_HOSTEL_FEE : 0.0;

        // FEATURE: Operators, operator hierarchy, expressions - Addition with operator precedence
        double grossFee = tuitionFee + hostelFee + CAMPUS_AMENITIES_FEE;

        // FEATURE: Control flow statements - Boundary clamp for scholarship
        float validScholarship = scholarshipPercent;
        if (validScholarship < (float) MIN_SCHOLARSHIP_PERCENT) {
            validScholarship = 0.0f;
        } else if (validScholarship > (float) MAX_SCHOLARSHIP_PERCENT) {
            validScholarship = 100.0f;
        }

        // FEATURE: Type conversion and casting - float to double arithmetic expression
        double discountAmount = grossFee * ((double) validScholarship / 100.0);

        // FEATURE: Operators - Subtraction
        double netFee = grossFee - discountAmount;

        // Rounding to nearest cent/paise
        return Math.round(netFee * 100.0) / 100.0;
    }

    /**
     * FEATURE: Methods - Method overloading (same name 'calculateSemesterFee', different parameter
     *          list from the 4-argument version above: assumes non-hosteller, no scholarship)
     * FEATURE: Polymorphism - Static/compile-time polymorphism via overload resolution
     * 
     * Overload 1: simplified 2-argument version for a day-scholar with no scholarship discount.
     */
    public static double calculateSemesterFee(short credits, Department department) {
        return calculateSemesterFee(credits, department, false, 0.0f);
    }

    /**
     * FEATURE: Methods - Method overloading (third distinct signature, taking an object reference)
     * FEATURE: Implement interfaces - Reading fields off a Student instance via its getters
     * 
     * Overload 3: derives all four inputs directly from an existing Student object.
     */
    public static double calculateSemesterFee(Student student) {
        return calculateSemesterFee(student.getTotalCredits(), student.getDepartment(),
                                     student.isHosteller(), student.getScholarshipPercent());
    }

    /**
     * FEATURE: Formatting output - Breakdown printout
     * FEATURE: Formatting output - printf with field widths and alignment
     */
    public static void printFeeBreakdown(short credits, Department dept, boolean isHosteller, float scholarshipPercent) {
        double tuition = credits * dept.getBaseCreditRate();
        double hostel = isHosteller ? BASE_HOSTEL_FEE : 0.0;
        double gross = tuition + hostel + CAMPUS_AMENITIES_FEE;
        double discount = gross * (scholarshipPercent / 100.0);
        double net = gross - discount;

        System.out.println("  ----------------- Fee Breakdown -----------------");
        System.out.printf("  %-25s: INR %10.2f (%d credits @ %.2f)\n", "Tuition Fee", tuition, credits, dept.getBaseCreditRate());
        System.out.printf("  %-25s: INR %10.2f\n", "Campus Amenities", CAMPUS_AMENITIES_FEE);
        System.out.printf("  %-25s: INR %10.2f\n", "Hostel & Mess Charge", hostel);
        System.out.printf("  %-25s: INR %10.2f\n", "Gross Total", gross);
        System.out.printf("  %-25s: INR %10.2f (%.1f%% discount)\n", "Scholarship Deduction", discount, scholarshipPercent);
        System.out.printf("  %-25s: INR %10.2f\n", "Net Semester Fee", net);
        System.out.println("  -------------------------------------------------");
    }

    /**
     * FEATURE: Operators, operator hierarchy, expressions - Operator precedence demonstration
     * Demonstrates operator precedence: multiplication (*) is evaluated before addition (+),
     * and parentheses change the order of evaluation.
     */
    public static void demonstrateOperatorPrecedence() {
        System.out.println("--- Operator Precedence Demonstration ---");
        // Demonstrates operator precedence: multiplication (*) is evaluated before addition (+)
        int precedenceResult = 10 + 5 * 2;

        // Parentheses change the order of evaluation
        int parenthesesResult = (10 + 5) * 2;

        System.out.println("10 + 5 * 2 = " + precedenceResult);
        System.out.println("(10 + 5) * 2 = " + parenthesesResult);
    }
}
