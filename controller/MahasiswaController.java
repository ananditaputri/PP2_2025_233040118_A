package controller;

import model.Mahasiswa;
import model.MahasiswaDAO;
import view.MahasiswaView;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class MahasiswaController {
    private MahasiswaView view;
    private MahasiswaDAO dao;

    public MahasiswaController(MahasiswaView view) {
        this.view = view;
        this.dao = new MahasiswaDAO();
        
        initEventListeners();
        loadData();
    }

    private void initEventListeners() {
        // Event klik tabel untuk mengisi form
        view.getTableMahasiswa().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTableMahasiswa().getSelectedRow();
                if (row >= 0 && row < view.getModel().getRowCount()) {
                    // Ambil data dari baris yang diklik
                    String nama = view.getModel().getValueAt(row, 1).toString();
                    String nim = view.getModel().getValueAt(row, 2).toString();
                    String jurusan = view.getModel().getValueAt(row, 3).toString();
                    
                    // Isi ke form
                    view.getTxtNama().setText(nama);
                    view.getTxtNIM().setText(nim);
                    view.getTxtJurusan().setText(jurusan);
                }
            }
        });

        // Aksi Tombol
        view.getBtnSimpan().addActionListener(e -> tambahData());
        view.getBtnEdit().addActionListener(e -> ubahData());
        view.getBtnHapus().addActionListener(e -> hapusData());
        view.getBtnClear().addActionListener(e -> clearForm());
        view.getBtnCari().addActionListener(e -> cariData());
    }

    // Method loadData (READ) - Menampilkan semua data
    private void loadData() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                List<Mahasiswa> list = dao.getAll();
                view.getModel().setRowCount(0); // Reset tabel di thread background
                
                int no = 1;
                for (Mahasiswa m : list) {
                    final int currentNo = no++;
                    final Mahasiswa currentM = m;
                    
                    // Update UI di thread EDT
                    SwingUtilities.invokeLater(() -> {
                        view.getModel().addRow(new Object[]{
                            currentNo,
                            currentM.getNama(),
                            currentM.getNim(),
                            currentM.getJurusan()
                        });
                    });
                }
                return null;
            }
            
            @Override
            protected void done() {
                // Optional: bisa tambahkan loading indicator
            }
        };
        worker.execute();
    }

    // Method tambahData (CREATE) - Menambah data baru
    private void tambahData() {
        // LATIHAN 2: Validasi input tidak boleh kosong
        if (view.getTxtNama().getText().trim().isEmpty()) {
            showError("Nama tidak boleh kosong!");
            view.getTxtNama().requestFocus();
            return;
        }
        
        if (view.getTxtNIM().getText().trim().isEmpty()) {
            showError("NIM tidak boleh kosong!");
            view.getTxtNIM().requestFocus();
            return;
        }
        
        String nim = view.getTxtNIM().getText().trim();
        
        // Validasi format NIM (hanya angka)
        if (!nim.matches("\\d+")) {
            showError("NIM harus berupa angka!");
            view.getTxtNIM().requestFocus();
            return;
        }

        // LATIHAN 4: Validasi NIM sudah ada
        if (dao.isNimExist(nim)) {
            showError("NIM " + nim + " sudah terdaftar!");
            view.getTxtNIM().requestFocus();
            return;
        }

        // Ambil data dari form
        Mahasiswa m = new Mahasiswa(
            view.getTxtNama().getText().trim(),
            nim,
            view.getTxtJurusan().getText().trim()
        );
        
        // Simpan ke database
        if (dao.tambah(m)) {
            JOptionPane.showMessageDialog(view, 
                "Data Berhasil Disimpan", 
                "Sukses", 
                JOptionPane.INFORMATION_MESSAGE);
            loadData();
            clearForm();
        } else {
            showError("Gagal menyimpan data. Periksa koneksi database.");
        }
    }

    // Method ubahData (UPDATE) - Mengubah data
    private void ubahData() {
        // Validasi harus ada data yang dipilih
        if (view.getTxtNIM().getText().trim().isEmpty()) {
            showError("Pilih data yang akan diubah dari tabel!");
            return;
        }
        
        String nim = view.getTxtNIM().getText().trim();
        
        // Validasi data ada di database
        if (!dao.isNimExist(nim)) {
            showError("Data dengan NIM " + nim + " tidak ditemukan!");
            return;
        }

        // Ambil data dari form
        Mahasiswa m = new Mahasiswa(
            view.getTxtNama().getText().trim(),
            nim,
            view.getTxtJurusan().getText().trim()
        );
        
        // Update ke database
        if (dao.update(m)) {
            JOptionPane.showMessageDialog(view, 
                "Data Berhasil Diubah", 
                "Sukses", 
                JOptionPane.INFORMATION_MESSAGE);
            loadData();
            clearForm();
        } else {
            showError("Gagal mengubah data. Periksa koneksi database.");
        }
    }

    // Method hapusData (DELETE) - Menghapus data
    private void hapusData() {
        // Validasi harus ada data yang dipilih
        if (view.getTxtNIM().getText().trim().isEmpty()) {
            showError("Pilih data yang akan dihapus dari tabel!");
            return;
        }

        String nim = view.getTxtNIM().getText().trim();
        
        // Konfirmasi hapus
        int confirm = JOptionPane.showConfirmDialog(view,
            "Apakah Anda yakin ingin menghapus data dengan NIM: " + nim + "?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(nim)) {
                JOptionPane.showMessageDialog(view, 
                    "Data Berhasil Dihapus", 
                    "Sukses", 
                    JOptionPane.INFORMATION_MESSAGE);
                loadData();
                clearForm();
            } else {
                showError("Gagal menghapus data. Periksa koneksi database.");
            }
        }
    }

    // Method cariData (SEARCH) - LATIHAN 3
    private void cariData() {
        String keyword = view.getTxtCari().getText().trim();
        
        if (keyword.isEmpty()) {
            // Jika pencarian kosong, tampilkan semua data
            loadData();
            return;
        }
        
        // Cari data berdasarkan keyword
        List<Mahasiswa> list = dao.cari(keyword);
        view.getModel().setRowCount(0); // Reset tabel
        
        int no = 1;
        for (Mahasiswa m : list) {
            view.getModel().addRow(new Object[]{
                no++,
                m.getNama(),
                m.getNim(),
                m.getJurusan()
            });
        }
        
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Data dengan kata kunci '" + keyword + "' tidak ditemukan", 
                "Hasil Pencarian", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, 
                "Ditemukan " + list.size() + " data", 
                "Hasil Pencarian", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Method clearForm - Mengosongkan form
    private void clearForm() {
        view.getTxtNama().setText("");
        view.getTxtNIM().setText("");
        view.getTxtJurusan().setText("");
        view.getTxtCari().setText("");
        view.getTableMahasiswa().clearSelection();
    }

    // Helper method untuk menampilkan error
    private void showError(String message) {
        JOptionPane.showMessageDialog(view, 
            message, 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }

    // Main method - Entry point aplikasi
    public static void main(String[] args) {
        // Mengalankan Aplikasi di Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Set Look and Feel ke sistem
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            MahasiswaView view = new MahasiswaView();
            new MahasiswaController(view);
            view.setVisible(true);
        });
    }
}