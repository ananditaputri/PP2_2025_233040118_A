package model;

import util.KoneksiDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MahasiswaDAO {
    
    // CREATE - Menambah data mahasiswa
    public boolean tambah(Mahasiswa m) {
        String sql = "INSERT INTO mahasiswa (nama, nim, jurusan) VALUES (?, ?, ?)";
        
        try (Connection conn = KoneksiDB.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            if (conn == null) return false;
            
            pst.setString(1, m.getNama());
            pst.setString(2, m.getNim());
            pst.setString(3, m.getJurusan());
            
            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error tambah mahasiswa: " + e.getMessage());
            return false;
        }
    }

    // READ - Mendapatkan semua data mahasiswa
    public List<Mahasiswa> getAll() {
        List<Mahasiswa> list = new ArrayList<>();
        String sql = "SELECT * FROM mahasiswa ORDER BY id";
        
        try (Connection conn = KoneksiDB.configDB();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            
            if (conn == null) return list;
            
            while (rs.next()) {
                Mahasiswa m = new Mahasiswa(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("nim"),
                    rs.getString("jurusan")
                );
                list.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error get all mahasiswa: " + e.getMessage());
        }
        return list;
    }

    // UPDATE - Mengubah data mahasiswa berdasarkan NIM
    public boolean update(Mahasiswa m) {
        String sql = "UPDATE mahasiswa SET nama = ?, jurusan = ? WHERE nim = ?";
        
        try (Connection conn = KoneksiDB.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            if (conn == null) return false;
            
            pst.setString(1, m.getNama());
            pst.setString(2, m.getJurusan());
            pst.setString(3, m.getNim());
            
            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error update mahasiswa: " + e.getMessage());
            return false;
        }
    }

    // DELETE - Menghapus data mahasiswa berdasarkan NIM
    public boolean delete(String nim) {
        String sql = "DELETE FROM mahasiswa WHERE nim = ?";
        
        try (Connection conn = KoneksiDB.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            if (conn == null) return false;
            
            pst.setString(1, nim);
            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error delete mahasiswa: " + e.getMessage());
            return false;
        }
    }

    // SEARCH - Mencari data berdasarkan nama
    public List<Mahasiswa> cari(String keyword) {
        List<Mahasiswa> list = new ArrayList<>();
        String sql = "SELECT * FROM mahasiswa WHERE nama LIKE ? ORDER BY nama";
        
        try (Connection conn = KoneksiDB.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            if (conn == null) return list;
            
            pst.setString(1, "%" + keyword + "%");
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Mahasiswa m = new Mahasiswa(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("nim"),
                    rs.getString("jurusan")
                );
                list.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error cari mahasiswa: " + e.getMessage());
        }
        return list;
    }

    // CHECK - Mengecek apakah NIM sudah ada
    public boolean isNimExist(String nim) {
        String sql = "SELECT COUNT(*) FROM mahasiswa WHERE nim = ?";
        
        try (Connection conn = KoneksiDB.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            if (conn == null) return false;
            
            pst.setString(1, nim);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error cek NIM: " + e.getMessage());
        }
        return false;
    }
    
    // GET BY NIM - Mendapatkan mahasiswa berdasarkan NIM
    public Mahasiswa getByNim(String nim) {
        String sql = "SELECT * FROM mahasiswa WHERE nim = ?";
        
        try (Connection conn = KoneksiDB.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            if (conn == null) return null;
            
            pst.setString(1, nim);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return new Mahasiswa(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("nim"),
                    rs.getString("jurusan")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error get by NIM: " + e.getMessage());
        }
        return null;
    }
}