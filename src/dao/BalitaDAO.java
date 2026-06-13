package simpos.dao;

import simpos.model.Balita;
import simpos.util.Database;
import java.sql.*;
import java.util.*;

public class BalitaDAO {

    public List<Balita> getAll() {
        List<Balita> list = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM balita ORDER BY nama")) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Balita> search(String keyword) {
        List<Balita> list = new ArrayList<>();
        String sql = "SELECT * FROM balita WHERE nama LIKE ? OR nik LIKE ? OR nama_ibu LIKE ? ORDER BY nama";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw); ps.setString(2, kw); ps.setString(3, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Balita> getByOrtuNama(String namaOrtu) {
        List<Balita> list = new ArrayList<>();
        String sql = "SELECT * FROM balita WHERE nama_ibu = ? OR nama_ayah = ? ORDER BY nama";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, namaOrtu);
            ps.setString(2, namaOrtu);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean save(Balita b) {
        String sql = "INSERT INTO balita (nama, nik, tanggal_lahir, jenis_kelamin, nama_ibu, nama_ayah, alamat, no_telp) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getNama()); ps.setString(2, b.getNik());
            ps.setString(3, b.getTanggalLahir()); ps.setString(4, b.getJenisKelamin());
            ps.setString(5, b.getNamaIbu()); ps.setString(6, b.getNamaAyah());
            ps.setString(7, b.getAlamat()); ps.setString(8, b.getNoTelp());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(Balita b) {
        String sql = "UPDATE balita SET nama=?, nik=?, tanggal_lahir=?, jenis_kelamin=?, nama_ibu=?, nama_ayah=?, alamat=?, no_telp=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getNama()); ps.setString(2, b.getNik());
            ps.setString(3, b.getTanggalLahir()); ps.setString(4, b.getJenisKelamin());
            ps.setString(5, b.getNamaIbu()); ps.setString(6, b.getNamaAyah());
            ps.setString(7, b.getAlamat()); ps.setString(8, b.getNoTelp());
            ps.setInt(9, b.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM balita WHERE id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public int count() {
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM balita")) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) { e.printStackTrace(); return 0; }
    }

    private Balita map(ResultSet rs) throws SQLException {
        return new Balita(rs.getInt("id"), rs.getString("nama"), rs.getString("nik"),
            rs.getString("tanggal_lahir"), rs.getString("jenis_kelamin"),
            rs.getString("nama_ibu"), rs.getString("nama_ayah"),
            rs.getString("alamat"), rs.getString("no_telp"));
    }
}
