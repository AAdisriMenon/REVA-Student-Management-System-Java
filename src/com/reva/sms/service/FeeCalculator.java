package com.reva.sms.service;

import com.reva.sms.model.Department;
import com.reva.sms.model.Student;

public class FeeCalculator {
    public static final double BASE_HOSTEL_FEE = 45000.00;
    public static final double CAMPUS_AMENITIES_FEE = 8500.00;
    public static final double MIN_SCHOLARSHIP_PERCENT = 0.0;
    public static final double MAX_SCHOLARSHIP_PERCENT = 100.0;

    public static double calculateSemesterFee(short credits, Department department, boolean isHosteller, float scholarshipPercent) {
        Department dept = (department != null) ? department : Department.CSE;

        double tuitionFee = credits * dept.getBaseCreditRate();

        double hostelFee = isHosteller ? BASE_HOSTEL_FEE : 0.0;

        double grossFee = tuitionFee + hostelFee + CAMPUS_AMENITIES_FEE;

        float validScholarship = scholarshipPercent;
        if (validScholarship < (float) MIN_SCHOLARSHIP_PERCENT) {
            validScholarship = 0.0f;
        } else if (validScholarship > (float) MAX_SCHOLARSHIP_PERCENT) {
            validScholarship = 100.0f;
        }

        double discountAmount = grossFee * ((double) validScholarship / 100.0);

        double netFee = grossFee - discountAmount;

        return Math.round(netFee * 100.0) / 100.0;
    }

    public static double calculateSemesterFee(short credits, Department department) {
        return calculateSemesterFee(credits, department, false, 0.0f);
    }

    public static double calculateSemesterFee(Student student) {
        return calculateSemesterFee(student.getTotalCredits(), student.getDepartment(),
                                     student.isHosteller(), student.getScholarshipPercent());
    }

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

    public static void demonstrateOperatorPrecedence() {
        System.out.println("--- Operator Precedence Demonstration ---");
        int precedenceResult = 10 + 5 * 2;

        int parenthesesResult = (10 + 5) * 2;

        System.out.println("10 + 5 * 2 = " + precedenceResult);
        System.out.println("(10 + 5) * 2 = " + parenthesesResult);
    }
}
