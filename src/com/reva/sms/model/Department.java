// FEATURE: Packages - Defining a package
package com.reva.sms.model;

/**
 * FEATURE: Enumerated types
 * FEATURE: Classes and objects (Enums in Java are specialized classes)
 * FEATURE: Access control (public enum with private fields and public methods)
 * 
 * Department represents academic departments within REVA University.
 * It encapsulates department code, full display name, and base tuition rate per credit.
 * 
 * Course Context: REVA University, B.Sc. (BSTCs), Semester V
 * Java Programming (Units I & II Mini Project)
 */
public enum Department {
    // FEATURE: Enumerated types - Enum constants
    CSE("CSE", "Computer Science & Engineering", 3200.0),
    ECE("ECE", "Electronics & Communication Engineering", 3000.0),
    MECH("MECH", "Mechanical Engineering", 2500.0),
    CIVIL("CIVIL", "Civil Engineering", 2400.0),
    ISE("ISE", "Information Science & Engineering", 3100.0),
    EEE("EEE", "Electrical & Electronics Engineering", 2800.0),
    BIOTECHNOLOGY("BT", "Biotechnology", 2700.0),
    ALLIED_HEALTH("AHS", "Allied Health Sciences", 2600.0),
    COMMERCE("COM", "School of Commerce", 2200.0),
    MANAGEMENT("MGT", "School of Management Studies", 2400.0),
    PERFORMING_ARTS("PA", "School of Performing Arts", 2000.0);

    // FEATURE: Encapsulation - Private final fields within enum
    // FEATURE: Variables and constants - final immutable instance variables
    // FEATURE: Data types - String (reference type) and double (primitive floating point)
    private final String code;
    private final String fullName;
    private final double baseCreditRate;

    // FEATURE: Constructors - Enum constructor (private by default in Java)
    // FEATURE: this reference - Resolving variable shadowing
    Department(String code, String fullName, double baseCreditRate) {
        this.code = code;
        this.fullName = fullName;
        this.baseCreditRate = baseCreditRate;
    }

    // FEATURE: Methods - Getter methods (Encapsulation)
    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public double getBaseCreditRate() {
        return baseCreditRate;
    }

    /**
     * FEATURE: Static fields and methods - Static utility method on enum
     * FEATURE: Control flow statements - Enhanced for loop
     * FEATURE: Jump statements - return
     * FEATURE: Exploring String class - equalsIgnoreCase(), trim(), isEmpty()
     * 
     * Finds a department by its code or enum name (case-insensitive).
     */
    public static Department fromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        String clean = input.trim();
        for (Department dept : Department.values()) {
            if (dept.name().equalsIgnoreCase(clean) || dept.code.equalsIgnoreCase(clean)) {
                return dept;
            }
        }
        return null;
    }

    // FEATURE: The Object class and its methods - Overriding toString()
    // FEATURE: Method overriding - Custom string representation
    @Override
    public String toString() {
        return this.fullName + " (" + this.code + ")";
    }
}
