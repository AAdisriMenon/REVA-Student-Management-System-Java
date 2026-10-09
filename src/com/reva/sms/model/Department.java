package com.reva.sms.model;

public enum Department {
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

    private final String code;
    private final String fullName;
    private final double baseCreditRate;

    Department(String code, String fullName, double baseCreditRate) {
        this.code = code;
        this.fullName = fullName;
        this.baseCreditRate = baseCreditRate;
    }

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public double getBaseCreditRate() {
        return baseCreditRate;
    }

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

    @Override
    public String toString() {
        return this.fullName + " (" + this.code + ")";
    }
}
