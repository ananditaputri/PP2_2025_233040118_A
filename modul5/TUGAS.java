/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.pp2_A_233040118.modul5;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author ASUS
 */
public class TUGAS {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Contoh BorderLayout dengan Aksi Tombol");
                frame.setSize(400, 300);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.setLayout(new BorderLayout());

                JLabel label = new JLabel("Silakan klik tombol yang ada.");
                JButton buttonSouth = new JButton("SOUTH");
                JButton buttonNorth = new JButton("NORTH");
                JButton buttonWest = new JButton("WEST");
                JButton buttonEast = new JButton("EAST");
                JButton buttonCenter = new JButton("CENTER");

                // Aksi tombol SOUTH (sudah ada di latihan4)
                buttonSouth.addActionListener(e -> {
                    label.setText("Tombol SOUTH diklik!");
                });

                // Aksi tombol NORTH
                buttonNorth.addActionListener(e -> {
                    label.setText("Tombol NORTH diklik!");
                });

                // Aksi tombol WEST
                buttonWest.addActionListener(e -> {
                    label.setText("Tombol WEST diklik!");
                });

                // Aksi tombol EAST
                buttonEast.addActionListener(e -> {
                    label.setText("Tombol EAST diklik!");
                });

                // Aksi tombol CENTER
                buttonCenter.addActionListener(e -> {
                    label.setText("Tombol CENTER diklik!");
                });

                // Tambahkan semua komponen ke frame sesuai posisinya
                frame.add(label, BorderLayout.NORTH);
                frame.add(buttonSouth, BorderLayout.SOUTH);
                frame.add(buttonWest, BorderLayout.WEST);
                frame.add(buttonEast, BorderLayout.EAST);
                frame.add(buttonCenter, BorderLayout.CENTER);

                frame.setVisible(true);
            }
        });
    }
}