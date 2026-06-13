package simpos.dao;

import simpos.model.Pengumuman;
import simpos.util.Database;
import java.sql.*;
import java.util.*;

public class PengumumanDAO {

    public List<Pengumuman> getAll() {
        List<Pengumuman> list = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM pengumuman ORDER BY tanggal DESC")) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Pengumuman> getActive(int limit) {
        List<Pengumuman> list = new ArrayList<>();
        String sql = "SELECT * FROM pengumuman WHERE status='Aktif' ORDER BY tanggal DESC LIMIT ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean save(Pengumuman p) {
        String sql = "INSERT INTO pengumuman (judul, isi, tanggal, status) VALUES (?,?,?,?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getJudul());
            ps.setString(2, p.getIsi());
            ps.setString(3, p.getTanggal());
            ps.setString(4, p.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(Pengumuman p) {
        String sql = "UPDATE pengumuman SET judul=?, isi=?, tanggal=?, status=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getJudul());
            ps.setString(2, p.getIsi());
            ps.setString(3, p.getTanggal());
            ps.setString(4, p.getStatus());
            ps.setInt(5, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM pengumuman WHERE id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private Pengumuman map(ResultSet rs) throws SQLException {
        Pengumuman p = new Pengumuman();
        p.setId(rs.getInt("id"));
        p.setJudul(rs.getString("judul"));
        p.setIsi(rs.getString("isi"));
        p.setTanggal(rs.getString("tanggal"));
        p.setStatus(rs.getString("status"));
        return p;
    }
}
