package com.reva.sms.model;

public interface Reportable {
    String UNIVERSITY_TAG = "REVA UNIVERSITY - BUCKET OF EXCELLENCE";

    String generateReport();

    default String getReportHeader() {
        return "=== " + UNIVERSITY_TAG + " ===";
    }
}
