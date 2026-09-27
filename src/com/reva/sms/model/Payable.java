// FEATURE: Packages - Defining a package
package com.reva.sms.model;

/**
 * FEATURE: Interface - Extending interface (Interface inheritance)
 * FEATURE: Multiple inheritance through interfaces - An interface can extend another interface
 * FEATURE: Data abstraction
 * 
 * Payable extends Reportable to create a unified contract for billable academic entities.
 * Any class implementing Payable must provide concrete implementations for fee processing
 * as well as report generation.
 * 
 * Course Context: REVA University, B.Sc. (BSTCs), Semester V
 * Java Programming (Units I & II Mini Project)
 */
public interface Payable extends Reportable {
    // FEATURE: Methods - Interface abstract methods
    // FEATURE: Parameter passing - passing primitive double by value
    void payFee(double amount);

    // FEATURE: Methods - Interface method returning primitive double
    double getDueAmount();
}
