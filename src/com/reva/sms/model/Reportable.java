// FEATURE: Packages - Defining a package
package com.reva.sms.model;

/**
 * FEATURE: Interface - Defining an interface
 * FEATURE: Data abstraction - Interface specifies WHAT to do, not HOW to do it
 * FEATURE: Interfaces VS Abstract classes - Interfaces define a pure contract (can be multiple-implemented),
 *          whereas abstract classes can provide state and partial implementation.
 * 
 * Reportable interface establishes a contract for any university entity capable
 * of generating a structured textual summary report.
 * 
 * Course Context: REVA University, B.Sc. (BSTCs), Semester V
 * Java Programming (Units I & II Mini Project)
 */
public interface Reportable {
    // FEATURE: Variables and constants - Interface constant (implicitly public static final)
    String UNIVERSITY_TAG = "REVA UNIVERSITY - BUCKET OF EXCELLENCE";

    // FEATURE: Methods - Abstract interface method (implicitly public abstract)
    String generateReport();

    // FEATURE: Methods - Default method in interface (since Java 8)
    default String getReportHeader() {
        return "=== " + UNIVERSITY_TAG + " ===";
    }
}
