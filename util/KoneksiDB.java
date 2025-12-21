package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class KoneksiDB {
    
    public static Connection configDB() {
        try {
            // URL database dengan parameter tambahan untuk koneksi lebih baik
            String url = "jdbc:mysql://localhost:3306/kampus_db?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String pass = "";
            
            // Untuk JDBC driver versi baru, tidak perlu register manual
            // Driver akan otomatis terdeteksi
            return DriverManager.getConnection(url, user, pass);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Koneksi Gagal: " + e.getMessage() + 
                "\nPastikan:\n1. MySQL Server berjalan\n2. Database 'kampus_db' sudah dibuat\n3. Port 3306 terbuka",
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}