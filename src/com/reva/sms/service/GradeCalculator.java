// FEATURE: Packages - Defining a package
package com.reva.sms.service;

/**
 * FEATURE: Preventing inheritance: final classes and methods - 'final' class cannot be extended
 * FEATURE: Classes and objects - Utility service class with private constructor
 * FEATURE: Static fields and methods - Static grading utilities
 * FEATURE: Variables and constants - Public static final constants
 * 
 * GradeCalculator computes total marks, percentage, and assigns letter grades ('O', 'A', 'B', 'C', 'F')
 * according to the REVA University grading scale.
 * 
 * Course Context: REVA University, B.Sc. (BSTCs), Semester V
 * Java Programming (Units I & II Mini Project)
 */
public final class GradeCalculator {
    // FEATURE: Variables and constants - Constants defined with public static final
    // FEATURE: Scope and lifetime of variables - Class-level constants with application lifetime
    // FEATURE: Data types - int and double primitive data types
    public static final int SUBJECT_COUNT = 5;
    public static final int MAX_MARKS_PER_SUBJECT = 100;
    public static final int MAX_TOTAL_MARKS = SUBJECT_COUNT * MAX_MARKS_PER_SUBJECT; // 500
    public static final double MIN_PASS_PERCENTAGE = 40.0;

    // FEATURE: Access control - Private constructor prevents direct instantiation
    private GradeCalculator() {
        // Utility class pattern
    }

    /**
     * FEATURE: Methods - Static calculation method
     * FEATURE: Arrays - Single-dimensional integer array parameter
     * FEATURE: Scope and lifetime of variables - Local method variables
     * FEATURE: Operators, operator hierarchy, expressions - Arithmetic addition and compound assignment (+=)
     * FEATURE: Control flow statements - Enhanced for loop
     */
    public static int calculateTotal(int[] marks) {
        if (marks == null) {
            return 0;
        }
        int total = 0; // Local variable
        for (int m : marks) {
            total += m;
        }
        return total;
    }

    /**
     * FEATURE: Type conversion and casting - Explicit narrowing cast and implicit widening
     * FEATURE: Operators - Arithmetic division
     * 
     * Calculates percentage from marks array.
     */
    public static double calculatePercentage(int[] marks) {
        if (marks == null || marks.length == 0) {
            return 0.0;
        }
        int total = calculateTotal(marks);
        // FEATURE: Type conversion and casting - Explicitly casting int 'total' to double to avoid integer truncation
        double percentage = ((double) total / (marks.length * MAX_MARKS_PER_SUBJECT)) * 100.0;
        
        // FEATURE: Type conversion and casting - Rounding with explicit casting demonstration
        // Rounding to two decimal places:
        double rounded = Math.round(percentage * 100.0) / 100.0;
        return rounded;
    }

    /**
     * FEATURE: Control flow statements - Multi-branch if-else-if control flow
     * FEATURE: Operators, operator hierarchy, expressions - Relational and Logical AND (&&)
     * FEATURE: Data types - char primitive return type
     * 
     * Grade Scale (REVA University standard):
     *   >= 90% : 'O' (Outstanding)
     *   >= 80% : 'A' (Excellent)
     *   >= 70% : 'B' (Very Good)
     *   >= 60% : 'C' (Good)
     *   >= 40% : 'D' (Satisfactory / Pass)
     *    < 40% : 'F' (Fail)
     */
    public static char calculateGrade(double percentage) {
        if (percentage >= 90.0 && percentage <= 100.0) {
            return 'O';
        } else if (percentage >= 80.0 && percentage < 90.0) {
            return 'A';
        } else if (percentage >= 70.0 && percentage < 80.0) {
            return 'B';
        } else if (percentage >= 60.0 && percentage < 70.0) {
            return 'C';
        } else if (percentage >= 40.0 && percentage < 60.0) {
            return 'D';
        } else {
            return 'F';
        }
    }

    /**
     * FEATURE: Methods - Method overloading (same name 'calculateGrade', different parameter list
     *          from calculateGrade(double) above: this one takes the raw marks array)
     * FEATURE: Polymorphism - Static/compile-time polymorphism via overload resolution
     * 
     * Overload 2: derives percentage from a marks array, then delegates to calculateGrade(double).
     */
    public static char calculateGrade(int[] marks) {
        return calculateGrade(calculatePercentage(marks));
    }

    /**
     * FEATURE: Methods - Method overloading (same name 'calculateGrade', third distinct signature)
     * 
     * Overload 3: derives percentage from a raw total/max pair, then delegates to calculateGrade(double).
     */
    public static char calculateGrade(int total, int max) {
        if (max <= 0) {
            return 'F';
        }
        double percentage = ((double) total / max) * 100.0;
        return calculateGrade(percentage);
    }

    /**
     * FEATURE: Control flow statements - switch-case statement
     * FEATURE: Jump statements - break and return
     * 
     * Returns descriptive interpretation for a given letter grade.
     */
    public static String getGradeDescription(char grade) {
        String description;
        switch (Character.toUpperCase(grade)) {
            case 'O':
                description = "Outstanding Performance (Top Tier)";
                break;
            case 'A':
                description = "Excellent Performance";
                break;
            case 'B':
                description = "Very Good Performance";
                break;
            case 'C':
                description = "Good / Above Average Performance";
                break;
            case 'D':
                description = "Average / Passing Grade";
                break;
            case 'F':
                description = "Fail (Needs Re-examination)";
                break;
            default:
                description = "Unassigned / Pending Evaluation";
                break;
        }
        return description;
    }

    /**
     * FEATURE: Preventing inheritance: final classes and methods - Final method in final class
     * FEATURE: Operators - Logical OR (||)
     */
    public static final boolean isPassingGrade(char grade) {
        char upper = Character.toUpperCase(grade);
        return upper == 'O' || upper == 'A' || upper == 'B' || upper == 'C' || upper == 'D';
    }
}
