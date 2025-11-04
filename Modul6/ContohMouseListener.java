/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modul6;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author hp
 */
public class ContohMouseListener {
    public static void main(String[] args) {
        // 1. Buat Frame
        JFrame frame = new JFrame("Contoh MouseListener");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        // 2. Buat Komponen (Event Source)
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setPreferredSize(new Dimension(200, 200));

        // 3. Buat Event Listener (menggunakan MouseAdapter)
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Saat mouse masuk area panel, ubah warna jadi biru muda
                panel.setBackground(Color.CYAN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Saat mouse keluar area panel, kembalikan warna semula
                panel.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Saat diklik, tampilkan koordinat klik di console
                System.out.println("Mouse diklik di: x=" + e.getX() + ", y=" + e.getY());
            }
        };

        // 4. Daftarkan listener ke panel
        panel.addMouseListener(adapter);

        // 5. Tambahkan panel ke frame
        frame.add(panel);
        frame.setVisible(true);
    }
}
