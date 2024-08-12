/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rmi;

public class PayrollCalculator {

    public double calculateGrossPay(double salary, double hoursWorked) {
        double grossPay = 0.0;
        // Implement logic to calculate gross pay based on provided parameters
        grossPay = salary * hoursWorked;
        return grossPay;
    }

    public double calculateDeduction(double grossPay, double taxRate, double insuranceRate) {
        double totalDeductions = 0.0;
        // Implement logic to calculate deductions based on gross pay and other parameters
        double taxDeduction = grossPay * taxRate;
        double insuranceDeduction = grossPay * insuranceRate;
        totalDeductions = taxDeduction + insuranceDeduction;
        return totalDeductions;
    }

    public double calculateNetPay(double grossPay, double deductions) {
        return grossPay - deductions;
    }
}