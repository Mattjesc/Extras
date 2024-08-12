/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rmi;

import java.io.Serializable;

public class PayrollInfo implements Serializable {
    private double grossPay;
    private double deductions;
    private double netPay;

    public PayrollInfo(double grossPay, double deductions, double netPay) {
        this.grossPay = grossPay;
        this.deductions = deductions;
        this.netPay = netPay;
    }

    public double getGrossPay() {
        return grossPay;
    }

    public double getDeductions() {
        return deductions;
    }

    public double getNetPay() {
        return netPay;
    }
}
