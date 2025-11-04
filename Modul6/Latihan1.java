/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modul6;

import javax.swing.*;
import java.awt.*;

public class Latihan1 extends JFrame {
    
    public Latihan1() {
        setTitle("Kalkulator Sederhana");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLayout(new BorderLayout(10, 10));
        
        // Bagian atas: layar kalkulator
        JTextField layar = new JTextField();
        layar.setEditable(false);
        layar.setHorizontalAlignment(JTextField.RIGHT);
        add(layar, BorderLayout.NORTH);
        
        // Bagian tengah: tombol angka dan operator
        JPanel panelTombol = new JPanel(new GridLayout(4, 4, 5, 5));
        String[] tombol = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };
        
        for (String teks : tombol) {
            panelTombol.add(new JButton(teks));
        }
        
        add(panelTombol, BorderLayout.CENTER);
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        Latihan1 latihan1 = new Latihan1();
    }
}

