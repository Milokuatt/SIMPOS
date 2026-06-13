package simpos.dao;

import simpos.model.Jadwal;
import simpos.util.Database;
import java.sql.*;
import java.util.*;

public class JadwalDAO {

    public List<Jadwal> getAll() {
        List<Jadwal> list = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM jadwal ORDER BY tanggal DESC")) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean save(Jadwal j) {
        String sql = "INSERT INTO jadwal (nama_kegiatan, tanggal, waktu, lokasi, penanggung_jawab, deskripsi, status) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, j.getNamaKegiatan());
            ps.setString(2, j.getTanggal());
            ps.setString(3, j.getWaktu());
            ps.setString(4, j.getLokasi());
            ps.setString(5, j.getPenanggungjawab());
            ps.setString(6, j.getDeskripsi());
            ps.setString(7, j.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(Jadwal j) {
        String sql = "UPDATE jadwal SET nama_kegiatan=?, tanggal=?, waktu=?, lokasi=?, penanggung_jawab=?, deskripsi=?, status=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, j.getNamaKegiatan());
            ps.setString(2, j.getTanggal());
            ps.setString(3, j.getWaktu());
            ps.setString(4, j.getLokasi());
            ps.setString(5, j.getPenanggungjawab());
            ps.setString(6, j.getDeskripsi());
            ps.setString(7, j.getStatus());
            ps.setInt(8, j.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM jadwal WHERE id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Jadwal> getUpcoming(int limit) {
        List<Jadwal> list = new ArrayList<>();
        String sql = "SELECT * FROM jadwal WHERE tanggal >= CURDATE() AND status='Aktif' ORDER BY tanggal ASC LIMIT ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    private Jadwal map(ResultSet rs) throws SQLException {
        Jadwal j = new Jadwal();
        j.setId(rs.getInt("id"));
        j.setNamaKegiatan(rs.getString("nama_kegiatan"));
        j.setTanggal(rs.getString("tanggal"));
        j.setWaktu(rs.getString("waktu"));
        j.setLokasi(rs.getString("lokasi"));
        j.setPenanggungjawab(rs.getString("penanggung_jawab"));
        j.setDeskripsi(rs.getString("deskripsi"));
        j.setStatus(rs.getString("status"));
        return j;
    }
}
