/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modul6;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author hp
 */
public class ContohActionListener {
    public static void main(String[] args) {
        // 1. Buat Frame
        JFrame frame = new JFrame("Contoh ActionListener");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        // 2. Buat Komponen (Event Source dan Komponen Lain)
        JLabel label = new JLabel("Halo, klik tombol di bawah!");
        JButton button = new JButton("Klik Saya!");

        // 3. Buat Event Listener (menggunakan anonymous inner class)
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logika saat tombol diklik
                label.setText("Tombol telah diklik!");
            }
        };

        // 4. Daftarkan Listener ke Sumber Event (Button)
        button.addActionListener(listener);

        // 5. Tambahkan Komponen ke Frame
        frame.add(label);
        frame.add(button);

        // 6. Tampilkan Frame
        frame.setVisible(true);
    }
}

