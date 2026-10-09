package com.reva.sms.model;

public interface Payable extends Reportable {
    void payFee(double amount);

    double getDueAmount();
}
