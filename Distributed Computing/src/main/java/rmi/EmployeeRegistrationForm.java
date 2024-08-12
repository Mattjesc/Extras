/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
   
package rmi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.BorderLayout;

public class EmployeeRegistrationForm extends JFrame {
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField idTextField;
    private JButton registerButton;
    private JButton updateButton;
    private JButton payrollButton;
    private PayrollClient client;

    public EmployeeRegistrationForm(PayrollClient client) {
        this.client = client;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Employee Registration");

        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel idLabel = new JLabel("ID:");

        firstNameTextField = new JTextField(20);
        lastNameTextField = new JTextField(20);
        idTextField = new JTextField(10);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerButtonActionPerformed(e);
            }
        });

        updateButton = new JButton("Update Details");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateButtonActionPerformed(e);
            }
        });

        payrollButton = new JButton("Calculate Payroll");
        payrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                payrollButtonActionPerformed(e);
            }
        });

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(firstNameLabel)
                        .addComponent(lastNameLabel)
                        .addComponent(idLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(firstNameTextField)
                        .addComponent(lastNameTextField)
                        .addComponent(idTextField))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(firstNameLabel)
                        .addComponent(firstNameTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lastNameLabel)
                        .addComponent(lastNameTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel)
                        .addComponent(idTextField))
        );

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(payrollButton);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void registerButtonActionPerformed(ActionEvent evt) {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String id = idTextField.getText();

        try {
            client.registerEmployee(firstName, lastName, id);
            JOptionPane.showMessageDialog(this, "Employee registered successfully!");
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(this, "Error registering employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateButtonActionPerformed(ActionEvent evt) {
        String idToUpdate = JOptionPane.showInputDialog(this, "Enter the ID of the employee to update:");
        if (idToUpdate != null && !idToUpdate.isEmpty()) {
            String newFirstName = JOptionPane.showInputDialog(this, "Enter the new first name:");
            String newLastName = JOptionPane.showInputDialog(this, "Enter the new last name:");
            if (newFirstName != null && newLastName != null) {
                try {
                    client.updateEmployeeDetails(idToUpdate, newFirstName, newLastName);
                    JOptionPane.showMessageDialog(this, "Employee details updated successfully!");
                } catch (RemoteException e) {
                    JOptionPane.showMessageDialog(this, "Error updating employee details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void payrollButtonActionPerformed(ActionEvent evt) {
        String id = JOptionPane.showInputDialog(this, "Enter the ID of the employee:");
        if (id != null && !id.isEmpty()) {
            try {
                PayrollInfo payrollInfo = client.getPayrollInfo(id);
                String message = "Payroll Information for Employee " + id + ":\n"
                        + "Gross Pay: " + payrollInfo.getGrossPay() + "\n"
                        + "Deductions: " + payrollInfo.getDeductions() + "\n"
                        + "Net Pay: " + payrollInfo.getNetPay();
                JOptionPane.showMessageDialog(this, message, "Payroll Information", JOptionPane.INFORMATION_MESSAGE);
            } catch (RemoteException e) {
                JOptionPane.showMessageDialog(this, "Error retrieving payroll information: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PayrollClient client = new PayrollClient();
                    new EmployeeRegistrationForm(client).setVisible(true);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}