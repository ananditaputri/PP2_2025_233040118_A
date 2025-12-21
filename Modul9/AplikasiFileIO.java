package Modul9;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class AplikasiFileIO extends JFrame {
    // Komponen UI
    private JTextArea textArea;
    private JButton btnOpenText, btnSaveText, btnAppendText;
    private JButton btnSaveBinary, btnLoadBinary;
    private JButton btnSaveObject, btnLoadObject;
    private JFileChooser fileChooser;

    public AplikasiFileIO() {
        super("Tutorial File IO & Exception Handling");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialisasi Komponen
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        fileChooser = new JFileChooser();

        // Panel Tombol
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        btnOpenText = new JButton("Buka Text");
        btnSaveText = new JButton("Simpan Text");
        btnAppendText = new JButton("Tambah Text"); // LATIHAN 4
        btnSaveBinary = new JButton("Simpan Config (Binary)");
        btnLoadBinary = new JButton("Muat Config (Binary)");
        btnSaveObject = new JButton("Simpan Objek"); // LATIHAN 3
        btnLoadObject = new JButton("Muat Objek"); // LATIHAN 3

        buttonPanel.add(btnOpenText);
        buttonPanel.add(btnSaveText);
        buttonPanel.add(btnAppendText);
        buttonPanel.add(btnSaveBinary);
        buttonPanel.add(btnLoadBinary);
        buttonPanel.add(btnSaveObject);
        buttonPanel.add(btnLoadObject);

        // Layout
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- Event Handling ---
        // 1. MEMBACA FILE TEKS (Text Stream)
        btnOpenText.addActionListener(e -> bukarilereks());

        // 2. MENULIS FILE TEKS (Text Stream) - Overwrite
        btnSaveText.addActionListener(e -> simpanFileTekx());

        // 3. MENULIS FILE TEKS (Text Stream) - Append (LATIHAN 4)
        btnAppendText.addActionListener(e -> tambahFileTeks());

        // 4. MENULIS FILE BINARY (Byte Stream)
        btnSaveBinary.addActionListener(e -> simpanConfigBinary());

        // 5. MEMBACA FILE BINARY (Byte Stream)
        btnLoadBinary.addActionListener(e -> muatConfigBinary());

        // 6. MENYIMPAN OBJEK (LATIHAN 3)
        btnSaveObject.addActionListener(e -> simpanObjek());

        // 7. MEMUAT OBJEK (LATIHAN 3)
        btnLoadObject.addActionListener(e -> muatObjek());

        // LATIHAN 2: Membaca last_notes.txt saat aplikasi dibuka
        bacaLastNotes();
    }

    // =============== LATIHAN 2: Membaca last_notes.txt saat startup ===============
    private void bacaLastNotes() {
        try (BufferedReader reader = new BufferedReader(new FileReader("last_notes.txt"))) {
            textArea.setText(""); // Kosongkan area
            String line;
            // Baca baris demi baris
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
        } catch (FileNotFoundException ex) {
            // File tidak ditemukan, aplikasi tidak error (hanya diam saja)
        } catch (IOException ex) {
            // Error membaca file, aplikasi tidak error (hanya diam saja)
        }
    }

    // =============== LATIHAN 1: Membaca File Teks ===============
    private void bukarilereks() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            BufferedReader reader = null; // Deklarasi di luar try agar bisa diakses di finally

            try {
                // Membuka stream
                reader = new BufferedReader(new FileReader(file));
                textArea.setText(""); // Kosongkan area

                String line;
                // Baca baris demi baris
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }

                JOptionPane.showMessageDialog(this, "File berhasil dimuat!");

            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "File tidak ditemukan: " + ex.getMessage());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Gagal membaca file: " + ex.getMessage());
            } finally {
                // Blok Finally: Belalu dijalankan untuk menutup resource
                try {
                    if (reader != null) {
                        reader.close(); // PENTING: Menutup stream
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // =============== LATIHAN 1: Menulis File Teks ===============
    private void simpanFileTekx() {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // Try-with-resources otomatis menutup stream tanpa blok finally manual
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(textArea.getText());
                JOptionPane.showMessageDialog(this, "File berhasil disimpan!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan file: " + ex.getMessage());
            }
        }
    }

    // =============== LATIHAN 4: Menambah Teks ke File ===============
    private void tambahFileTeks() {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // Parameter kedua true untuk append mode
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write("\n" + textArea.getText()); // Tambahkan baris baru sebelum teks
                JOptionPane.showMessageDialog(this, "Teks berhasil ditambahkan ke file!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Gagal menambah teks ke file: " + ex.getMessage());
            }
        }
    }

    // =============== LATIHAN 1: Menyimpan Binary ===============
    private void simpanConfigBinary() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("config.bin"))) {
            // Kita simpan ukuran font saat ini (Integer)
            int fontSize = textArea.getFont().getSize();
            dos.writeInt(fontSize);
            JOptionPane.showMessageDialog(this, "Ukuran font (" + fontSize + ") disimpan ke config.bin");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan binary: " + ex.getMessage());
        }
    }

    // =============== LATIHAN 1: Membaca Binary ===============
    private void muatConfigBinary() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream("config.bin"))) {
            // Membaca data Integer mentali
            int fontSize = dis.readInt();
            // Terapkan ke aplikasi
            textArea.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
            JOptionPane.showMessageDialog(this, "Font diubah menjadi ukuran: " + fontSize);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "File config.bin belum dibuat!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Gagal membaca binary: " + ex.getMessage());
        }
    }

    // =============== LATIHAN 3: Menyimpan Objek ===============
    private void simpanObjek() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user_config.obj"))) {
            // Membuat objek UserConfig
            UserConfig config = new UserConfig();
            config.setUsername("Pengguna");
            config.setFontsize(textArea.getFont().getSize());
            
            // Menyimpan objek ke file
            oos.writeObject(config);
            JOptionPane.showMessageDialog(this, 
                "Objek berhasil disimpan!\nUsername: " + config.getUsername() + 
                "\nFont Size: " + config.getFontsize());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan objek: " + ex.getMessage());
        }
    }

    // =============== LATIHAN 3: Membaca Objek ===============
    private void muatObjek() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user_config.obj"))) {
            // Membaca objek dan melakukan casting ke UserConfig
            UserConfig config = (UserConfig) ois.readObject();
            
            // Menerapkan konfigurasi ke aplikasi
            textArea.setFont(new Font("Monospaced", Font.PLAIN, config.getFontsize()));
            
            JOptionPane.showMessageDialog(this, 
                "Objek berhasil dimuat!\nUsername: " + config.getUsername() + 
                "\nFont Size: " + config.getFontsize());
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "File user_config.obj belum dibuat!");
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Gagal membaca objek: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AplikasiFileIO().setVisible(true);
        });
    }
}