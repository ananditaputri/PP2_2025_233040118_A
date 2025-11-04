/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modul6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Latihan2 extends JFrame {
    
    public Latihan2() {
        setTitle("Konverter Suhu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new FlowLayout());
        
        JLabel lblCelcius = new JLabel("Celcius:");
        JTextField txtCelcius = new JTextField(10);
        JButton btnKonversi = new JButton("Konversi");
        JLabel lblFahrenheit = new JLabel("Fahrenheit:");
        JLabel lblHasil = new JLabel("-");
        
        add(lblCelcius);
        add(txtCelcius);
        add(btnKonversi);
        add(lblFahrenheit);
        add(lblHasil);
        
        // Event Handling untuk tombol Konversi
        btnKonversi.addActionListener((ActionEvent e) -> {
            try {
                double celcius = Double.parseDouble(txtCelcius.getText());
                double fahrenheit = (celcius * 9 / 5) + 32;
                lblHasil.setText(String.format("%.2f", fahrenheit));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Input harus berupa angka!");
            }
        });
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
    }
}
