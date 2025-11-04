/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modul6;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author hp
 */
public class ContohGridLayout {
    public static void main(String[] args) {
        // 1. Buat Frame
        JFrame frame = new JFrame("Contoh GridLayout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // 2. Atur layout frame menjadi GridLayout 3 baris, 2 kolom
        // Tambahkan jarak antar sel (5px horizontal dan vertikal)
        frame.setLayout(new GridLayout(3, 2, 5, 5));

        // 3. Tambahkan 6 komponen ke frame
        frame.add(new JLabel("Label 1:"));
        frame.add(new JTextField());
        frame.add(new JLabel("Label 2:"));
        frame.add(new JPasswordField());
        frame.add(new JButton("Login"));
        frame.add(new JButton("Batal"));

        // 4. Tampilkan frame
        frame.setVisible(true);
    }
}

