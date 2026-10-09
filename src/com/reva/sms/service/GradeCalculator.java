package com.reva.sms.service;

public final class GradeCalculator {
    public static final int SUBJECT_COUNT = 5;
    public static final int MAX_MARKS_PER_SUBJECT = 100;
    public static final int MAX_TOTAL_MARKS = SUBJECT_COUNT * MAX_MARKS_PER_SUBJECT;
    public static final double MIN_PASS_PERCENTAGE = 40.0;

    private GradeCalculator() {
    }

    public static int calculateTotal(int[] marks) {
        if (marks == null) {
            return 0;
        }
        int total = 0;
        for (int m : marks) {
            total += m;
        }
        return total;
    }

    public static double calculatePercentage(int[] marks) {
        if (marks == null || marks.length == 0) {
            return 0.0;
        }
        int total = calculateTotal(marks);
        double percentage = ((double) total / (marks.length * MAX_MARKS_PER_SUBJECT)) * 100.0;

        double rounded = Math.round(percentage * 100.0) / 100.0;
        return rounded;
    }

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

    public static char calculateGrade(int[] marks) {
        return calculateGrade(calculatePercentage(marks));
    }

    public static char calculateGrade(int total, int max) {
        if (max <= 0) {
            return 'F';
        }
        double percentage = ((double) total / max) * 100.0;
        return calculateGrade(percentage);
    }

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

    public static final boolean isPassingGrade(char grade) {
        char upper = Character.toUpperCase(grade);
        return upper == 'O' || upper == 'A' || upper == 'B' || upper == 'C' || upper == 'D';
    }
}
