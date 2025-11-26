/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modul7;

/**
 *
 * @author Yoga
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
       
public class TUGAS extends JFrame{
    private JTextField txtNama;
    private JTextField txtNilai;
    private JComboBox cmbMatkul;
    private JTable tableData;
    private final JTabbedPane tabbedPane;
    private DefaultTableModel tableModel;
        
    // Method untuk membuat desain Tab Input Tambah Tombol Reset
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10)); // Baris menjadi 5
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Komponen Nama
        panel.add(new JLabel("Nama Siswa:"));
        txtNama = new JTextField();
        panel.add(txtNama);

        // Komponen Mata Pelajaran (ComboBox)
        panel.add(new JLabel("Mata Pelajaran:"));
        String[] matkul = {"Matematika Dasar", "Bahasa Indonesia",
                           "Algoritma dan Pemrograman I", "Praktikum Pemrograman II"};
        cmbMatkul = new JComboBox<>(matkul);
        panel.add(cmbMatkul);

        // Komponen Nilai
        panel.add(new JLabel("Nilai (0-100):"));
        txtNilai = new JTextField();
        panel.add(txtNilai);

        // Baris untuk Tombol Simpan dan Reset (di baris ke-4)
        // Tombol Simpan
        JButton btnSimpan = new JButton("Simpan Data");
        panel.add(new JLabel("")); // Placeholder kosong agar tombol di kanan
        panel.add(btnSimpan);
        
        // Baris untuk Tombol Reset (di baris ke-5)
        JButton btnReset = new JButton("Reset Input");
        panel.add(new JLabel("")); 
        panel.add(btnReset);

        // Event Handling Tombol Simpan
        btnSimpan.addActionListener((ActionEvent e) -> {
            prosesSimpan();
        });
        
        // Event Handling Tombol Reset
        btnReset.addActionListener((ActionEvent e) -> {
            txtNama.setText("");
            txtNilai.setText("");
            cmbMatkul.setSelectedIndex(0);
            txtNama.requestFocus();
        });

        return panel;
    }
    
    // Method untuk membuat desain Tab Tabel dan Tambah Tombol Hapus
    private JPanel createTablePanel(){
        JPanel panel = new JPanel (new BorderLayout());
        
        //Setup Model Tabel
        String[] kolom = {"Nama Siswa","Mata Pelajaran","Nilai", "Grade"}; // Koreksi typo "Pleajaran"
        tableModel = new DefaultTableModel(kolom, 0);
        tableData = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableData);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Tambah Tombol Hapus di panel bawah
        JButton btnHapus = new JButton("Hapus Data Terpilih");
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnHapus);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Event Handling Tombol Hapus
        btnHapus.addActionListener((ActionEvent e) -> {
            hapusData();
        });
        
        return panel;
    }
    
    // Method baru untuk proses hapus data
    private void hapusData() {
        int selectedRow = tableData.getSelectedRow();
        
        if (selectedRow >= 0) {
            // Konfirmasi sebelum menghapus
            int dialogResult = JOptionPane.showConfirmDialog(this, 
                    "Yakin ingin menghapus data ini?", "Konfirmasi Hapus", 
                    JOptionPane.YES_NO_OPTION);
            
            if (dialogResult == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                    "Silakan pilih baris data yang ingin dihapus terlebih dahulu.", 
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // Logika Validasi dan Penyimpanan Data
    private void prosesSimpan() {
        // 1. Ambil data dari input
        String nama = txtNama.getText().trim(); // Gunakan trim di awal untuk validasi
        String matkul = (String) cmbMatkul.getSelectedItem();
        String strNilai = txtNilai.getText();

        // 2. VALIDASI INPUT

        // Validasi Nama (Minimal 3 karakter)
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong!", 
                                          "Error Validasi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (nama.length() < 3) {
            JOptionPane.showMessageDialog(this, "Nama minimal terdiri dari 3 karakter!", 
                                          "Error Validasi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validasi Nama Gak boleh Ada Angka
        if (nama.matches(".*\\d.*")) {
        JOptionPane.showMessageDialog(this, "Nama tidak boleh mengandung angka!", 
                                      "Error Validasi", JOptionPane.ERROR_MESSAGE);
        return;
        }

        // Validasi Cek apakah nilai berupa angka dan dalam range valid
        int nilai;
        try {
            nilai = Integer.parseInt(strNilai);
            if (nilai < 0 || nilai > 100) {
                JOptionPane.showMessageDialog(this, "Nilai harus antara 0 - 100!",
                                              "Error Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nilai harus berupa angka!",
                                          "Error Validasi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Logika Bisnis (Menentukan Grade) menggunakan switch expression
        String grade;
        int scaledNilai = nilai / 10;
        
        grade = switch (scaledNilai) {
            case 10, 9, 8 -> "A";   // Nilai 80-100
            case 7 -> "AB";         // Nilai 70-79
            case 6 -> "B";          // Nilai 60-69
            case 5 -> "BC";         // Nilai 50-59
            case 4 -> "C";          // Nilai 40-49
            case 3 -> "D";          // Nilai 30-39
            default -> "E";         // Nilai 0-29
        };

        // 4. Masukkan ke Tabel (Update Model)
        Object[] dataBaris = {nama, matkul, nilai, grade};
        tableModel.addRow(dataBaris);

        // 5. Reset Form dan Pindah Tab
        txtNama.setText("");
        txtNilai.setText("");
        cmbMatkul.setSelectedIndex(0);

        JOptionPane.showMessageDialog(this, "Data Berhasil Disimpan!");
        tabbedPane.setSelectedIndex(1); // Otomatis pindah ke tab tabel
    }
    
    public TUGAS() {
    // 1. Konfigurasi Frame Utama
    setTitle("Aplikasi Manajemen Nilai Siswa");
    setSize(600, 450); // Ukuran sedikit diperbesar agar tombol hapus terlihat
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // Posisi di tengah layar

    // 2. Inisialisasi Tabbed Pane
    tabbedPane = new JTabbedPane();

    // 3. Membuat Panel untuk Tab 1 (Form Input)
    JPanel panelInput = createInputPanel();
    tabbedPane.addTab("Input Data", panelInput);

    // 4. Membuat Panel untuk Tab 2 (Tabel Data)
    JPanel panelTabel = createTablePanel();
    tabbedPane.addTab("Daftar Nilai", panelTabel);

    // Menambahkan TabbedPane ke Frame
    add(tabbedPane);
}

// Terakhir, buat method main untuk menjalankan kelas ini.

public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        new TUGAS().setVisible(true); 
    });
}

}