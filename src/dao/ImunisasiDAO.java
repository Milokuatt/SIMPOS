package simpos.dao;

import simpos.model.Imunisasi;
import simpos.util.Database;
import java.sql.*;
import java.util.*;

public class ImunisasiDAO {

    public List<Imunisasi> getAll() {
        List<Imunisasi> list = new ArrayList<>();
        String sql = "SELECT i.*, b.nama as nama_balita FROM imunisasi i JOIN balita b ON i.balita_id=b.id ORDER BY i.tanggal DESC";
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Imunisasi> getByBalita(int balitaId) {
        List<Imunisasi> list = new ArrayList<>();
        String sql = "SELECT i.*, b.nama as nama_balita FROM imunisasi i JOIN balita b ON i.balita_id=b.id WHERE i.balita_id=? ORDER BY i.tanggal DESC";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, balitaId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Imunisasi> getByOrtuNama(String namaOrtu) {
        List<Imunisasi> list = new ArrayList<>();
        String sql = "SELECT i.*, b.nama as nama_balita FROM imunisasi i JOIN balita b ON i.balita_id=b.id " +
                "WHERE b.nama_ibu = ? OR b.nama_ayah = ? ORDER BY i.tanggal DESC";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, namaOrtu);
            ps.setString(2, namaOrtu);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean save(Imunisasi im) {
        String sql = "INSERT INTO imunisasi (balita_id, jenis_imunisasi, tanggal, usia_bulan, petugas, keterangan) VALUES (?,?,?,?,?,?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, im.getBalitaId());
            ps.setString(2, im.getJenisImunisasi());
            ps.setString(3, im.getTanggal());
            ps.setInt(4, im.getUsiaBulan());
            ps.setString(5, im.getPetugas());
            ps.setString(6, im.getKeterangan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(Imunisasi im) {
        String sql = "UPDATE imunisasi SET balita_id=?, jenis_imunisasi=?, tanggal=?, usia_bulan=?, petugas=?, keterangan=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, im.getBalitaId());
            ps.setString(2, im.getJenisImunisasi());
            ps.setString(3, im.getTanggal());
            ps.setInt(4, im.getUsiaBulan());
            ps.setString(5, im.getPetugas());
            ps.setString(6, im.getKeterangan());
            ps.setInt(7, im.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM imunisasi WHERE id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public int count() {
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM imunisasi")) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) { e.printStackTrace(); return 0; }
    }

    private Imunisasi map(ResultSet rs) throws SQLException {
        Imunisasi im = new Imunisasi();
        im.setId(rs.getInt("id"));
        im.setBalitaId(rs.getInt("balita_id"));
        im.setNamaBalita(rs.getString("nama_balita"));
        im.setJenisImunisasi(rs.getString("jenis_imunisasi"));
        im.setTanggal(rs.getString("tanggal"));
        im.setUsiaBulan(rs.getInt("usia_bulan"));
        im.setPetugas(rs.getString("petugas"));
        im.setKeterangan(rs.getString("keterangan"));
        return im;
    }
}
